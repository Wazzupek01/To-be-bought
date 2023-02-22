import React from "react";
import classes from "./TopBar.module.css";
import Cookies from "js-cookie";

const TopBar = (props) => {
  const logout = () => {
    Cookies.remove("jwt-token");
    sessionStorage.removeItem("userName");
    window.location.reload();
  };

  return (
    <nav className={classes.topbar__container}>
      <span>{props.userName !== false && props.userName}</span>
      <div className={classes.topbar__hamburger}>
        <span>&#9776;</span>
        <div className={classes.topbar__menu}>
          {!(props.userName === null) && (
            <React.Fragment>
              <div onClick={logout}>Log Out</div>
              {/* account button for account info and settings */}
            </React.Fragment>
          )}
        </div>
      </div>
    </nav>
  );
};

export default TopBar;
