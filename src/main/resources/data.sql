insert into sys_user(id, username, enabled, locked)
values (1, 'root', true, false);

insert into user_credential(id, credential)
values (1, '{noop}12345678');

insert into sys_role(id, code, name)
values (2, 'ROOT', 'ROOT');
insert into sys_role(id, code, name)
values (3, 'USER', 'USER');

insert into sys_user_roles (users_id, roles_id)
values (1, 2);
insert into sys_user_roles (users_id, roles_id)
values (1, 3);

alter sequence hibernate_sequence restart with 10;