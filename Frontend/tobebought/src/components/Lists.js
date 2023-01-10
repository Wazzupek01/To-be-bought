import React, { Component } from 'react';
import List from './List';
import "./Lists.css";

class Lists extends Component {
    constructor(props) {
        super(props);
    }

    list = class list {
        constructor(id, name){
            this.id = id;
            this.name = name;
        }
    }

    state = { lists: [], listName: null }

    componentDidMount() {
        this.getLists().then(result => {
            JSON.parse(result).forEach(element => {
                const newList = new this.list(element.id, element.name);
                let contains = false;
                for(let i = 0; i < this.state.lists.length; i++){
                    if(this.state.lists[i].id == newList.id) contains = true;
                }
                if(!contains){
                    this.state.lists.push(newList);
                }
                this.setState({lists: this.state.lists});
            });
        });
    }

    addList = () => {
        var myHeaders = new Headers();
        myHeaders.append("Content-Type", "application/json");
        myHeaders.append("Access-Control-Allow-Origin", "*");
        myHeaders.append("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE");
        myHeaders.append(
        "Access-Control-Allow-Headers",
        "Content-Type, Authorization"
        );
        
        var raw = JSON.stringify({name: this.state.listName});

        var requestOptions = {
        method: "POST",
        headers: myHeaders,
        body: raw,
        redirect: "follow",
        credentials: "include",
        };

        fetch("http://localhost:8080/shoppingList/", requestOptions)
        .then((response) => response.text())
        .then((result) => {
            console.log(result);
        })
        .catch((error) => console.log("error", error));
        this.setState({lists: [...this.state.lists, new this.list("", this.state.listName)]})
    }


    getLists = async () => {
        var myHeaders = new Headers();
        myHeaders.append("Content-Type", "application/json");
        myHeaders.append("Access-Control-Allow-Origin", "*");
        myHeaders.append("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE");
        myHeaders.append(
        "Access-Control-Allow-Headers",
        "Content-Type, Authorization"
        );


        var requestOptions = {
        method: "GET",
        headers: myHeaders,
        redirect: "follow",
        credentials: "include",
        };

        const response = await fetch("http://localhost:8080/user/lists", requestOptions);
        const result = await response.text();
        return result;
    }

    render() { 
        

        return (<div className="lists__container">
            <div className="lists__addlist">
                <input className="lists__input" type="text" onChange={(event) => {
            this.state.listName = event.currentTarget.value;
          }}></input> <button className="lists__button" onClick={this.addList}>Add</button>
            </div>
            <div className="lists">
                {
                    this.state.lists.map((element, index) => (<List  delete = {this.handleDelete} key = {element.id} id = {element.id} name = {element.name}/>))
                }
            </div>
        </div>);
    }
}


export default Lists;