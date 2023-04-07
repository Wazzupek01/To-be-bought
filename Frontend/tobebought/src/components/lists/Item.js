import React, { useState } from 'react';
import classes from './Item.module.css';

// [ ] TODO: field validation in update item

const Item = (props) => {
  const [name, setName] = useState(props.name);
  const [quantity, setQuantity] = useState(props.quantity);
  const [unit, setUnit] = useState(props.unit);
  const [update, setUpdate] = useState(false);

  const deleteItem = async () => {
    let myHeaders = new Headers();
    myHeaders.append("Content-Type", "application/json");
    myHeaders.append("Access-Control-Allow-Origin", "*");
    myHeaders.append("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE");
    myHeaders.append(
      "Access-Control-Allow-Headers",
      "Content-Type, Authorization"
    );

    let requestOptions = {
      method: "DELETE",
      headers: myHeaders,
      redirect: "follow",
      credentials: "include",
    };

    const response = await fetch(
      "http://localhost:8080/item/" +
        props.id +
        "/shoppingList/" +
        props.shoppingListId,
      requestOptions
    ).then(props.onDelete(props.id));
  };

  const updateItem = async () => {
    if (update === false) {
      setUpdate(true);
    } else {
      setUpdate(false);
      let myHeaders = new Headers();
      myHeaders.append("Content-Type", "application/json");
      myHeaders.append("Access-Control-Allow-Origin", "*");
      myHeaders.append(
        "Access-Control-Allow-Methods",
        "POST, GET, PUT, DELETE"
      );
      myHeaders.append(
        "Access-Control-Allow-Headers",
        "Content-Type, Authorization"
      );

      let raw = JSON.stringify({
        name: name,
        quantity: quantity,
        unit: unit,
      });

      let requestOptions = {
        method: "PUT",
        headers: myHeaders,
        body: raw,
        redirect: "follow",
        credentials: "include",
      };

      await fetch(
        "http://localhost:8080/item/" +
          props.id +
          "/shoppingList/" +
          props.shoppingListId,
        requestOptions
      );
    }
  };

  const checkItem = async () => {
    let myHeaders = new Headers();
    myHeaders.append("Content-Type", "application/json");
    myHeaders.append("Access-Control-Allow-Origin", "*");
    myHeaders.append("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE");
    myHeaders.append(
      "Access-Control-Allow-Headers",
      "Content-Type, Authorization"
    );

    let requestOptions = {
      method: "PUT",
      headers: myHeaders,
      redirect: "follow",
      credentials: "include",
    };

    await fetch(
      "http://localhost:8080/item/check/" +
        props.id +
        "/shoppingList/" +
        props.shoppingListId,
      requestOptions
    );
  };
  return (
    <div>
      {update ? (
        <div className={classes.item__container}>
          <div className={classes.item__inputs}>
            <input
              type="text"
              onChange={(event) => {
                setName(event.currentTarget.value);
              }}
              value={name}
            ></input>
            <input
              type="text"
              onChange={(event) => {
                setQuantity(event.currentTarget.value);
              }}
              value={quantity}
            ></input>
            <input
              type="text"
              onChange={(event) => {
                setUnit(event.currentTarget.value);
              }}
              value={unit}
            ></input>
          </div>
          <div className={classes.item__buttons}>
            <input
              type="checkbox"
              defaultChecked={props.checked}
              onChange={checkItem}
            />
            <div onClick={updateItem} className={classes.update}>
              &#8634;
            </div>
            <div onClick={deleteItem} className={classes.delete}>
              &#10006;
            </div>
          </div>
        </div>
      ) : (
        <div className={classes.item__container}>
          <div>{name}</div>
          <div>{quantity}</div>
          <div>{unit}</div>
          <div className={classes.item__buttons}>
            <input
              type="checkbox"
              defaultChecked={props.checked}
              onChange={checkItem}
            />
            <div onClick={updateItem} className={classes.update}>
              &#8634;
            </div>
            <div onClick={deleteItem} className={classes.delete}>
              &#10006;
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default Item;
