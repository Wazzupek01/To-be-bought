import React, {useState, useEffect} from "react";
import classes from "./ListsContainer.module.css";
import List from "./List";
import { sendRequest } from '../../helpers/sendRequest';
import Input from "../UI/Input";

const list = class list {
    constructor(id, name) {
        this.id = id;
        this.name = name;
    }
};

const ListsContainer = (props) => {
    const [lists, setLists] = useState([]);
    const [newListName, setNewListName] = useState(null);
    const [isNewListValid, setIsNewListValid] = useState("");

    const addList = async () => {
        if (isNewListValid) {
            const body = JSON.stringify({name: newListName});
            const result = JSON.parse(await sendRequest("POST", body, "http://localhost:8080/shoppingList/")); 
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
    }, []);

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
            <Input type="text"
                   onChange={(event) => {
                       setNewListName(event.currentTarget.value);
                   }} 
                   onBlur={validateNewList}
                   placeholder="New list"
                   isValid={isNewListValid}></Input>
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