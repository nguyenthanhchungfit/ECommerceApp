import { ImageLoader } from 'components/common';
import { displayMoney } from 'helpers/utils';
import PropType from 'prop-types';
import React from 'react';
import Skeleton, { SkeletonTheme } from 'react-loading-skeleton';
import { useHistory } from 'react-router-dom';

const ProductFeatured = ({ product }) => {
  const history = useHistory();

  const categoryName = (category) =>{
    if(category==8095){
      return 'laptop-may-vi-tinh';
    }else if(category==1789){
      return 'dien-thoai-may-tinh-bang';
    }else if(category==1882){
      return 'dien-gia-dung'
    }

  }

  const onClickItem = () => {
    if (!product) return;
    history.push(`${categoryName(product.category)}/${product.id}`);
  };

  return (
    <SkeletonTheme color="#e1e1e1" highlightColor="#f2f2f2">
      <div
        className="product-display"
        onClick={onClickItem}
        role="presentation"
      >
        <div className="product-display-img">
          {product.thumbUrl ? (
            <ImageLoader
              alt=""
              className="product-card-img"
              src={product.thumbUrl}
            />
          ) : (
            <Skeleton width="100%" height="100%" />
          )}
        </div>
        <div className="product-display-details">
          <h2>{product.name || <Skeleton width={80} />}</h2>
          <p className="text-subtle text-italic">
            {product.brandName || <Skeleton width={40} />}
          </p>
          <h4 className="product-card-price">
            {product.price && displayMoney(product.price)}
          </h4>
        </div>
      </div>
    </SkeletonTheme>
  );
};

ProductFeatured.propTypes = {
  product: PropType.shape({
    image: PropType.string,
    name: PropType.string,
    id: PropType.string,
    brand: PropType.string
  }).isRequired
};

export default ProductFeatured;
