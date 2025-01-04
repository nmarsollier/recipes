# Recipes

This project was developed in Debian Linux, some command could vary depending on the os

## Database schema

This is the ER diagram of the database schema

![Entity Diagram](https://g.gravizo.com/svg?digraph%20G%20%7B%0A%20%20%20%20node%20%5Bshape%3Drecord%5D%3B%0A%0A%20%20%20%20recipes%20%5Blabel%3D%22%7Brecipes%20%7C%20id%3A%20int%20%5Cl%20title%3A%20varchar(255)%20%5Cl%20description%3A%20text%20%5Cl%20instructions%3A%20text%20%5Cl%20minutes%3A%20int%20%5Cl%20difficulty%3A%20varchar(255)%20%5Cl%20vegetarian%3A%20boolean%20%5Cl%20servings%3A%20int%20%5Cl%20created%3A%20date%20%5Cl%20price_in_cents%3A%20int%20%5Cl%7D%22%5D%3B%0A%20%20%20%20ingredients%20%5Blabel%3D%22%7Bingredients%20%7C%20id%3A%20int%20%5Cl%20name%3A%20varchar(255)%20%5Cl%7D%22%5D%3B%0A%20%20%20%20recipe_ingredient%20%5Blabel%3D%22%7Brecipe_ingredient%20%7C%20id%3A%20int%20%5Cl%20recipe_id%3A%20int%20%5Cl%20ingredient_id%3A%20int%20%5Cl%20quantity%3A%20int%20%5Cl%20unit%3A%20varchar(50)%20%5Cl%7D%22%5D%3B%0A%20%20%20%20cart%20%5Blabel%3D%22%7Bcart%20%7C%20id%3A%20int%20%5Cl%20total_in_cents%3A%20int%20%5Cl%20created%3A%20date%20%5Cl%20enabled%3A%20boolean%20%5Cl%7D%22%5D%3B%0A%20%20%20%20cart_recipe%20%5Blabel%3D%22%7Bcart_recipe%20%7C%20id%3A%20int%20%5Cl%20recipe_id%3A%20int%20%5Cl%20cart_id%3A%20int%20%5Cl%7D%22%5D%3B%0A%0A%20%20%20%20recipes%20-%3E%20recipe_ingredient%20%5Blabel%3D%221%20to%20many%22%5D%3B%0A%20%20%20%20ingredients%20-%3E%20recipe_ingredient%20%5Blabel%3D%221%20to%20many%22%5D%3B%0A%20%20%20%20recipes%20-%3E%20cart_recipe%20%5Blabel%3D%221%20to%20many%22%5D%3B%0A%20%20%20%20cart%20-%3E%20cart_recipe%20%5Blabel%3D%221%20to%20many%22%5D%3B%0A%7D)

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

