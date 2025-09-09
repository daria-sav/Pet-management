# DevLog

## Technologies and Libraries

For this project, I chose the following stack:
- **Front-end**: Vue 3 + Vite, Pinia, Vue Router, Axios, Bootstrap 5
- **Back-end**: Spring Boot 3 (Web, Security, Validation, Data JPA, Liquibase), JWT
- **DB**: PostgreSQL in Docker


---

## Milestone 1 — Setup and Scaffolding

On the frontend side, everything was smooth - I have already created Vue projects a few times, so generating one with Vite was straightforward. In my previous work I used Vuex, but since I have heard a lot about **Pinia** being the modern alternative, I decided to give it a try. 

The backend part was more challenging. It was my very first time working with **Spring Boot**, so I had to spend extra time learning. I watched some YouTube tutorials and checked the official Spring documentation. At first, I ran into issues with configuration (H2 vs PostgreSQL profiles mixing), but after some debugging and guidance I managed to split configurations and finally got the server running.  

Another challenge was Spring Security. By default, it locked everything and redirected me to the login page. To test the backend properly, I asked ChatGPT to help me with a configuration that allows skipping authentication for specific endpoints. That way I could access `/api/ping` and confirm the backend is working.  


---

### Time log
- **Day 1**: ~3 hours coding setup + ~2 hours studying Spring Boot basics
- **Day 2**: ~5 hours coding + ~2 hours studying Spring Boot 
- **Day 3**: ~3 hours coding + ~1.5 hours studying Spring Boot and Liquibase
- **Day 4**: ~2 hours coding + ~2 hours studying Spring Boot Security
- **Day 5**: ~4 hours coding + ~2 hours studying Spring Boot Security
- **Day 6**: ~4 hours coding + ~1 hours studying Spring Boot Security and Docker
- **Day 7**: ~4 hours coding + ~1 hours studying Spring Boot Security and Docker
- **Day 8**: ~4 hours coding + ~2 hours studying Bootstrap
- **Day 9**: ~6 hours coding + ~3 hours studying Bootstrap and Vue
- **Day 10**: ~4 hours coding + ~3 hours studying Testinggit add