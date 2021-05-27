import { FeaturedProduct } from "components/product";
import {Carousel} from 'components/common'
import React from "react";
import { useQueryProducts } from "hooks";


const ProductCarousel = ({category}) => {

  console.log(category)

  const { isLoading, data } = useQueryProducts(category, 1);

  return (
    <div
      style={{
        maxWidth: 1200,
        marginLeft: "auto",
        marginRight: "auto",
        marginTop: 50,
      }}
    >
      {isLoading ? (
        <></>
      ) : (
        <Carousel show={4}>
          {data.data.items.map((product, id) => (
            <div>
              <div style={{ padding: 8 }}>
                <FeaturedProduct key={id} product={product} />
              </div>
            </div>
          ))}
        </Carousel>
      )}
    </div>
  );
};

export default ProductCarousel;
