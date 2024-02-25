# SDG-Local-Delights
Welcome to SDG-Local-Delights! This is a project that aims to build a platform for local businesses to showcase their products and services. This project is part of TAAS course of Computer Science Maaster's Degree at University of Turin.

## Indice

- [Used Technologies](#used-technologies)
- [Get started](#get-started)
- [Run the project with Docker](#run-the-project-with-docker)
- [Run the project on Kubernetes](#run-the-project-on-kubernetes)
- [How to use the application](#how-to-use-the-application)

## Used Technologies
In this project are used the following technologies:

### Frontend is built with:
- [Vue.js](https://vuejs.org/) - The Progressive JavaScript Framework
- [Vite](https://vitejs.dev/) - Next generation frontend tooling
- [Tailwind CSS](https://tailwindcss.com/) - A utility-first CSS framework
- [Axios](https://axios-http.com/) - Promise based HTTP client for the browser and node.js
- [Vue Router](https://router.vuejs.org/) - The official router for Vue.js
- [Vuex](https://vuex.vuejs.org/) - State management pattern + library for Vue.js applications
- [Flowbite](https://flowbite.com/) - Tailwind CSS components and templates
- [Openstreetmap](https://www.openstreetmap.org/) - Interactive Map integration
- [Leaflet](https://leafletjs.com/) - JavaScript library for mobile-friendly interactive maps and **reverse geocoding**

### Backend
- [Spring Boot](https://spring.io/projects/spring-boot) - for building the RESTful APIs
- [Spring Security](https://spring.io/projects/spring-security) - for authentication and authorization and **oauth2** support
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa) - for database access
- [Spring Web](https://spring.io/guides/gs/serving-web-content/) - for RESTful APIs
- [Lombok](https://projectlombok.org/) - for reducing boilerplate code
- [PostgreSQL](https://www.postgresql.org/) - Relational Database
- [Docker](https://www.docker.com/) - for local development
- [Docker Compose](https://docs.docker.com/compose/) - for local development
- [Maven](https://maven.apache.org/) - Dependency Management
- [JWT](https://jwt.io/) - JSON Web Tokens
- [RabbitMQ](https://www.rabbitmq.com/) - MQTT broker for messaging
- [Eureka](https://spring.io/guides/gs/service-registration-and-discovery/) - Service registration and discovery
- [OpenAPI](https://swagger.io/specification/) - API documentation
- [Kubernetes](https://kubernetes.io/) - for deployment and container orchestration

### Miscellaneous
- [Apidog](https://apidog.com/) - API documentation and design
- [Git](https://git-scm.com/) - Version control system


## Get started
First of all, you need to have installed the following tools:
- [Docker](https://www.docker.com/) Docker Engine
- [Docker Compose](https://docs.docker.com/compose/) 
- [Docker Desktop](https://www.docker.com/products/docker-desktop)
- [Kubernets on Docker Desktop](https://docs.docker.com/desktop/kubernetes/) you can enable it from Docker Desktop settings
- [minikube](https://minikube.sigs.k8s.io/docs/start/) you can use it instead of Docker Desktop but requires some changes described in the [Run the project on Kubernetes](#run-the-project-on-kubernetes) section 

You can choose to run the project in two ways:
- [Run the project with Docker](#run-the-project-with-docker)
- [Run the project on Kubernetes](#run-the-project-on-kubernetes)

In both cases, you need first to clone the repository:

```git clone https://github.com/dipada/SDG-Local-Delights.git ```

### Run the project with Docker
Using Docker Compose, you can run the project with the following command:

```docker-compose up -d``` 

(Use -d flag to run it in detached mode)

If you want to build the project from scratch, you can use the following command:

```docker-compose build --parallel --no-cache && docker-compose up -d```

(Use -d flag to run it in detached mode)


### Run the project on Kubernetes
As sayd at begin you have to enable Kubernetes on Docker Desktop.

#### miniKube users
Switching to Docker Desktop Due to QEMU Driver Limitations in Minikube
In our journey of developing and testing applications within a local Kubernetes setup, we have navigated through various challenges and limitations. One such challenge arises from the limitations associated with using the QEMU driver for Minikube. These limitations primarily involve networking complexities and the inability to seamlessly expose services for external access, critical aspects for real-time application testing and development.
This section provides a concise explanation of the motivations behind choosing Docker Desktop over Minikube due to specific limitations encountered with the QEMU driver, focusing on the aspects of networking and service exposure.

If you want to use minikube you have to change the kubernetes context.
Ensure you installed kubernetes extension for docker desktop. You can find instructions [here](https://docs.docker.com/desktop/kubernetes/).

After all, check the available contexts with the following command:

```kubectl config get-contexts```

Then you can switch to docker-desktop context with the following command:

```kubectl config use-context docker-desktop```

#### All users - start
Here we assume you have installer docker desktop and enabled kubernetes on it. Or you have installed minikube and switched to docker-desktop context. And cloned the repository.

##### Start
After satisfying the previous requirements, you can **run** the project executing the following script:

```./k8s-up.sh```

##### Stop
If you want to **stop** the project, you can execute the following script:

```./k8s-down.sh```

##### Delete pvcs
And if you want to **delete** the pvcs, you can execute the following script:

```kubectl delete -f all-pvcs.yaml```

## How to use the application
After running the project on Docker or Kubernets, you can access the application at the following URL:

```http://localhost:30073```

Here you will find the landing page of the application. When login with google account or after creating a new user you can:
- Create a new shop
- Add some products with images, description, price and category etc
- View the shops and products on the map
- Make some orders
- Mark order for delivery
- Take and deliver some orders

This a subset of the functionalities that the application provides, just to give you an idea of what you can do with it.