
[Restaurant voting](https://github.com/Timofei725/restaurant-voting.git)
===============================

#### The task was: 

Build a voting system for deciding where to have lunch.

* 2 types of users: admin and regular users
* Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)
* Menu changes each day (admins do the updates)
* Users can vote for a restaurant they want to have lunch at today
* Only one vote counted per user
* If user votes again the same day:
    - If it is before 11:00 we assume that he changed his mind.
    - If it is after 11:00 then it is too late, vote can't be changed

Each restaurant provides a new menu each day.



-------------------------------------------------------------
- Stack: [JDK 17](http://jdk.java.net/17/), Spring Boot 2.5, Lombok, H2, Caffeine Cache, Swagger/OpenAPI 3.0, Mapstruct, Liquibase
- Run: `mvn spring-boot:run` in root directory.
-----------------------------------------------------
[REST API documentation](http://localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config)  
Credentials:
```
User_First:  user@gmail.ru / password
Admin: admin@gmail.com / admin
User_Second:  user@gmail.ru / password2

User_First and Admin have already voted today, but User_Second is able to vote 
```

