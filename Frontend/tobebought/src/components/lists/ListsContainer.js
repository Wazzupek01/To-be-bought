import React, {useState, useEffect} from 'react';
import classes from './ListsContainer.module.css';
import List from './List';

const list = class list {
  constructor(id, name){
    this.id = id;
    this.name = name;
  }
}

const ListsContainer = (props) => {
  
  const [lists, setLists] = useState([]);
  const [newListName, setNewListName] = useState(null);
  const [isNewListValid, setIsNewListValid] = useState(true);

  const addList = () => {
    var myHeaders = new Headers();
    myHeaders.append("Content-Type", "application/json");
    myHeaders.append("Access-Control-Allow-Origin", "*");
    myHeaders.append("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE");
    myHeaders.append(
    "Access-Control-Allow-Headers",
    "Content-Type, Authorization"
    );
    
    var raw = JSON.stringify({name: newListName});

    var requestOptions = {
    method: "POST",
    headers: myHeaders,
    body: raw,
    redirect: "follow",
    credentials: "include",
    };

    fetch("http://localhost:8080/shoppingList/", requestOptions)
    .then((response) => response.json())
    .then((result) => {
        setLists([...lists, new list(result.id, result.name)])
    })
    .catch((error) => console.log("error", error));
}


  const getLists = async () => {
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

  useEffect(() => {
    getLists().then(result => {
      JSON.parse(result).forEach(element => {
          const newList = new list(element.id, element.name);
          let contains = false;
          for(let i = 0; i < lists.length; i++){
              if(lists[i].id === newList.id) contains = true;
          }
          if(!contains){
            setLists([...lists, newList]);
          }
      });
    });
  });

  const update = () => {
    getLists().then(result => {
      JSON.parse(result).forEach(element => {
          const newList = new list(element.id, element.name);
          let contains = false;
          for(let i = 0; i < lists.length; i++){
              if(lists[i].id === newList.id) contains = true;
          }
          if(!contains){
            setLists([...lists, newList]);
          }
      });
    });
  }

  const validateNewList = () => {
    if(newListName == null){
      setIsNewListValid(false);
      return;
    }
    if(newListName.split(' ').join('').length < 1){
      setIsNewListValid(false);
      return;
    }
    setIsNewListValid(true);
  }


  return (<div className={classes.lists__container}>
            <div className={classes.lists__addlist}>
                <input className={`${classes.lists__input} ${!isNewListValid && classes.invalid}`} type="text" onChange={(event) => {
            setNewListName(event.currentTarget.value);
          }} onBlur={validateNewList}></input> <button className={classes.lists__button} onClick={addList} disabled={!isNewListValid}>Add</button>
            </div>
            
            <div className={classes.lists}>
                {
                    lists.map((element, index) => (<List key = {element.id} onUpdate={update} id = {element.id} name = {element.name}/>))
                }
            </div>
        </div>);
};

export default ListsContainer;