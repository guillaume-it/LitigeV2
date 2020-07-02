#https://medium.com/@wkrzywiec/build-and-run-angular-application-in-a-docker-container-b65dbbc50be8

FROM node:12.7-alpine AS build
WORKDIR /usr/src/app
RUN pwd
COPY package.json ./
RUN npm install
COPY . .
RUN npm run build