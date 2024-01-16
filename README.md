# DAI PW4 - ColorGrid

## Introduction

This repository hosts the code for a webapp built with javalin.
This webapp displays a grid of pixels as JSON where some pixels can be colored with a rgb color.
(and if time permits, the grid is displayed in a more visually appealing way)
The grid can be modified with a HTTP message.

The website can be found here : https://colorgrid.dedyn.io

### Group Composition
- Jeremiah Steiner - //TODO
- Jonas Troeltsch - Responsable VM
- Sarah Jallon - //TODO 
- Simon Guggisberg - README & Javalin

## How to

//TODO

### Installation of the server

//TODO

### Deployment of the web app

#### First deployment

To deploy the app on the server, you need to clone this repository on the server and fill the .env  and the 
dns-challenge.env files in the `deployment/traefik` folder with the correct values. follow instructions in the 
[README.md](deployment/traefik/README.md)

Then you run the traefik container with the following command :

```bash
docker compose up -d
```
In the `deployment/api` folder, you need to fill the .env file with the correct values 
And then you can run the following commands to deploy the api :

```bash
docker compose up -d
```
#### Update project

Go to the ´deployment/api´ folder and run the following commands after a new version of the project has been pushed to
the github repository (see: [Build and publish web app with Docker](#build-and-publish-web-app-with-docker)):

```bash
docker compose down
docker compose pull
docker compose up -d
```

### Configure DNS zone

//TODO

### Build and publish web app with Docker

```bash
# Build
docker build . -t ghcr.io/jonastroeltsch/pw4:latest

# Publish
docker push ghcr.io/jonastroeltsch/pw4:latest
```

### Interact with the web app

//TODO

----------------------------------

## Temporary

Run the Package as JAR running configuration then :

```bash
java -jar target/DAI-PW4-1.0-SNAPSHOT.jar
```

Then head over here : 
- http://localhost:8080/json
- http://localhost:8080/json?x=1&y=2
