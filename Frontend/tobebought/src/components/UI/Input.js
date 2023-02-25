import React from 'react';
import classes from './Input.module.css'

const Input = props => {
    return (
        <React.Fragment>
        <input
          type={props.type}
          className={`${classes.input} ${props.isValid === false && classes.invalid}`}
          id={props.id}
          onChange={props.onChange}
          onBlur={props.onBlur}
        ></input>
        {(props.isValid === false && props.showError) && <span className={classes.invalid__message}>{props.id} invalid</span>}
        </React.Fragment>
    )
}

export default Input