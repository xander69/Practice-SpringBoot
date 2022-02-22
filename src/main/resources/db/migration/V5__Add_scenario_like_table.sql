create table scenario_like
(
    user_id     bigint not null references users,
    scenario_id bigint not null references scenario,
    primary key (user_id, scenario_id)
)