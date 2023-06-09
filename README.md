# Items application

Simple small application utilizing basic components like Java, Spring Boot, Spring JPA, Liquibase, SQL, H2, Postgres and JUnit. 
Application can support SQL database like H2 and Postgres to store data created within application.
Output of application is server that will open several REST API endpoints that can receive JSON payloads of objects.

## Buzzwords
Java, Spring Boot, Spring JPA, REST API, JSON, XML, SQL, H2, Postgres, Liquibase, JUnit, Postman

## Goal
- Gain knowledge of above-mentioned technologies and deepen understanding of them
- Try to meet predefined test conditions of task
- Have fun implementing application

## How to run
1. Build application using `maven`:
```bash
mvn clean install
```
2. Run build artifact located usually in `target` folder:
```bash
java -jar target/item-app-0.0.1-SNAPSHOT.jar
```

### Configurations
1. Set limit size: `--item.listing.defaults.limit`, default 10
2. Set offset size: `--item.listing.defaults.offset`, default 0
3. Set scheduling timer: `--item.deletion.delay`, default 60000

## Template request
Requests valid for the application are done in Postman collection template file: `src/test/resources/itemsApp.postman_collection.json`