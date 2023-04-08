export const sendRequest = async (method, body, url) => {
   
    const myHeaders = new Headers();
    myHeaders.append("Content-Type", "application/json");
    myHeaders.append("Access-Control-Allow-Origin", "*");
    myHeaders.append("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE");
    myHeaders.append(
      "Access-Control-Allow-Headers",
      "Content-Type, Authorization"
    );

    const requestOptions = {
      method: method,
      headers: myHeaders,
      body: body,
      redirect: "follow",
      credentials: "include",
    };

    
    return await fetch(url, requestOptions)
    .then((response) => response.text())
    .then((result) => {
        return result;
    })
    .catch((error) => console.log("error", error));
}
