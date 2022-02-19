## Spring Boot Start

### Stack

* Maven
* Java 8
* Spring Boot
* Spring Data JPA
* Spring MVC
* Spring Security
* Spring Mail
* Spring Test
* Template Engines: thymeleaf, mustache, freemarker
* Bootstrap 5
* FlyWay

### References

1. Spring Boot: делаем простое веб приложение на Java (простой сайт)

   https://www.youtube.com/watch?v=jH17YkBTpI4

2. Spring Boot JPA (Hibernate): добавляем базу данных в веб приложение на Java (простой сайт)

   https://www.youtube.com/watch?v=nyFLX3q3poY

   `docker run -d -e POSTGRES_PASSWORD=postgres --name home_pg -p 5432:5432 postgres`

3. Spring Boot Security: добавляем регистрацию и авторизацию пользователей в приложение (простой сайт)

   https://www.youtube.com/watch?v=WDlifgLS8iQ

4. Spring Boot Jpa (Hibernate): добавляем связи между таблицами базы данных (one to many)

   https://www.youtube.com/watch?v=PpoOoR55Ypw

5. Spring Boot Freemarker: подключаем шаблонизатор Freemarker

   https://www.youtube.com/watch?v=8MlXahJXLFg

6. Spring Boot Security: добавляем панель администратора и роли пользователей, ограничиваем доступ

   https://www.youtube.com/watch?v=6dteOGWy4uk

7. Spring Boot MVC: загрузка файлов на сервер и раздача статики

   https://www.youtube.com/watch?v=bmMWrTMB5uo

8. Spring Boot: оформляем UI с Bootstrap

   https://www.youtube.com/watch?v=a51jGwoTNmI

9. Spring Boot Mail: рассылка почты пользователям, активация аккаунта

   https://www.youtube.com/watch?v=yBXs_gtSmUc
   https://temp-mail.org/

10. Spring Boot FlyWay: миграции БД, профиль пользователя

    https://www.youtube.com/watch?v=ArM7nCys4hY

    Для FlyWay требуется пустая схема, чтобы он заработал.
    Поэтому сначала дропаем БД:
     ```postgresql
     drop database <database_name>;
     ```
    Потом создаем её:
     ```postgresql
     create database <database_name>;
     ```
    Если при дропе вылезел ошибка типа:
    > [55006] ОШИБКА: база данных "<database_name>" занята другими пользователями

    то нужно посмотреть активные сессии с помощью запроса:
     ```postgresql
     select *
     from pg_stat_activity
     where pg_stat_activity.datname = '<database_name>';
     ```
    и киллнуть их с помощью запроса:
     ```postgresql
     select pg_terminate_backend (pg_stat_activity.pid)
     from pg_stat_activity
     where pg_stat_activity.datname = '<database_name>';
     ```
    потом повторить дроп БД.
    Посмотреть историю миграций:
    ```postgresql
    select * from flyway_schema_history;
    ```
