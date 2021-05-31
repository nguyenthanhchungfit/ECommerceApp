import {
  ON_AUTHSTATE_FAIL,
  SIGNIN,
  SIGNOUT,
  SIGNUP,
} from "constants/constants";
import { SIGNIN as ROUTE_SIGNIN } from "constants/routes";
import { call, put } from "redux-saga/effects";
import { signInSuccess, signOutSuccess } from "redux/actions/authActions";
import { setAuthenticating, setAuthStatus } from "redux/actions/miscActions";
import { clearProfile } from "redux/actions/profileActions";
import axios from "axios";
import { clearBasket } from "redux/actions/basketActions";
import { resetCheckout } from "redux/actions/checkoutActions";
import { history } from "routers/AppRouter";

function signIn(payload) {
  return axios
    .get(`http://localhost:9000/api/login`, {
      params: payload,
      withCredentials: true,
    })
    .then((resp) => resp.data);
}

function signUp(payload) {
  return axios.get("http://localhost:9000/api/register", { params: payload });
}

function signOut() {
  return axios.get("http://localhost:9000/api/logout");
}

function* handleError(e) {
  const obj = { success: false, type: "auth", isError: true };
  yield put(setAuthenticating(false));
  
  switch (e.code) {
    // case 'auth/network-request-failed':
    //   yield put(setAuthStatus({ ...obj, message: 'Network error has occured. Please try again.' }));
    //   break;
    // case 'auth/email-already-in-use':
    //   yield put(setAuthStatus({ ...obj, message: 'Email is already in use. Please use another email' }));
    //   break;
    // case 'auth/wrong-password':
    //   yield put(setAuthStatus({ ...obj, message: 'Incorrect email or password' }));
    //   break;
    case 'auth/user-not-found':
      yield put(setAuthStatus({ ...obj, message: 'Incorrect email or password' }));
      break;
    // case 'auth/reset-password-error':
    //   yield put(setAuthStatus({ ...obj, message: 'Failed to send password reset email. Did you type your email correctly?' }));
    //   break;
    default:
      yield put(setAuthStatus({ ...obj, message: e.message }));
      break;
  }
}

function* initRequest() {
  yield put(setAuthenticating());
  yield put(setAuthStatus({}));
}

function* authSaga({ type, payload }) {
  switch (type) {
    case SIGNOUT:
      try {
        yield initRequest();
        yield put(clearBasket());
        yield put(clearProfile());
        yield put(resetCheckout());
        yield put(signOutSuccess());
        yield put(setAuthenticating(false));
        yield call(history.push, ROUTE_SIGNIN);
      } catch (e) {
        console.log(e);
      }
      break;
    case SIGNIN:
      try {
        yield initRequest();
        const response = yield call(signIn, payload);
        if (response && response.data) {
          const user = response.data;
          yield put(
            signInSuccess({
              id: user.id,
              role: "USER",
              provider: user.password,
            })
          );
        }else{
          yield handleError("auth/user-not-found");
        }
      } catch (e) {
        yield handleError(e);
      }
      break;

    case SIGNUP:
      try {
        yield initRequest();
        const response = yield call(signUp, payload);
        yield put(setAuthenticating(false));
        if (response && response.data.error === 0) {
          yield put(
            setAuthStatus({
              success: true,
              type: "auth",
              isError: false,
              message: "Register successfully",
            })
          );
        }
      } catch (e) {
        yield handleError(e);
      }
      break;
    case SIGNOUT:
      try {
        yield initRequest();
        const response = yield call(signOut);
        yield put(setAuthenticating(false));
        if (response && response.data.error === 0) {
          yield put(clearProfile());
          yield put(signOutSuccess());
        }
      } catch (e) {
        yield handleError(e);
      }
      break;
    case ON_AUTHSTATE_FAIL: {
      yield put(clearProfile());
      yield put(signOutSuccess());
      break;
    }
  }
}

export default authSaga;
