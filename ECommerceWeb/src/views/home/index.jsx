
import { MessageDisplay } from 'components/common';
import { ProductShowcaseGrid } from 'components/product';
import { FEATURED_PRODUCTS } from 'constants/routes';
import {
  useDocumentTitle, useScrollTop, useQueryProducts
} from 'hooks';
import React from 'react';
import { Link } from 'react-router-dom';


const Home = () => {
  useDocumentTitle('Ecommerce | Home');
  useScrollTop();

  const { isLoading, data } = useQueryProducts(8095);

  return (
    <main className="content">
      <div className="home">
        <div className="display">
          <div className="display-header">
            <h1>Featured Products</h1>
            <Link to={FEATURED_PRODUCTS}>See All</Link>
          </div>
          {(isLoading) ? (
            <MessageDisplay
              message={"Error"}
              buttonLabel="Try Again"
            />
          ) : (
            <ProductShowcaseGrid
              products={data.data.data}
              skeletonCount={6}
            />
          )}
        </div>
      </div>
    </main>
  );
};

export default Home;
