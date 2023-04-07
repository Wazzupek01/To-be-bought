import React from 'react';
import classes from './Input.module.css'

const Input = props => {
    return (
        <React.Fragment>
        <input
          placeholder={props.placeholder}
          type={props.type}
          className={`${classes.input} ${props.isValid === false && classes.invalid} ${props.className}`}
          id={props.id}
          onChange={props.onChange}
          onBlur={props.onBlur}
          value={props.value}
        ></input>
        {(props.isValid === false && props.showError) && <span className={classes.invalid__message}>{props.id} invalid</span>}
        </React.Fragment>
    )
}

export default Input