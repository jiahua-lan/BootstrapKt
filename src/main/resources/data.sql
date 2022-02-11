insert into sys_user(id, username, enabled, locked)
values (1, 'root', true, false);

insert into user_credential(id, credential)
values (1, '{noop}12345678');

insert into sys_user(id, username, enabled, locked)
values (5, 'mike', true, false);

insert into user_credential(id, credential)
values (5, '{noop}12345678');

insert into sys_role(id, code, name)
values (2, 'ROOT', 'ROOT');
insert into sys_role(id, code, name)
values (3, 'USER', 'USER');

insert into sys_user_roles (users_id, roles_id)
values (1, 2);
insert into sys_user_roles (users_id, roles_id)
values (1, 3);

insert into sys_user_roles (users_id, roles_id)
values (5, 3);

insert into organization_type(id, code, name)
values (20, 'SHOP', 'SHOP');

insert into organization(id, code, name, type_id, creator_id, created_date)
values (30, 'MAIN_STORE', 'MAIN_STORE', 20, 1, current_timestamp());

insert into position (id, code, name, organization_id)
values (40, 'STORE_MANAGER', 'STORE_MANAGER', 30);

insert into position (id, code, name, organization_id)
values (41, 'STORE_STAFF', 'STORE_MANAGER', 30);

insert into member(id, code, name, account_id)
values (50, 'EMPLOYEE_01', 'CAT', 1);

insert into member_positions (members_id, positions_id)
values (50, 40);

insert into sys_role(id, code, name)
values (31, 'ORGANIZATION_MANAGER', 'ORGANIZATION_MANAGER');

insert into sys_role(id, code, name)
values (32, 'MEMBER_MANAGER', 'MEMBER_MANAGER');

insert into sys_role(id, code, name)
values (33, 'POSITION_MANAGER', 'POSITION_MANAGER');


insert into sys_user_roles (users_id, roles_id)
values (5, 31);
insert into sys_user_roles (users_id, roles_id)
values (5, 32);
insert into sys_user_roles (users_id, roles_id)
values (5, 33);

alter sequence hibernate_sequence restart with 100;