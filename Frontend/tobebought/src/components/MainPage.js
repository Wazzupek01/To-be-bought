import React, { Component } from "react";
import LogIn from "./LogIn";
import Logo from "./Logo";
import "./MainPage.css";
import Register from "./Register";
import Cookies from 'js-cookie';
import Lists from "./Lists";

class MainPage extends Component {
  constructor(props) {
    super(props);
  }
  state = { token: Cookies.get("jwt-token"), register: false, loggedIn: false };


  renderPage = () => {
    if(this.state.token === undefined){
      return this.state.register ? <Register /> : <LogIn onLogin={this.setState({loggedIn: true})}/>;
    } else {
      
      return <Lists />;
    }
  }

  handleOnLogin = () => {
    const ref = this;
    ref.setState({loggedIn: true});
  }

  logout = () => {
    Cookies.remove('jwt-token');
  }


  render() {
    return (
      <div className="mainpage">
        <Logo />
        {
        this.state.token === undefined ? 
        <div className="login-register__container">{
          this.state.register ? <Register /> : <LogIn onLogin={this.handleOnLogin}/>
          }</div> : <Lists />
        }
        {
        this.state.token === undefined ?
        <div onClick={() => (this.setState({register: !this.state.register}))}>
            {
              !this.state.register ? "Don't have an account? Register" : "Already have an account? Log In"
            }
        </div> : <button onClick = {this.logout}>Log Out</button>
        }

      </div>
    );
  }
}

export default MainPage;
