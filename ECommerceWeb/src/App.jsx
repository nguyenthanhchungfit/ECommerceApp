/* eslint-disable react/forbid-prop-types */
import PropType from 'prop-types';
import React, { StrictMode } from 'react';
import { Provider } from 'react-redux';
import AppRouter from 'routers/AppRouter';

const App = ({ store, persistor }) => (
  <StrictMode>
    <Provider store={store}>
    <AppRouter />
    </Provider>
  </StrictMode>
);

App.propTypes = {
  store: PropType.any.isRequired,
  persistor: PropType.any.isRequired
};

export default App;
