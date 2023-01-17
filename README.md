# ToBeBought-SpringBoot-ReactJS
Simple app for storing users shopping lists, allowing to register and login with JWT authentication. Application gives access to store your lists, as well as modification, adding items, and modifying them as well. 

## How to run Backend
For backend you'll need Java 19 and Maven installed in your system

1. Go to `Backend` folder and run `docker compose up
2. In terminal access container terminal using 
```
docker exec -it postgresql bash
```
3. create database:
```
psql -U admin
CREATE DATABASE tobebought;
```
4. In `./Backend/tobebought` install maven dependencies using `mvn install`
5. In the same folder run program using mvn `clean spring-boot:run`

## How to run Frontend
Frontend requires Node and npm.

1. Go to `./Frontend/tobebought`
2. Run `npm install`
3. Run `npm start`
4. Site should be available at `localhost:3000`
