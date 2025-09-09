# DevLog

## Technologies and Libraries

For this project, I chose the following stack:
- **Front-end**: Vue 3 + Vite, Pinia, Vue Router, Axios, Bootstrap 5
- **Back-end**: Spring Boot 3 (Web, Security, Validation, Data JPA, Liquibase), JWT
- **DB**: PostgreSQL in Docker



## Milestone 1 — Setup and Scaffolding

On the frontend side, everything was smooth - I have already created Vue projects a few times, so generating one with Vite was straightforward. In my previous work I used Vuex, but since I have heard a lot about **Pinia** being the modern alternative, I decided to give it a try. 

The backend part was more challenging. It was my very first time working with **Spring Boot**, so I had to spend extra time learning. I watched some YouTube tutorials and checked the official Spring documentation. At first, I ran into issues with configuration (H2 vs PostgreSQL profiles mixing), but after some debugging and guidance I managed to split configurations and finally got the server running.  

Another challenge was Spring Security. By default, it locked everything and redirected me to the login page. To test the backend properly, I asked ChatGPT to help me with a configuration that allows skipping authentication for specific endpoints. That way I could access `/api/ping` and confirm the backend is working.  


## Milestone 2 — Authentication & JWT

This was my very first time working with **Spring Security**, so I had to study its structure and main concepts. I spent several hours reading official documentation and tutorials:  

- Spring Security reference guides (JWT, password encoding, authentication providers, UserDetailsService)  
- GeeksforGeeks articles on JPA repositories and UserDetailsService  
- Spring.io “Securing Web” guide  
- And most importantly, this tutorial that helped me build the project step by step:  
  [Implementing JWT Authentication in Spring Boot](https://medium.com/@victoronu/implementing-jwt-authentication-in-a-simple-spring-boot-application-with-java-b3135dbdb17b)

With this, I was able to create a working login endpoint and JWT filter.  

Some of the challenges I faced were: 
- At first I stored tokens in **localStorage**, but realized users stayed logged in even after closing the page or restarting Docker. Later, I moved everything to **sessionStorage**.  
- Understanding how to handle **401/403 errors** on the frontend was difficult. The Axios interceptor initially just rejected requests, but with ChatGPT’s help I learned how to redirect to `/login` and trigger logout automatically.  
- I had to implement **auto-logout** by decoding the token’s `exp` field. This was new to me, but it works now.  


## Milestone 3 — CRUD for Pets

Here I focused on the **Pet management** part. I had to learn about:  
- **DTOs** in Spring Boot ([Baeldung article](https://www.baeldung.com/java-bean-validation-not-null-empty-blank), [Roshan Farakate Medium guide](https://medium.com/@roshanfarakate/understanding-dtos-in-spring-boot-a-comprehensive-guide-20e2b8101ee6))  
- **CORS** integration ([Spring docs](https://docs.spring.io/spring-security/reference/servlet/integrations/cors.html))  
- Validations (`@NotBlank`, regex patterns, max length)  

Some of the challenges I faced were:  
- At first, the **delete endpoint** was wrong (`PATCH /pets/{id}/deactivate`). Later I changed it to a proper `DELETE /pets/{id}`.  
- Pets did not disappear after deletion, because I didn’t filter by `active=true`. This was fixed after introducing **soft-delete** (marking pets inactive).  
- Implementing **real-time ID number availability check** (`check-identification`) was tricky, both on the backend (repository methods) and frontend (live API calls).  


## Milestone 4 — UI and UX improvements

Working on UI was easier for me as I was familiar with it:  
- I used **Bootstrap 5** documentation and w3schools examples.  
- Added **toast notifications** for success/error/info.  
- Built **ConfirmModal** for delete confirmation.  
- Added **route guards** with `meta.requiresAuth` to protect private pages.  

The harder part was **validation UX**:  
- Initially, forms allowed invalid input (letters in IDs, digits in fur color). Later, I implemented stricter validation rules both on backend DTOs and frontend with Vue form validation.  
- Another issue was the **login form**: when entering invalid credentials, the page flickered and reloaded. I fixed this so now the error is shown inline without reload.  


## Milestone 5 — Dockerization and Integration

I wasn’t sure what **“full Dockerization”** meant at first, so I followed [this tutorial](https://www.milanwittpohl.com/projects/tutorials/full-stack-web-app/dockerizing-our-front-and-backend).  

Steps done:  
- Multi-stage Docker build for backend (Maven + JRE).  
- Dockerized frontend (Vite build → Nginx).  
- Docker Compose: PostgreSQL + backend + frontend, connected via a network.  

Problems:  
- For a while, **frontend kept getting 403** errors when talking to backend. It turned out I had duplicate `/api` paths in nginx + axios config. After cleanup, everything worked.  
- Another challenge was making sure environment variables and DB migration (Liquibase) worked consistently across containers.  

---

## Milestone 6 — Polishing & Docs

In the final stage, I focused on **documentation** and small polish:  
- Wrote **README.md** with project description, stack, and instructions for running with Docker.  
- Writing this **DevLog.md** to reflect learning steps, challenges, and progress.  

One thing I tried but dropped was trying to set up **tests** (JUnit for backend, Vitest for frontend). I spent ~3 hours, but couldn’t get stable results, so I removed them for now.  

Future improvements if I had more time:  
- Proper **unit and integration tests** (especially for login and CRUD).  
- Fix the issue that lets a logged-in user still return to `/login` without logout.  
- More **advanced validation messages** (e.g. localized error texts).  
- More **beautiful UI** with a stronger identity instead of plain Bootstrap defaults.  

---

### Time log
- **Day 1**: ~3 hours coding setup + ~2 hours studying Spring Boot basics
- **Day 2**: ~5 hours coding + ~2 hours studying Spring Boot 
- **Day 3**: ~3 hours coding + ~1.5 hours studying Spring Boot and Liquibase
- **Day 4**: ~5 hours coding + ~3 hours studying Spring Boot Security
- **Day 5**: ~5 hours coding + ~2 hours studying Spring Boot Security and Docker
- **Day 6**: ~4 hours coding + ~1 hours studying Spring Boot Security and Docker
- **Day 7**: ~4 hours coding + ~2 hours studying Bootstrap
- **Day 8**: ~6 hours coding + ~3 hours studying Bootstrap and Vue
- **Day 9**: ~4 hours coding + ~3 hours studying Testing