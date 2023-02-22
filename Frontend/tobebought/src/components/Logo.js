import React from 'react';
import classes from './Logo.module.css';

const Logo = () => {
  return (
    <header className={classes.logo}>
      <div className={classes.logo__left}>
        <div>To</div>
        <div>Be</div>
      </div>
      <div className={classes.logo__right}>Bought</div>
    </header>
  );
};

export default Logo;
