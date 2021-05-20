import { ArrowLeftOutlined, LoadingOutlined } from "@ant-design/icons";
import { ImageLoader } from "components/common";
import {  SHOP } from "constants/routes";
import { displayMoney } from "helpers/utils";
import {
  useBasket,
  useDocumentTitle,
  useScrollTop,
  useQueryProductDetail,
} from "hooks";
import React, { useEffect, useRef, useState } from "react";
import { Link, useParams } from "react-router-dom";
import Select from "react-select";

const ViewProduct = () => {
  const { category, id } = useParams();
  const { isLoading, error, data } = useQueryProductDetail(category, id);

  const { addToBasket, isItemOnBasket } = useBasket(id);
  useScrollTop();
  useDocumentTitle(`View Item}`);

  const [selectedImage, setSelectedImage] = useState("");
  const [selectedColor, setSelectedColor] = useState("");
  const colorOverlay = useRef(null);

  useEffect(() => {
    setSelectedImage(data?.data.data.thumbUrl);
  }, [data]);


  const handleAddToBasket = () => {
    addToBasket({
      ...data.data.data,
      selectedColor,
      selectedSize: 1,
    });
  };

  return (
    <main className="content">
      {isLoading && (
        <div className="loader">
          <h4>Loading Product...</h4>
          <br />
          <LoadingOutlined style={{ fontSize: "3rem" }} />
        </div>
      )}
      {!isLoading && (
        <div className="product-view">
          <Link to={SHOP}>
            <h3 className="button-link d-inline-flex">
              <ArrowLeftOutlined />
              &nbsp; Back to shop
            </h3>
          </Link>
          <div className="product-modal">
            <div className="product-modal-image-wrapper">
              {selectedColor && (
                <input
                  type="color"
                  disabled
                  ref={colorOverlay}
                  id="color-overlay"
                />
              )}
              <ImageLoader
                alt={ data.data.data.name}
                className="product-modal-image"
                src={selectedImage}
              />
            </div>
            <div className="product-modal-details">
              <br />
              <span className="text-subtle">{ data.data.data.brandName}</span>
              <h1 className="margin-top-0">{ data.data.data.name}</h1>
              <span>{ data.data.data.shortDescription}</span>
              <br />
              <br />
              <div className="divider" />
              <br />
              <br />
              <h1>{displayMoney( data.data.data.price)}</h1>
              <div className="product-modal-action">
                <button
                  className={`button button-small ${
                    isItemOnBasket( data.data.data.id)
                      ? "button-border button-border-gray"
                      : ""
                  }`}
                  onClick={handleAddToBasket}
                  type="button"
                >
                  {isItemOnBasket( data.data.data.id)
                    ? "Remove From Basket"
                    : "Add To Basket"}
                </button>
              </div>
            </div>
          </div>
        </div>
      )}
    </main>
  );
};

export default ViewProduct;
