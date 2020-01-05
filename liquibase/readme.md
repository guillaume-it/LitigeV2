## Extract database schema
```
mvn liquibase:generateChangeLog
```

## Extract database schema avec data
```
mvn liquibase:generateChangeLog -Dliquibase.diffTypes=data
```

## Drop the database
```
mvn liquibase:dropAll
```