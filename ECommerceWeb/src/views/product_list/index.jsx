import { MessageDisplay } from "components/common";
import { Pagination } from "components/pagination";
import { ProductShowcaseGrid } from "components/product";
import { FEATURED_PRODUCTS } from "constants/routes";
import { useDocumentTitle, useScrollTop, useQueryProducts } from "hooks";
import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";

const ProductList = ({category}) => {
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

  const { isLoading, data } = useQueryProducts(category, state.currentPage);

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
          {isLoading ? (
            <MessageDisplay message={"Error"} buttonLabel="Try Again" />
          ) : (
            <>
              <div className="display-header">
                <h1>Category Name</h1>
                <Pagination
                  totalItemsCount={state.totalItemsCount}
                  currentPage={state.currentPage}
                  perPage={state.perPage}
                  pagesToShow={3}
                  onGoPage={goPage}
                  onPrevPage={onPrev}
                  onNextPage={onNext}
                />
              </div>

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

export default ProductList;
