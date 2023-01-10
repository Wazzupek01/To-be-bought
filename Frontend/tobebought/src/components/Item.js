import React, { Component } from "react";
import "./Item.css";

class Item extends Component {
  constructor(props) {
    super(props);
  }
  state = {
    id: this.props.id,
    name: this.props.name,
    quantity: this.props.quantity,
    unit: this.props.unit,
    checked: this.props.checked,
    update: false,
  };

  delete = async () => {
    var myHeaders = new Headers();
    myHeaders.append("Content-Type", "application/json");
    myHeaders.append("Access-Control-Allow-Origin", "*");
    myHeaders.append("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE");
    myHeaders.append(
      "Access-Control-Allow-Headers",
      "Content-Type, Authorization"
    );

    var requestOptions = {
      method: "DELETE",
      headers: myHeaders,
      redirect: "follow",
      credentials: "include",
    };

    const response = await fetch(
      "http://localhost:8080/item/" +
        this.state.id +
        "/shoppingList/" +
        this.props.shoppingListId,
      requestOptions
    );
  };

  update = async () => {
    if (this.state.update === false) {
      this.setState({ update: true });
    } else {
      this.setState({ update: false });
      var myHeaders = new Headers();
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

      var raw = JSON.stringify({
        name: this.state.name,
        quantity: this.state.quantity,
        unit: this.state.unit,
      });

      var requestOptions = {
        method: "PUT",
        headers: myHeaders,
        body: raw,
        redirect: "follow",
        credentials: "include",
      };

      const response = await fetch(
        "http://localhost:8080/item/" +
          this.state.id +
          "/shoppingList/" +
          this.props.shoppingListId,
        requestOptions
      );
    }
  };

  checkItem = async () => {
    var myHeaders = new Headers();
    myHeaders.append("Content-Type", "application/json");
    myHeaders.append("Access-Control-Allow-Origin", "*");
    myHeaders.append("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE");
    myHeaders.append(
      "Access-Control-Allow-Headers",
      "Content-Type, Authorization"
    );

    var requestOptions = {
      method: "PUT",
      headers: myHeaders,
      redirect: "follow",
      credentials: "include",
    };

    const response = await fetch(
      "http://localhost:8080/item/check/" +
        this.state.id +
        "/shoppingList/" +
        this.props.shoppingListId,
      requestOptions
    );
  };

  render() {
    return (
      <div>
        {this.state.update ? (
          <div className="item__container">
            <div className="item__inputs">
              <input
                type="text"
                onChange={(event) => {
                  this.setState({ name: event.currentTarget.value });
                }}
                value={this.state.name}
              ></input>
              <input
                type="text"
                onChange={(event) => {
                  this.setState({ quantity: event.currentTarget.value });
                }}
                value={this.state.quantity}
              ></input>
              <input
                type="text"
                onChange={(event) => {
                  this.setState({ unit: event.currentTarget.value });
                }}
                value={this.state.unit}
              ></input>
            </div>
            <div className="item__buttons">
              <input
                type="checkbox"
                defaultChecked={this.state.checked}
                onChange={this.checkItem}
              />
              <div onClick={this.update} className="update">&#8634;</div>
              <div onClick={this.delete} className="delete">&#10006;</div>
              </div>
          </div>
        ) : (
          <div className="item__container">
            <div>{this.state.name}</div>
            <div>{this.state.quantity}</div>
            <div>{this.state.unit}</div>
            <div className="item__buttons">
              <input
                type="checkbox"
                defaultChecked={this.state.checked}
                onChange={this.checkItem}
              />
              <div onClick={this.update} className="update">&#8634;</div>
              <div onClick={this.delete} className="delete">&#10006;</div>
              </div>
          </div>
        )}
      </div>
    );
  }
}

export default Item;
