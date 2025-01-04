# Recipes

This project was developed in Debian Linux, some command could vary depending on the os

## Database schema

This is the ER diagram of the database schema

![Entity Diagram](https://www.gravizo.com/svg?digraph%20G%20{%20%20%20%20rankdir=LR;%20%20%20%20node%20[shape=record];%20%20%20%20recipes%20[label=%22{recipes|id:%20int\ltitle:%20varchar%28255%29\ldescription:%20text\linstructions:%20text\lminutes:%20int\ldifficulty:%20varchar%28255%29\lvegetarian:%20boolean\lservings:%20int\lcreated:%20date\l}%22];%20%20%20%20ingredients%20[label=%22{ingredients|id:%20int\lname:%20varchar%28255%29\l}%22];%20%20%20%20recipe_ingredient%20[label=%22{recipe_ingredient|id:%20int\lrecipe_id:%20int\lingredient_id:%20int\lquantity:%20int\lunit:%20varchar%2850%29\l}%22];%20%20%20%20cart%20[label=%22{cart|id:%20int\lcreated:%20date\lenabled:%20boolean\l}%22];%20%20%20%20cart_recipe%20[label=%22{cart_recipe|id:%20int\lrecipe_id:%20int\lcart_id:%20int\l}%22];%20%20%20%20recipes%20-%3E%20recipe_ingredient%20[label=%221%20to%20many%22];%20%20%20%20ingredients%20-%3E%20recipe_ingredient%20[label=%221%20to%20many%22];%20%20%20%20recipes%20-%3E%20cart_recipe%20[label=%221%20to%20many%22];%20%20%20%20cart%20-%3E%20cart_recipe%20[label=%221%20to%20many%22];})

## PostgreSQL

Install postgresql

```bash
docker run -d --name r-postgres -p 5432:5432 -e POSTGRES_HOST_AUTH_METHOD=trust postgres:13
``` 

Connect to postgresql and run the script Recipes_DDL.sql

```bash
psql -h 127.0.0.1 -p 5432 -U postgres -f Recipes_DDL.sql
```

## Run the project

```bash
./gradlew bootRun
```

## Api access

Swagger UI is available at

[Swagger](http://localhost:8080/webjars/swagger-ui/index.html)

## Run tests

Unit

```bash
./gradlew unitTest
```

Integration

```bash
./gradlew integrationTest
```

