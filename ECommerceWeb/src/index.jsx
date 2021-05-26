import { Preloader } from 'components/common';
import 'normalize.css/normalize.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import React from 'react';
import { render } from 'react-dom';
import 'react-phone-input-2/lib/style.css';
import { onAuthStateFail, onAuthStateSuccess } from 'redux/actions/authActions';
import configureStore from 'redux/store/store';
import 'styles/style.scss';
import WebFont from 'webfontloader';
import App from './App';

WebFont.load({
  google: {
    families: ['Tajawal']
  }
});

const { store, persistor } = configureStore();
const root = document.getElementById('app');

render(<App store={store} persistor={persistor} />, root);