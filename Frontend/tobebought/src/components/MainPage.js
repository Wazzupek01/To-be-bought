import React, { useState, useEffect } from "react";
import classes from "./MainPage.module.css";
import Logo from "./UI/Logo";
import Register from "./user/Register";
import LogIn from "./user/LogIn";
import ListsContainer from "./lists/ListsContainer";
import TopBar from "./navigation/TopBar";

const MainPage = () => {
  const [showRegister, setShowRegister] = useState(false);
  const [userName, setUserName] = useState(sessionStorage.getItem("userName"));
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [showAccount, setShowAccount] = useState(false);

  useEffect(() => {
    if (userName === "Username doesn't exist" || userName === "undefined") setUserName(null);
    if (userName === null) setIsLoggedIn(false);
    else setIsLoggedIn(true);
  }, [userName]);

  const handleOnLogin = () => {
    const value = sessionStorage.getItem("userName");
    if (!(value === null)) {
      setUserName(value);
      setIsLoggedIn(true);
    }
  };

  const onRegister = () => {
    setShowRegister(false);
  };

  const handleOnLogout = () => {
    setIsLoggedIn(false);
  };

  const handleShowAccount = () => {
    setShowAccount(!showAccount);
  };

  return (
    <div className={classes.mainpage}>
      <TopBar
        userName={userName}
        isLoggedIn={isLoggedIn}
        onLogOut={handleOnLogout}
        showAccount={handleShowAccount}
      />
      <Logo />
      {!showAccount && (
        <React.Fragment>
          {userName === null ? (
            <div className={classes["login-register__container"]}>
              {showRegister ? (
                <Register onRegister={onRegister} />
              ) : (
                <LogIn onLogin={handleOnLogin} />
              )}
            </div>
          ) : (
            <ListsContainer />
          )}
          {userName === null && (
            <div onClick={() => setShowRegister(!showRegister)}>
              {!showRegister ? (
                <div>
                  Don't have an account?&nbsp;
                  <span style={{ color: "#579BB1", fontWeight: "bold" }}>
                    Register
                  </span>
                </div>
              ) : (
                <div>
                  Already have an account?&nbsp;
                  <span style={{ color: "#579BB1", fontWeight: "bold" }}>
                    Log In
                  </span>
                </div>
              )}
            </div>
          )}
        </React.Fragment>
      )}
    </div>
  );
};

export default MainPage;
