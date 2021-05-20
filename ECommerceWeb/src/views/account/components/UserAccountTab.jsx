/* eslint-disable indent */
import { ImageLoader } from "components/common";
import { ACCOUNT_EDIT } from "constants/routes";
import { displayDate } from "helpers/utils";
import { useQueryUser } from "hooks";
import PropType from "prop-types";
import React from "react";
import { useSelector } from "react-redux";
import { withRouter } from "react-router-dom";

const UserProfile = (props) => {
  const userId = useSelector((state) => state.auth.id);
  const { isLoading, error, data } = useQueryUser(userId);

  return (
    <div className="user-profile">
      <div className="user-profile-block">
        <div className="user-profile-banner">
          <div className="user-profile-banner-wrapper">
            <ImageLoader
              alt="Banner"
              className="user-profile-banner-img"
              src={
                "https://colorlib.com/wp/wp-content/uploads/sites/2/404-error-template-18.png"
              }
            />
          </div>
          <div className="user-profile-avatar-wrapper">
            <ImageLoader
              alt="Avatar"
              className="user-profile-img"
              src={
                "https://png.pngtree.com/element_our/20200610/ourmid/pngtree-character-default-avatar-image_2237203.jpg"
              }
            />
          </div>
          <button
            className="button button-small user-profile-edit"
            onClick={() => props.history.push(ACCOUNT_EDIT)}
            type="button"
          >
            Edit Account
          </button>
        </div>
        {isLoading ? (
          <h6>Loading ... </h6>
        ) : (
          <div className="user-profile-details">
            <h2 className="user-profile-name">{data.data.name}</h2>
            <span>Email</span>
            <br />
            <h5>{data.data.email}</h5>
            <span>Date of birth</span>
            <br />
            {data.data.dateOfBirth ? (
              <h5>{data.data.dateOfBirth}</h5>
            ) : (
              <h5 className="text-subtle text-italic">Address not set</h5>
            )}
          </div>
        )}
      </div>
    </div>
  );
};

UserProfile.propTypes = {
  history: PropType.shape({
    push: PropType.func,
  }).isRequired,
};

export default withRouter(UserProfile);
