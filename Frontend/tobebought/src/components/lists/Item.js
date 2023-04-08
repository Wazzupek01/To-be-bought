import React, { useState } from "react";
import classes from "./Item.module.css";
import { sendRequest } from "../../helpers/sendRequest";

// [ ] TODO: field validation in update item

const Item = (props) => {
  const [name, setName] = useState(props.name);
  const [quantity, setQuantity] = useState(props.quantity);
  const [unit, setUnit] = useState(props.unit);
  const [update, setUpdate] = useState(false);

  const deleteItem = async () => {
    await sendRequest(
      "DELETE",
      null,
      "http://localhost:8080/item/" +
        props.id +
        "/shoppingList/" +
        props.shoppingListId
    ).then(props.onDelete(props.id));
  };

  const updateItem = async () => {
    if (update === false) {
      setUpdate(true);
    } else {
      setUpdate(false);

      const body = JSON.stringify({
        name: name,
        quantity: quantity,
        unit: unit,
      });

      sendRequest(
        "PUT",
        body,
        "http://localhost:8080/item/" +
          props.id +
          "/shoppingList/" +
          props.shoppingListId
      );
    }
  };

  const checkItem = async () => {
    sendRequest(
      "PUT",
      null,
      "http://localhost:8080/item/check/" +
        props.id +
        "/shoppingList/" +
        props.shoppingListId
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
