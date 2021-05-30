/* eslint-disable react/no-array-index-key */
import { SearchOutlined } from '@ant-design/icons';
import React, { useRef, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useHistory } from 'react-router-dom';
import { clearRecentSearch } from 'redux/actions/filterActions';

const SearchBar = () => {
  const [searchInput, setSearchInput] = useState('');
  const searchbarRef = useRef(null);
  const history = useHistory();

  const onSearchChange = (e) => {
    const val = e.target.value.trimStart();
    setSearchInput(val);
  };

  const onKeyUp = (e) => {
    if (e.keyCode === 13) {
      e.target.blur();
      searchbarRef.current.classList.remove('is-open-recent-search');
      history.push(`/search/${searchInput.trim().toLowerCase()}`);
    }
  };

  const recentSearchClickHandler = (e) => {
    const searchBar = e.target.closest('.searchbar');
    if (!searchBar) {
      searchbarRef.current.classList.remove('is-open-recent-search');
      document.removeEventListener('click', recentSearchClickHandler);
    }
  };

  return (
    <>
      <div className="searchbar" ref={searchbarRef}>
        <SearchOutlined className="searchbar-icon" />
        <input
          className="search-input searchbar-input"
          onChange={onSearchChange}
          onKeyUp={onKeyUp}
          placeholder="Search product..."
          type="text"
          value={searchInput}
        />
      </div>
    </>
  );
};

export default SearchBar;
