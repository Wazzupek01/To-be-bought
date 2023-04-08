import React, {useState, useEffect} from "react";
import classes from "./ListsContainer.module.css";
import List from "./List";
import { sendRequest } from '../../helpers/sendRequest';

const list = class list {
    constructor(id, name) {
        this.id = id;
        this.name = name;
    }
};

const ListsContainer = (props) => {
    const [lists, setLists] = useState([]);
    const [newListName, setNewListName] = useState(null);
    const [isNewListValid, setIsNewListValid] = useState(null);

    const addList = async () => {
        if (isNewListValid) {
            const body = JSON.stringify({name: newListName});
            const result = await sendRequest("POST", body, "http://localhost:8080/shoppingList/"); 
            setLists([...lists, new list(result.id, result.name)]);
        }
    };

    const getLists = async () => {
        return await sendRequest("GET", null, "http://localhost:8080/user/lists");
    }

    const update = () => {
        getLists().then((result) => {
            let fetchedLists = [];
            JSON.parse(result).forEach((element) => {
                const newList = new list(element.id, element.name);
                fetchedLists.push(newList);
            });
            setLists(fetchedLists);
        });
    };

    useEffect(() => {
        update();
    });

    const validateNewList = () => {
        if (newListName == null) {
            setIsNewListValid(false);
            return;
        }
        if (newListName.split(" ").join("").length < 1) {
            setIsNewListValid(false);
            return;
        }
        setIsNewListValid(true);
    }


    return (<div className={classes.lists__container}>
        <div className={classes.lists__addlist}>
            <input className={`${classes.lists__input} ${!isNewListValid && classes.invalid}`} type="text"
                   onChange={(event) => {
                       setNewListName(event.currentTarget.value);
                   }} onBlur={validateNewList}></input>
            <button className={classes.lists__button} onClick={addList} disabled={!isNewListValid}>Add</button>
        </div>

        <div className={classes.lists}>
            {
                lists.map((element, index) => (
                    <List key={element.id} onUpdate={update} id={element.id} name={element.name}/>))
            }
        </div>
    </div>);
};

export default ListsContainer;