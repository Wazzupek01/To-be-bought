import React, { useState } from "react";
import classes from "./List.module.css";
import Item from "./Item";
import Input from "../UI/Input";
import { sendRequest } from "../../helpers/sendRequest";

// [ ] TODO: new list field validation
// [ ] TODO: update field validation

const List = (props) => {
  const [id, setId] = useState(props.id);
  const [name, setName] = useState(props.name);
  const [items, setItems] = useState([]);

  const [updateList, setUpdateList] = useState(false);
  const [showItems, setShowItems] = useState(false);

  const [newItemName, setNewItemName] = useState("Name");
  const [newItemQuantity, setNewItemQuantity] = useState(0);
  const [newItemUnit, setNewItemUnit] = useState("kilogram");
  const [showAddItem, setShowAddItem] = useState(false);

  const item = class item {
    constructor(id, name, quantity, unit, checked) {
      this.id = id;
      this.name = name;
      this.quantity = quantity;
      this.unit = unit;
      this.checked = checked;
    }
  };

  const fetchItems = async () => {
    return await sendRequest(
      "GET",
      null,
      "http://localhost:8080/shoppingList/" + id + "/all"
    );
  };

  const deleteList = async () => {
    await sendRequest(
      "DELETE",
      null,
      "http://localhost:8080/shoppingList/" + id
    );
    props.onUpdate();
  };

  const setFetchedItems = () => {
    if (showItems === false) {
      fetchItems().then((result) => {
        JSON.parse(result).forEach((el) => {
          const newItem = new item(
            el.id,
            el.name,
            el.quantity,
            el.unit,
            el.checked
          );
          let contains = false;
          for (let i = 0; i < items.length; i++) {
            if (items[i].id == newItem.id) contains = true;
          }
          if (!contains) {
            let itemsCopy = items;
            itemsCopy.push(newItem);
            setItems(itemsCopy);
            console.log(items);
          }
        });
        setShowItems(true);
      });
    } else {
      setShowItems(false);
    }
  };

  const update = async () => {
    if (updateList === false) {
      setUpdateList(true);
    } else {
      setUpdateList(false);
      return await sendRequest(
        "PUT",
        JSON.stringify({ name: name }),
        "http://localhost:8080/shoppingList/" + id
      );
    }
  };

  const addItem = async () => {
    if (showAddItem === false) {
      setShowAddItem(true);
    } else {
      setShowAddItem(false);

      const body = JSON.stringify({
        name: newItemName,
        quantity: newItemQuantity,
        unit: newItemUnit,
      });

      let result = await sendRequest(
        "POST",
        body,
        "http://localhost:8080/item/shoppingList/" + id
      );
      result = JSON.parse(result);
      setItems([
        ...items,
        new item(
          result.id,
          result.name,
          result.quantity,
          result.unit,
          result.checked
        )
      ]);
      console.log(items);
    }
  };

  const deleteItem = (id) => {
    let itemsCopy = items;
    setItems(itemsCopy.filter((item) => item.id !== id));
  };

  const itemList = items.map((value, index) => (
    <Item
      key={value.id}
      shoppingListId={id}
      id={value.id}
      name={value.name}
      quantity={value.quantity}
      unit={value.unit}
      checked={value.checked}
      onDelete={deleteItem}
    />
  ));

  return (
    <div className={classes.list__container}>
      <div className={classes.list__firstline}>
        {updateList ? (
          <Input
            type="text"
            onChange={(event) => {
              setName(event.currentTarget.value);
            }}
            value={name}
            placeholder="List name"
          />
        ) : (
          <div>{name}</div>
        )}
        <div className={classes.list__buttoncontainer}>
          <div className={classes.delete} onClick={deleteList}>
            &#10006;
          </div>
          <div className={classes.update} onClick={update}>
            &#8634;
          </div>
          <div onClick={setFetchedItems} className={classes.show}>
            {showItems ? "-" : "+"}
          </div>
        </div>
      </div>
      {showItems && (
        <div className={classes.list__items}>
          {showAddItem ? (
            <div className={classes.list__additem}>
              <input
                className={`${classes.additem__input} ${
                  !(newItemName.split(" ").join("").length > 0) &&
                  classes.invalid
                }`}
                type="text"
                onChange={(event) => {
                  setNewItemName(event.currentTarget.value);
                }}
                value={newItemName}
              ></input>
              <input
                className={`${classes.additem__input} ${
                  !(newItemQuantity > 0) && classes.invalid
                }`}
                type="number"
                onChange={(event) => {
                  setNewItemQuantity(event.currentTarget.value);
                }}
                value={newItemQuantity}
              ></input>
              <select
                className={classes.additem__input}
                onChange={(event) => {
                  setNewItemUnit(event.currentTarget.value);
                }}
                value={newItemUnit}
                required
              >
                <option value="kilogram">kilogram</option>
                <option value="gram">gram</option>
                <option value="piece">piece</option>
                <option value="pack">pack</option>
                <option value="litre">litre</option>
                <option value="unit" defaultValue>
                  unit
                </option>
              </select>
              <button onClick={addItem}>Add Item</button>
            </div>
          ) : (
            <div className={classes.button__additem} onClick={addItem}>
              +
            </div>
          )}
          {itemList}
        </div>
      )}
    </div>
  );
};

export default List;
