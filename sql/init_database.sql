--删除权限数据
DELETE FROM auth_user_authorities;
DELETE FROM auth_group_authorities;
DELETE FROM auth_group_members;
DELETE FROM auth_userinfo;
DELETE FROM auth_user;
DELETE FROM auth_group;
DELETE FROM auth_authority;

--初始数据
--角色(权限)设定
INSERT INTO auth_authority (name,remark) VALUES ('ROLE_ADMIN','管理员');
INSERT INTO auth_authority (name,remark) VALUES ('ROLE_WRITER','作者用户');
INSERT INTO auth_authority (name,remark) VALUES ('ROLE_EDITOR','责任编辑');
INSERT INTO auth_authority (name,remark) VALUES ('ROLE_PUBLISH','发布用户');
INSERT INTO auth_authority (name,remark) VALUES ('ROLE_RESOURCE','资源管理');
INSERT INTO auth_authority (name,remark) VALUES ('ROLE_USER','普通用户');
INSERT INTO auth_authority (name,remark) VALUES ('ROLE_INTERACTION','互动管理');
INSERT INTO auth_authority (name,remark) VALUES ('ROLE_ONLINEOFFICE','在线办事');
INSERT INTO auth_authority (name,remark) VALUES ('ROLE_REPORT','报表集');
INSERT INTO auth_authority (name,remark) VALUES ('ROLE_PROJECT','工程领域');

--用户组
INSERT INTO auth_group (name,remark) VALUES ('GROUP_ADMIN','管理员组');
INSERT INTO auth_group (name,remark) VALUES ('GROUP_GCLY_ADMIN','工程领域管理员组');
INSERT INTO auth_group (name,remark) VALUES ('GROUP_USER','用户组');
INSERT INTO auth_group (name,remark) VALUES ('GROUP_NONE','无权限用户');

--用户组权限
INSERT INTO auth_group_authorities (group_name,authority_name) VALUES ('GROUP_ADMIN','ROLE_ADMIN');
INSERT INTO auth_group_authorities (group_name,authority_name) VALUES ('GROUP_USER','ROLE_USER');

--用户
INSERT INTO auth_user (username,enabled,password,create_time) VALUES ('admin',true,'b594510740d2ac4261c1b2fe87850d08',now());

--用户信息
INSERT INTO auth_userinfo (username,name) VALUES ('admin','管理员');

--用户组成员
INSERT INTO auth_group_members (group_name,username) VALUES ('GROUP_ADMIN','admin');

--删除定时器数据
DELETE FROM qrtz_locks;