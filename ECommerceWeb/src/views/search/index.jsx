/* eslint-disable react/jsx-props-no-spreading */
import { LoadingOutlined } from '@ant-design/icons';
import { Boundary, MessageDisplay } from 'components/common';
import { ProductGrid } from 'components/product';
import { useDidMount, useQuerySearchProducts } from 'hooks';
import PropType from 'prop-types';
import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { setRequestStatus } from 'redux/actions/miscActions';
import { searchProduct } from 'redux/actions/productActions';

const Search = ({ match }) => {
  const { searchKey } = match.params;
  const {data, isLoading} = useQuerySearchProducts(searchKey);

  if (!isLoading) {
    return (
      <Boundary>
        <main className="content">
          <section className="product-list-wrapper product-list-search">
              <div className="product-list-header">
                <div className="product-list-header-title">
                  <h5>
                    {`Found ${data.data.items.length} ${
                      data.data.items.length > 1 ? "products" : "product"
                    } with keyword ${searchKey}`}
                  </h5>
                </div>
              </div>
            {data.data.items.length >0 && 
            <ProductGrid products={data.data.items} />}
          </section>
        </main>
      </Boundary>
    );
  }

  return (
    <main className="content">
      <div className="loader">
        <h4>Searching Product...</h4>
        <br />
        <LoadingOutlined style={{ fontSize: '3rem' }} />
      </div>
    </main>
  );
};

Search.propTypes = {
  match: PropType.shape({
    params: PropType.shape({
      searchKey: PropType.string
    })
  }).isRequired
};

export default Search;
