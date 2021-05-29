import { MessageDisplay } from "components/common";
import { Pagination } from "components/pagination";
import { ProductShowcaseGrid } from "components/product";
import {
  useDocumentTitle,
  useScrollTop,
  useQueryRecommendProducts,
} from "hooks";
import React, { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import { Link } from "react-router-dom";

const RecommendProduct = () => {
 
  useSelector((state) => {console.log("STATE: "+ JSON.stringify(state))});

  const userId = 1;
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

  useDocumentTitle("Ecommerce | Recommend");
  useScrollTop();

  const { isLoading, data } = useQueryRecommendProducts(userId);

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
                products={data.data}
                skeletonCount={6}
              />
            </>
          )}
        </div>
      </div>
    </main>
  );
};

export default RecommendProduct;
