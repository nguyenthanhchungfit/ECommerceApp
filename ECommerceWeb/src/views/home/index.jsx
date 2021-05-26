import { MessageDisplay } from "components/common";
import { Pagination } from "components/pagination";
import { ProductShowcaseGrid } from "components/product";
import { FEATURED_PRODUCTS } from "constants/routes";
import { useDocumentTitle, useScrollTop, useQueryProducts } from "hooks";
import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";

const Home = () => {
  const [state, setState] = useState({
    perPage: 20,
    currentPage: 1,
    pagesToShow: 1,
    totalItemsCount: 0,
  });

  const onPrev = () => {
    setState({
      ...state,
      currentPage: state.currentPage - 1,
    });
  };

  const onNext = () => {
    setState({
      ...state,
      currentPage: state.currentPage + 1,
    });

    console.log(state);
  };

  const goPage = (n) => {
    setState({
      ...state,
      currentPage: n,
    });
  };

  useDocumentTitle("Ecommerce | Home");
  useScrollTop();

  const { isLoading, data } = useQueryProducts(8095,state.currentPage);

  useEffect(() => {
    if (!isLoading) {
      let totalProducts = data.data.total;
      let perPage = data.data.nitemsPerPage;
      setState({
        ...state,
        perPage: perPage,
        pagesToShow: Math.ceil(totalProducts / perPage),
        totalItemsCount: totalProducts,
      });
    }
  }, [data]);

  return (
    <main className="content">
      <div className="home">
        <div className="display">
          <div className="display-header">
            <h1>Featured Products</h1>
            <Link to={FEATURED_PRODUCTS}>See All</Link>
          </div>
          {isLoading ? (
            <MessageDisplay message={"Error"} buttonLabel="Try Again" />
          ) : (
            <>
              <ProductShowcaseGrid
                products={data.data.items}
                skeletonCount={6}
              />
            </>
          )}
        </div>
      </div>
    </main>
  );
};

export default Home;
