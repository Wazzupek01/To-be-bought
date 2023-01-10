import React, { Component } from "react";
import "./LogIn.css";
import Cookies from "js-cookie";

class LogIn extends Component {
  constructor(props) {
    super(props);
  }
  state = {username: "", password: ""};

  login = async () => {
    var myHeaders = new Headers();
    myHeaders.append("Content-Type", "application/json");
    myHeaders.append("Access-Control-Allow-Origin", "*");
    myHeaders.append("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE");
    myHeaders.append(
      "Access-Control-Allow-Headers",
      "Content-Type, Authorization"
    );

    var raw = JSON.stringify({
      password: this.state.password,
      username: this.state.username
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
      .then((result) => console.log(result))
      .catch((error) => console.log("error", error));

      const response = await fetch("http://localhost:8080/user/lists", requestOptions);
      const result = await response.text().then();
      this.props.onLogin();
  }



  render() {
    return (
      <div className="register">
        <div className="register__text">Log In</div>
        <input
          type="text"
          className="register__input"
          id="username"
          placeholder="User name"
          onChange={(event) => {
            this.state.username = event.currentTarget.value;
          }}
        ></input>
        <br />
        <input
          type="password"
          className="register__input"
          id="password"
          placeholder="Password"
          onChange={(event) => {
            this.state.password = event.currentTarget.value;
          }}
        ></input>
        <button className="login__button" onClick={this.login}>Log In</button>
      </div>
    );
  }
}

export default LogIn;
