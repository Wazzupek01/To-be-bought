import React, { useReducer, useState, useEffect } from "react";
import classes from "./LogIn.module.css";
import Input from "../UI/Input";
import { regexPassword, regexUsername } from "../../helpers/constants";
import { sendRequest } from "../../helpers/sendRequest";

const formReducer = (state, action) => {
  if (action.type === "USERNAME_INPUT") {
    return {
      username: action.val,
      isUsernameValid: regexUsername.test(action.val),
      password: state.password,
      isPasswordValid: state.isPasswordValid,
    };
  }

  if (action.type === "USERNAME_BLUR") {
    return {
      username: state.username,
      isUsernameValid: regexUsername.test(state.username),
      password: state.password,
      isPasswordValid: state.isPasswordValid,
    };
  }

  if (action.type === "PASSWORD_INPUT") {
    return {
      username: state.username,
      isUsernameValid: state.isUsernameValid,
      password: action.val,
      isPasswordValid: regexPassword.test(action.val),
    };
  }

  if (action.type === "PASSWORD_BLUR") {
    return {
      username: state.username,
      isUsernameValid: state.isUsernameValid,
      password: state.password,
      isPasswordValid: regexPassword.test(state.password),
    };
  }

  return {
    username: "",
    isUsernameValid: null,
    password: "",
    isPasswordValid: null,
  };
};

const LogIn = (props) => {
  const [form, dispatchForm] = useReducer(formReducer, {
    username: "",
    isUsernameValid: null,
    password: "",
    isPasswordValid: null,
  });

  const [isFormValid, setIsFormValid] = useState(null);

  const usernameChangeHandler = (event) => {
    dispatchForm({ type: "USERNAME_INPUT", val: event.target.value });
  };

  const passwordChangeHandler = (event) => {
    dispatchForm({ type: "PASSWORD_INPUT", val: event.target.value });
  };

  const validateUsernameHandler = () => {
    dispatchForm({ type: "USERNAME_BLUR" });
  };

  const validatePasswordHandler = () => {
    dispatchForm({ type: "PASSWORD_BLUR" });
  };

  const login = async () => {
    const body = JSON.stringify({
      password: form.password,
      username: form.username,
    });

    let result = await sendRequest(
      "POST",
      body,
      "http://localhost:8080/authenticate"
    );
    
    console.log(result);
    sessionStorage.setItem("userName", result);
    props.onLogin();
  };

  useEffect(() => {
    const identifier = setTimeout(() => {
      setIsFormValid(form.isUsernameValid && form.isPasswordValid);
    }, 300);

    return () => {
      clearTimeout(identifier);
    };
  }, [form.isUsernameValid, form.isPasswordValid]);

  return (
    <div className={classes.register}>
      <div className={classes.register__text}>Log In</div>
      <Input
        type="text"
        id="Username"
        placeholder="Username"
        onChange={usernameChangeHandler}
        onBlur={validateUsernameHandler}
        showError={true}
        isValid={form.isUsernameValid}
      />
      <Input
        type="password"
        id="Password"
        placeholder="Password"
        onChange={passwordChangeHandler}
        onBlur={validatePasswordHandler}
        showError={true}
        isValid={form.isPasswordValid}
      />
      <button
        className={classes.login__button}
        onClick={login}
        disabled={!isFormValid}
      >
        Log In
      </button>
    </div>
  );
};

export default LogIn;
