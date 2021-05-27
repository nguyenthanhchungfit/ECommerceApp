import { Carousel, MessageDisplay } from "components/common";
import { Pagination } from "components/pagination";
import { FeaturedProduct, ProductCarousel, ProductShowcaseGrid } from "components/product";
import { FEATURED_PRODUCTS } from "constants/routes";
import { useDocumentTitle, useScrollTop, useQueryProducts } from "hooks";
import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";

const Home = () => {

  return (
    <div style={{marginTop:`200px`}}>
      <ProductCarousel category={8095} />
      <ProductCarousel category={1789} />
      <ProductCarousel category={1882} />
    </div>
  );
};

export default Home;
