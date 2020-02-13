MNIST-Neuralserv
==
##### An application of the MLP network that I built with the Java Standard Libraries

This project aims to make use of the MLP network that I created [here](https://github.com/reggiemcdonald/new-neural-net-number-reader)


### What it will be
The project will be a childrens learning tool. On the frontend, React will be used
to render a canvas to the screen. Children will be prompted to handwrite a random
number and the neural net will be used to determine whether it was written correctly.

Caregivers will be able to use the same account login to download the child's performance
over time into a Sheets doc via an Apps Script app.

### The Stack
On the backend: Postgres database, Spring Boot REST API, and JWT authentication with Spring Security.

On the frontend: Typescript, React, Skia CanvasKit

And an Apps Script app.

### TODO
- [x] Endpoint for scaling images to MNIST format
- [x] Endpoint for classifying images of numbers
- [x] Endpoint for admins to start network training
- [ ] Role-based authentication
- [ ] Basic frontend with canvaskit for drawing
- [ ] Apps Script app

### To Run
- Specify the username and password to the postgres instance that you have running
on your computer in `application.properties` (or make them environment variables)
- Ensure the port number of the database matches your configuration
- Run `mvn compile spring-boot:run`
