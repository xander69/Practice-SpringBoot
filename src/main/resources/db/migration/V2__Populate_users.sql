insert into users (id, username, password, email, active, activation_code, create_dt, change_dt)
values (1, 'admin', 'admin', 'admin@localhost.org', true, null, current_timestamp, current_timestamp),
       (2, 'user', 'user', 'user@localhost.org', true, null, current_timestamp, current_timestamp);

insert into user_role (user_id, roles)
values (1, 'ADMIN'), (1, 'USER'), (2, 'USER');

commit;

select setval('user_sequence', (select max(id) from users));