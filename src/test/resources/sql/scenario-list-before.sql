delete
from scenario;

insert into scenario (id, name, descr, user_id)
values (1, 'Scenario 1', 'Description of scenario 1', 1),
       (2, 'Scenario 2', 'Description of scenario 2', 1),
       (3, 'Scenario 3', 'Description of scenario 3', 1),
       (4, 'Scenario 4', 'Description of scenario 4', 2),
       (5, 'Scenario 33', 'Description of scenario 33', 1);

alter sequence scenario_sequence restart with 10;