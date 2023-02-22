import React, {useState} from 'react';
import classes from './LogIn.module.css';

const LogIn  = (props) => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [isUsernameValid, setIsUsernameValid] = useState(true);
  const [isPasswordValid, setIsPasswordValid] = useState(true);

  const login = async () => {
    var myHeaders = new Headers();
    myHeaders.append("Content-Type", "application/json");
    myHeaders.append("Access-Control-Allow-Origin", "*");
    myHeaders.append("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE");
    myHeaders.append(
      "Access-Control-Allow-Headers",
      "Content-Type, Authorization"
    );

    var raw = JSON.stringify({
      password: password,
      username: username
    });

    var requestOptions = {
      method: "POST",
      headers: myHeaders,
      body: raw,
      redirect: "follow",
      credentials: "include",
    };

    fetch("http://localhost:8080/authenticate", requestOptions)
      .then((response) => response.text())
      .then((result) => {sessionStorage.setItem("userName", result); props.onLogin();})
      .catch((error) => console.log("error", error));
  }

  const validateUsername = () => {
    const regexUsername = new RegExp("[A-Za-z][A-Za-z0-9_]{4,29}");
    setIsUsernameValid(regexUsername.test(username));
  }

  const validatePassword = () => {
    const regexPassword = new RegExp("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#\$%\^&\*])(?=.{7,30})");
    setIsPasswordValid(regexPassword.test(password));
  }

  const buttonIsDisabled = !isPasswordValid || !isUsernameValid;

  return (
    <div className={classes.register}>
        <div className={classes.register__text}>Log In</div>
        <input
          type="text"
          className={`${classes.register__input} ${!isUsernameValid && classes.invalid}`}
          id="username"
          onChange={(event) => {
            setUsername(event.currentTarget.value);
          }}
          onBlur={validateUsername}
        ></input>
        {!isUsernameValid && <span className={classes.invalid__message}>Username invalid</span>}
        <input
          type="password"
          className={`${classes.register__input} ${!isPasswordValid && classes.invalid}`}
          id="password"
          onChange={(event) => {
            setPassword(event.currentTarget.value);
          }}
          onBlur={validatePassword}
        ></input>
        {!isPasswordValid && <span className={classes.invalid__message}>Password invalid</span>}
        <button className={classes.login__button} onClick={login} disabled={buttonIsDisabled}>Log In</button>
      </div>
  );
};

export default LogIn;