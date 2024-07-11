## How to Run
1. Clone the repository
2. Make sure that docker is running on your machine
3. Run the following command in the terminal
    ```bash
    docker-compose up
    ```
4. After the containers are up and running, run the following command to create the database tables
    ```bash
    ./gradlew quarkusDev
    ```
5. The application is now running on http://localhost:8080
6. You can acces the swagger documentation on http://localhost:8080/docs
7. You can access the database with the following credentials:
    - URL: jdbc:postgresql://localhost:15111/postgres
    - User: postgres
    - Password: secret99
8. You can login with admin credentials:
    - Email: admin@gmail.com
    - Password: User#1234