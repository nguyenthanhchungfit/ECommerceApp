import * as ACTION from 'constants/constants';
import { takeLatest } from 'redux-saga/effects';
import authSaga from './authSaga';

function* rootSaga() {
  yield takeLatest([
    ACTION.SIGNIN,
    ACTION.SIGNUP,
    ACTION.SIGNOUT,
    ACTION.ON_AUTHSTATE_CHANGED,
    ACTION.ON_AUTHSTATE_SUCCESS,
    ACTION.ON_AUTHSTATE_FAIL,
    ACTION.SET_AUTH_PERSISTENCE,
    ACTION.RESET_PASSWORD
  ], authSaga);
}

export default rootSaga;
