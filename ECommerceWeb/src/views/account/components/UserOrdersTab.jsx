import { BasketItemControl } from "components/basket";
import { ImageLoader } from "components/common";
import { useQueryOrderHistory } from "hooks";
import React from "react";
import { useSelector } from "react-redux";
import { Link } from "react-router-dom";

// Just add this feature if you want :P

const UserOrdersTab = () => {
  const { user } = useSelector((state) => ({
    user: state.auth,
  }));
  const { data, isLoading } = useQueryOrderHistory(user.id);
  return (
    <div className="loader" style={{ minHeight: "80vh" }}>
      <div>
        {isLoading && (
          <h5 className="text-subtle">Loading...</h5>
        )}
        {!isLoading && data && (
          data.data.map((product, index) => (
          <div className="basket-item">
            <div className="basket-item-wrapper">
              <div className="basket-item-img-wrapper">
                <ImageLoader
                  alt={product.name}
                  className="basket-item-img"
                  src={product.thumbUrl}
                />
              </div>
              <div className="basket-item-details">
                <Link
                  to={`/product/${product.id}`}
                  onClick={() =>
                    document.body.classList.remove("is-basket-open")
                  }
                >
                  <h4 className="underline basket-item-name">{product.name}</h4>
                </Link>
              </div>
              <div className="basket-item-price">
                {/* <h4 className="my-0">{displayMoney(product.price * product.quantity)}</h4> */}
              </div>
            </div>
          </div>
        )))}
      </div>
    </div>
  );
};
export default UserOrdersTab;
