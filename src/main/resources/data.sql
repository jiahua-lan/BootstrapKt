insert into sys_user(id, username, enabled, locked, created_date)
values (1, 'root', true, false, current_timestamp());
insert into user_credential(id, credential)
values (1, '{noop}12345678');

insert into sys_user(id, username, enabled, locked, created_date)
values (5, 'mike', true, false, current_timestamp());
insert into user_credential(id, credential)
values (5, '{noop}12345678');

insert into sys_role(id, code, name)
values (10, 'ROOT', 'ROOT');
insert into sys_role(id, code, name)
values (11, 'USER', 'USER');
insert into sys_role(id, code, name)
values (12, 'ORGANIZATION_MANAGER', 'ORGANIZATION_MANAGER');
insert into sys_role(id, code, name)
values (13, 'MEMBER_MANAGER', 'MEMBER_MANAGER');
insert into sys_role(id, code, name)
values (14, 'POSITION_MANAGER', 'POSITION_MANAGER');

insert into sys_user_roles (users_id, roles_id)
values (1, 10);
insert into sys_user_roles (users_id, roles_id)
values (1, 11);

insert into sys_user_roles (users_id, roles_id)
values (5, 11);
insert into sys_user_roles (users_id, roles_id)
values (5, 12);
insert into sys_user_roles (users_id, roles_id)
values (5, 13);
insert into sys_user_roles (users_id, roles_id)
values (5, 14);

alter sequence hibernate_sequence restart with 1000;