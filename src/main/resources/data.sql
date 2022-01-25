insert into sys_user(id, username, password, enabled, locked)
values (1, 'root', '{noop}12345678', true, false);

insert into sys_role(id, code, name)
values (2, 'ROOT', 'ROOT');
insert into sys_role(id, code, name)
values (3, 'USER', 'USER');

insert into sys_user_roles (users_id, roles_id)
values (1, 2);
insert into sys_user_roles (users_id, roles_id)
values (1, 3);

alter sequence hibernate_sequence restart with 10;