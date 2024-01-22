# DAI PW4 - ColorGrid

## Introduction

This repository hosts the code for a webapp built with javalin.
This webapp displays a grid of pixels as JSON where some pixels can be colored with a rgb color.
(and if time permits, the grid is displayed in a more visually appealing way)
The grid can be modified with a HTTP message.

The website can be found here : https://colorgrid.dedyn.io

### Group Composition
<<<<<<< HEAD

//TODO critère 8 à check
//TODO critère 12 à check
//TODO critère 14 à check (devrait jouer mais sait-on jamais)
//TODO critères 19-25 : présentation

- Jeremiah Steiner - //TODO
=======
- Jeremiah Steiner - 
>>>>>>> f3b3659 (readme proto)
- Jonas Troeltsch - Responsable VM
- Sarah Jallon - README (protocol def) + discuss endpoints paths + body responses / status
- Simon Guggisberg - Javalin

<<<<<<< HEAD
## API

//TODO document POST/PATCH/GET/DELETE
=======

# Application

## What is the app for ?

The goal of this is app is to interact with a "pixel grid". Each "pixel" has a color and an associated char.
A client can send CRUD operations to a server in order to create, read, update, or delete the pixel grid.

## Protocol and API design

This part describes the possible interactions between a client (user) and the application using the HTTP protocol and the json format.

We will architecture the application using one domain with different parameters.

The json domain that contains complete grid of pixels.
The json?x=a parameter that contains the a row of the grid
The json?y=b parameter that contains the b column of the grid
the json?x=a&y=b parameters that contains a cell located at (a;b)

The cell ressource has the following properties
color - the color of the cell
text - the text contained in the cell

The grid ressource is a 2 dimensions matrix of cells, making it a simple table.

The grid resource has the following operations:

Create a new grid of x rows and y cols.
Get the first grid
Get a line given its index
Get a row given its index
Update a cell (x;y) of the first grid with the given color and the given text
Delete the first grid


The grid resource has the following endpoints and for each endpoint the data is transmitted via the content of the request.

POST /grids  - Create a new grid <br>
GET /grids - Get the first grid <br>
GET /grids/{id} - Get the grid based on an id <br>
GET /grids/{id}?x={x} - Get a row by its index <br>
GET /grids/{id}?y={y} - Get a col by its index <br>
GET /grids/{id}?x={x}&y={y} - Get a cell by its location <br>
PATCH /grids/{id}  - Update a cell <br>
DELETE /grids/{id} - Delete a user <br>


## Endpoints

### Create a new grid

- `POST /grids`

Create a new grid.

#### Request

The request body must contain a JSON object with the following properties:

- `x` - The number of rows
- `y` - The number of columns

#### Response

The response body contains a JSON object with the following properties:

- `x` - The number of rows
- `y` - The number of columns

#### Status codes

- `201` (Created) - The grid has been successfully created
- `418` (I'm a teapot) - The request body is invalid (x and y either missing or negative values)
- `500` (Server error) - Malformed json

### Get every grid

- `GET /grids`

Get all the grids.

#### Request

The request has no parameters

#### Response

The response body contains a JSON array with the following properties:


#### Status codes

- `200` (OK) - The grids have been successfully retrieved

### Get one grid

- `GET /grids/{id}`

Get one grid by its ID.

#### Request

The request path must contain the ID of the grid.

#### Response

The response body contains a JSON object containing the  with the given id

#### Status codes

- `200` (OK) - The grid has been successfully retrieved
- `404` (Not Found) - The grid does not exist


### Get one row of a grid

- `GET /grids/{id}?x={x}`

Get one row of a grid by its index.

#### Request

The request path must contain the ID of the grid and the index of the row.

#### Response

The response body contains a JSON object with the following properties:

#### Status codes

- `200` (OK) - The row has been successfully retrieved
- `404` (Not Found) - The row does not exist

### Get one col of a grid

- `GET /grids/{id}?y={y}`

Get one col of a grid by its index.

#### Request

The request path must contain the ID of the grid and the index of the col.

#### Response

The response body contains a JSON object with the following properties:

#### Status codes

- `200` (OK) - The row has been successfully retrieved
- `404` (Not Found) - The row does not exist

### Get one cell of a grid

- `GET /grids/{id}?x={x}&y={y}`

Get one cell of a grid by its (x;y) location.

#### Request

The request path must contain the ID of the grid , the index of the row and the index of the col.

#### Response

The response body contains a JSON object with the following properties:

#### Status codes

- `200` (OK) - The row has been successfully retrieved
- `404` (Not Found) - The row does not exist

### Update a grid

- `PATCH /grids/{id}`

Update a grid by its ID.

#### Request

The request path must contain the ID of the grid.

The request body must contain a JSON object with the following properties:

- `x` - x index of the cell
- `y` - y index of the cell
- `color` - color of the cell
- `text` - text of the cell

#### Response

The response body contains a JSON object with the following properties:

#### Status codes

- `200` (OK) - The grid has been successfully updated
- `400` (Bad Request) - The request body is invalid
- `404` (Not Found) - The user does not exist

### Delete a grid

- `DELETE /grids/{id}`

Delete a grid by its ID.

#### Request

The request path must contain the ID of the grid.

#### Response

The response body is empty.

#### Status codes

- `204` (No Content) - The user has been successfully deleted
- `404` (Not Found) - The user does not exist


## Interact with the web app

Here we are using curl to interact with our endpoints.

### Create a new grid

Request

```
curl -i -X POST -H "Content-Type: application/json" -d '{"x": 2, "y": 3}' "http://host.docker.internal:7070/json"
```

Response
```

```
### Get the every grid
Request

```
curl -i -X POST -H "Content-Type: application/json" -d '{"x": 2, "y": 3}' "http://host.docker.internal:7070/json"
```

Response
```

```

### Get a grid by id
Request

```
curl -i -X POST -H "Content-Type: application/json" -d '{"x": 2, "y": 3}' "http://host.docker.internal:7070/json"
```

Response
```

```

### Get a row by its index
Request

```
curl -i -X POST -H "Content-Type: application/json" -d '{"x": 2, "y": 3}' "http://host.docker.internal:7070/json"
```

Response
```

```

### Get a col by its index
Request

```
curl -i -X POST -H "Content-Type: application/json" -d '{"x": 2, "y": 3}' "http://host.docker.internal:7070/json"
```

Response
```

```

### Get a cell by its indexRequest

```
curl -i -X POST -H "Content-Type: application/json" -d '{"x": 2, "y": 3}' "http://host.docker.internal:7070/json"
```

Response
```

```

### Update a cellRequest

```
curl -i -X POST -H "Content-Type: application/json" -d '{"x": 2, "y": 3}' "http://host.docker.internal:7070/json"
```

Response
```

```

### Delete a userRequest

```
curl -i -X POST -H "Content-Type: application/json" -d '{"x": 2, "y": 3}' "http://host.docker.internal:7070/json"
```

Response
```

```

>>>>>>> f3b3659 (readme proto)

## Explanations

### Installation of the server

#### Update the virtual machine and install `apache2-utils`

Once you have access to the virtual machine, you can update it with the
following commands:

```sh
# Update the list of packages
sudo apt update

# Upgrade the packages
sudo apt upgrade
```

This will ensure that the virtual machine is up-to-date.

Install `apache2-utils` with the following command:

```sh
# Install apache2-utils
sudo apt install apache2-utils
```

#### Install Docker and Docker Compose on the virtual machine

Install Docker and Docker Compose as seen in the
[Docker and Docker Compose](https://github.com/heig-vd-dai-course/heig-vd-dai-course/tree/main/10-docker-and-docker-compose)
chapter.

As the virtual machine is running Linux, follow the instructions for Linux. Do
not forget the post-installation steps. This will ensure that Docker and Docker
Compose start automatically when the virtual machine is rebooted and that you
can use Docker without the need of using `sudo` (= admin) each time.

Check that you can run the `hello-world` Docker image as seen in the Docker and
Docker Compose chapter to ensure you can use Docker on the virtual machine
without the need of `sudo`.

#### Open the ports on the virtual machine

You can open the port of the virtual machine using
[UFW](https://en.wikipedia.org/wiki/Uncomplicated_Firewall) with the following
command:

```sh
# Open the HTTP protocol on the virtual machine
sudo ufw allow http

# Open the HTTPS protocol on the virtual machine
sudo ufw allow https
```

### Configure DNS zone
To do this, you need to have a domain name and a DNS provider.

Using Desec as DNS provider, you need to add :
- A record for the root domain pointing to the server ipv4 address
- A record for the subdomain `*` pointing to the server ipv4 address
- AAAA record for the root domain pointing to the server ipv6 address (used by letsencrypt)
- NS records for the root domain pointing to the desec nameservers (automatically generated by desec)

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

Go to the `deployment/api` folder and run the following commands after a new version of the project has been pushed to
the github repository (see: [Build and publish api with Docker](#build-and-publish-api-with-docker)):

```bash
docker compose down
docker compose pull
docker compose up -d
```

### Build and publish api with Docker

At the root of the project, run these commands :

```bash
# Build
docker build . -t ghcr.io/jonastroeltsch/pw4:latest

# Publish
docker push ghcr.io/jonastroeltsch/pw4:latest
```

<<<<<<< HEAD
### Interact with the web app

//TODO comment on the examples and make sure they work
//TODO add output examples

```bash
curl -i -X POST -H "Content-Type: application/json" -d '{"x": 2, "y": 3}' "https://colorgrid.dedyn.io/json"
```

```bash
curl -i -X PATCH -H "Content-Type: application/json" -d '{"x": 1, "y": 2, "cell": {"color": "220022", "text": "Hello, world!"}}' "https://colorgrid.dedyn.io/json"        
```

Then head over to :
- https://colorgrid.dedyn.io/json
- https://colorgrid.dedyn.io/json?x=1
- https://colorgrid.dedyn.io/json?y=2
- https://colorgrid.dedyn.io/json?x=1&y=2

```bash
curl -X DELETE -H "Content-Type: application/json" "https://colorgrid.dedyn.io/json"
```

=======
>>>>>>> f3b3659 (readme proto)
----------------------------------

//TODO remove below, that was useful for developing locally

## Temporary

Run the Package as JAR running configuration then :

```bash
java -jar target/DAI-PW4-1.0-SNAPSHOT.jar
```

```bash
curl -i -X POST -H "Content-Type: application/json" -d '{"x": 2, "y": 3}' "http://host.docker.internal:7070/json"
```

```bash
curl -i -X PATCH -H "Content-Type: application/json" -d '{"x": 1, "y": 2, "cell": {"color": "220022", "text": "Hello, world!"}}' "http://host.docker.internal:7070/json"        
```

Then head over to : 
- http://localhost:7070/json
- http://localhost:7070/json?x=1
- http://localhost:7070/json?y=2
- http://localhost:7070/json?x=1&y=2

```bash
curl -X DELETE -H "Content-Type: application/json" "http://host.docker.internal:7070/json"
```
