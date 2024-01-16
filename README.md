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
