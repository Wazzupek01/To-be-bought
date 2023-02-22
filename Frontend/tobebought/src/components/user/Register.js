import React, {useState} from 'react';
import classes from "./LogIn.module.css";

const Register = (props) => {
  const [email, setEmail] = useState("");
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [isUsernameValid, setIsUsernameValid] = useState(true);
  const [isPasswordValid, setIsPasswordValid] = useState(true);
  const [isEmailValid, setIsEmailValid] = useState(true);


  const register = () => {
    var myHeaders = new Headers();
    myHeaders.append("Content-Type", "application/json");
    myHeaders.append("Access-Control-Allow-Origin", "*");
    myHeaders.append("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE");
    myHeaders.append(
      "Access-Control-Allow-Headers",
      "Content-Type, Authorization"
    );

    var raw = JSON.stringify({
      email: email,
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

    fetch("http://localhost:8080/user/register", requestOptions)
      .then((response) => response.text())
      .then((result) => console.log(result))
      .catch((error) => console.log("error", error));
      props.onRegister();
  };

  const validateUsername = () => {
    const regexUsername = new RegExp("[A-Za-z][A-Za-z0-9_]{4,29}");
    console.log(regexUsername.test(username));
    setIsUsernameValid(regexUsername.test(username));
  }

  const validatePassword = () => {
    const regexPassword = new RegExp("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#\$%\^&\*])(?=.{7,30})");
    setIsPasswordValid(regexPassword.test(password));
  }

  const validateEmail = () => {
    const regexEmail = new RegExp("^(.+)@(.+)$");
    setIsEmailValid(regexEmail.test(email));
  }

  const buttonIsDisabled = !isPasswordValid || !isUsernameValid || !isEmailValid;

  return (
    <div className={classes.register}>
        <div className={classes.register__text}>Register</div>
        <input
          type="email"
          className={`${classes.register__input} ${!isEmailValid && classes.invalid}`}
          id="email"
          placeholder="Email"
          onChange={(event) => {
            setEmail(event.currentTarget.value);
          }}
          onBlur={validateEmail}
        ></input>
        {!isEmailValid && <span className={classes.invalid__message}>Email invalid</span>}
        <input
          type="text"
          className={`${classes.register__input} ${!isUsernameValid && classes.invalid}`}
          id="username"
          placeholder="User name"
          onChange={(event) => {
            setUsername(event.currentTarget.value);
          }}
          onBlur={validateUsername}
        ></input>
        {!isUsernameValid && <span className={classes.invalid__message} >Username invalid</span>}
        <input
          type="password"
          className={`${classes.register__input} ${!isPasswordValid && classes.invalid}`}
          id="password"
          placeholder="Password"
          onChange={(event) => {
            setPassword(event.currentTarget.value);
          }}
          onBlur={validatePassword}
        ></input>
        {!isPasswordValid && <span className={classes.invalid__message} >Password invalid</span>}
        <button className={classes.login__button} onClick={register} disabled={buttonIsDisabled} >Register</button>
      </div>
  );
};

export default Register;