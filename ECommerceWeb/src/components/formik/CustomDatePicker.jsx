/* eslint-disable react/jsx-props-no-spreading */
/* eslint-disable react/forbid-prop-types */
import PropType from "prop-types";
import React from "react";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";

const CustomDatePicker = ({
  field,
  form: { touched, errors, setFieldValue },
  label,
  ...props
}) => {
  return (
    <div className="input-group">
      {touched[field.name] && errors[field.name] ? (
        <span className="label-input label-error">{errors[field.name]}</span>
      ) : (
        <label className="label-input" htmlFor={field.name}>
          {label}
        </label>
      )}
      <DatePicker
        selected={(field.value && new Date(field.value)) || null}
        onChange={(newDate) => {
          setFieldValue(field.name, newDate);
        }}
        {...props}
      />
    </div>
  );
};

CustomDatePicker.propTypes = {
  label: PropType.string.isRequired,
  form: PropType.object.isRequired,
  field: PropType.object.isRequired,
};

export default CustomDatePicker;
