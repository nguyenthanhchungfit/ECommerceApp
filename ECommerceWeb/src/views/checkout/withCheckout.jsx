/* eslint-disable no-nested-ternary */
import { SIGNIN } from "constants/routes";
import { calculateTotal } from "helpers/utils";
import React from "react";
import { useSelector } from "react-redux";
import { Redirect, withRouter } from "react-router-dom";
import store from "redux/store/store";

const withCheckout = (Component) =>
  withRouter((props) => {
    const state = useSelector((store) => ({
      isAuth: !!store.auth.id && !!store.auth.role,
      basket: store.basket,
    }));

    const subtotal = calculateTotal(
      state.basket.map((product) => 
        product.price * product.selectedSize
      )
    );

    if (!state.isAuth) {
      return <Redirect to={SIGNIN} />;
    }
    if (state.basket.length === 0) {
      return <Redirect to="/" />;
    }
    if (state.isAuth && state.basket.length !== 0) {
      return (
        <Component
          {...props}
          basket={state.basket}
          subtotal={Number(subtotal)}
        />
      );
    }
    return null;
  });

export default withCheckout;
