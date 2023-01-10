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

  handleOnLogin = () => {
    this.setState({loggedIn: true, token: Cookies.get('jwt-token')});
  }

  logout = () => {
    Cookies.remove('jwt-token');
    window.location.reload();
  }

  onRegister = () => {
    this.setState({register: false});
  }


  render() {
    return (
      <div className="mainpage">
        <div className="topbar">
          <div className='topbar__hamburger'> &#9776;
          <div className="topbar__menu">
            <div onClick = {this.logout}>Log Out</div>
          </div>
          </div>
        </div>
        <Logo />
        {
        this.state.token === undefined ? 
        <div className="login-register__container">{
          this.state.register ? <Register onRegister={this.onRegister}/> : <LogIn onLogin={this.handleOnLogin}/>
          }</div> : <Lists />
        }
        {
        this.state.token === undefined ?
        <div onClick={() => (this.setState({register: !this.state.register}))}>
            {
              !this.state.register ? <div>Don't have an account?<span style={{"color":"#579BB1", "font-weight":"bold"}}> Register</span></div>
                : <div>Already have an account? <span style={{"color":"#579BB1", "font-weight":"bold"}}>Log In</span></div>
            }
        </div> : ""
        }

      </div>
    );
  }
}

export default MainPage;
