import {
  ON_AUTHSTATE_FAIL,
  SIGNIN,
} from "constants/constants";
import { call, put } from "redux-saga/effects";
import { signInSuccess, signOutSuccess } from "redux/actions/authActions";
import { setAuthenticating, setAuthStatus } from "redux/actions/miscActions";
import { clearProfile } from "redux/actions/profileActions";
import axios from "axios";

function signIn(email, password) {
  return axios.get(
    `http://localhost:9000/user?email=${email}&password=${password}`
  );
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
    // case 'auth/user-not-found':
    //   yield put(setAuthStatus({ ...obj, message: 'Incorrect email or password' }));
    //   break;
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
    case SIGNIN:
      try {
        yield initRequest();
        const response = yield call(signIn, payload.email, payload.password);
        if (response &&response.data) {
          const user = response.data;
          yield put(
            signInSuccess({
              id: user.id,
              role: "USER",
              provider: user.password,
            })
          );
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