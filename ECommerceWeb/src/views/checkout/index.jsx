import { ArrowRightOutlined, ShopOutlined } from "@ant-design/icons";
import { BasketItem } from "components/basket";
import { ACCOUNT, ACCOUNT_ORDER } from "constants/routes";
import { displayMoney } from "helpers/utils";
import { useDocumentTitle, useMutationCheckout, useScrollTop } from "hooks";
import PropType from "prop-types";
import React from "react";
import { useMutation } from "react-query";
import { useDispatch, useSelector } from "react-redux";
import { useHistory } from "react-router-dom";
import { clearBasket } from "redux/actions/basketActions";
import withCheckout from "./withCheckout";

const OrderSummary = ({ basket, subtotal }) => {
  useDocumentTitle("Check Out Step 1 | Salinaka");
  useScrollTop();
  const { user } = useSelector((state) => ({
    user: state.auth,
  }));
  const dispatch = useDispatch();
  const history = useHistory();
  const onClickPrevious = () => history.push("/");

  const [mutation, { isLoading }] = useMutationCheckout();

  const onClickFinish = () => {
    let orderItems = [];
    basket.map((product) => {
      let productId = product.id;
      let category = product.category;
      let quantity = 1;
      let price = product.price;
      orderItems = [...orderItems, {productId, category, quantity, price}];
    });

    mutation({
      userId:user.id,
      orderItems: orderItems,
      subTotal: subtotal,
    }, {
      onSuccess: () => {
        dispatch(clearBasket());
        history.push(ACCOUNT)
      },
    });
  };

  return (
    <div className="checkout">
      <div className="checkout-step-1">
        <h3 className="text-center">Order Summary</h3>
        <span className="d-block text-center">
          Review items in your basket.
        </span>
        <br />
        <div className="checkout-items">
          {basket.map((product) => (
            <BasketItem
              basket={basket}
              dispatch={dispatch}
              key={product.id}
              product={product}
            />
          ))}
        </div>
        <br />
        <div className="basket-total text-right">
          <p className="basket-total-title">Subtotal:</p>
          <h2 className="basket-total-amount">{displayMoney(subtotal)}</h2>
        </div>
        <br />
        <div className="checkout-shipping-action">
          <button
            className="button button-muted"
            onClick={onClickFinish}
            type="button"
          >
            <ShopOutlined />
            &nbsp; Continue Shopping
          </button>
          <button className="button" onClick={onClickFinish} type="submit">
            Finish &nbsp;
            <ArrowRightOutlined />
          </button>
        </div>
      </div>
    </div>
  );
};

OrderSummary.propTypes = {
  basket: PropType.arrayOf(PropType.object).isRequired,
  subtotal: PropType.number.isRequired,
};

export default withCheckout(OrderSummary);
