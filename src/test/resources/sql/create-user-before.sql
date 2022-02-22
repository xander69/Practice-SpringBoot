delete from user_role;
delete from users;

insert into users (id, username, password, active)
values (1, 'admin', 'admin', true),
       (2, 'user', 'user', true);

insert into user_role (user_id, roles)
values (1, 'USER'),
       (1, 'ADMIN'),
       (2, 'USER');

create extension if not exists pgcrypto;

update users set password = crypt(password, gen_salt('bf', 8));
