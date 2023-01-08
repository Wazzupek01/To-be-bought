import React, { Component } from "react";
import "./LogIn.css";

class Register extends Component {
  constructor(props) {
    super(props);
  }

  state = { email: "", username: "", password: "" };

  register = () => {
    var myHeaders = new Headers();
    myHeaders.append("Content-Type", "application/json");
    myHeaders.append("Access-Control-Allow-Origin", "*");
    myHeaders.append("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE");
    myHeaders.append(
      "Access-Control-Allow-Headers",
      "Content-Type, Authorization"
    );

    var raw = JSON.stringify({
      email: this.state.email,
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

    fetch("http://localhost:8080/user/register", requestOptions)
      .then((response) => response.text())
      .then((result) => console.log(result))
      .catch((error) => console.log("error", error));
  };

  render() {
    return (
      <div className="register">
        <div className="register__text">Register</div>
        <input
          type="email"
          className="register__input"
          id="email"
          placeholder="Email"
          onChange={(event) => {
            this.state.email = event.currentTarget.value;
          }}
        ></input>
        <br />
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
        <button onClick={this.register}>Register</button>
      </div>
    );
  }
}

export default Register;
