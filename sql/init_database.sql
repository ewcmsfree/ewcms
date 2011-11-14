--删除权限数据
Delete From auth_user_authorities;
Delete From auth_group_authorities;
Delete From auth_group_members;
Delete From auth_userinfo;
Delete From auth_user;
Delete From auth_group;
Delete From auth_authority;

--初始数据
--角色(权限)设定
Insert Into auth_authority (name,remark) Values ('ROLE_ADMIN','管理员');
Insert Into auth_authority (name,remark) Values ('ROLE_EDITOR','责任编辑');
Insert Into auth_authority (name,remark) Values ('ROLE_RESOURCE','资源管理');
Insert Into auth_authority (name,remark) Values ('ROLE_WRITER','作者用户');
Insert Into auth_authority (name,remark) Values ('ROLE_USER','普通用户');

--用户组
Insert Into auth_group (name,remark) Values ('GROUP_ADMIN','管理员组');
Insert Into auth_group (name,remark) Values ('GROUP_USER','用户组');
Insert Into auth_group (name,remark) Values ('GROUP_NONE','无权限用户');

--用户组权限
Insert Into auth_group_authorities (group_name,authority_name) Values ('GROUP_ADMIN','ROLE_ADMIN');
Insert Into auth_group_authorities (group_name,authority_name) Values ('GROUP_USER','ROLE_USER');

--用户
Insert Into auth_user (username,enabled,password,create_time) Values ('admin',true,'b594510740d2ac4261c1b2fe87850d08',now());

--用户信息
Insert Into auth_userinfo (username,name) Values ('admin','admin');

--用户组成员
Insert Into auth_group_members (group_name,username) Values ('GROUP_ADMIN','admin');

--删除定时器数据
Delete From qrtz_locks;

--初始定时器
INSERT INTO qrtz_locks values('TRIGGER_ACCESS');
INSERT INTO qrtz_locks values('JOB_ACCESS');
INSERT INTO qrtz_locks values('CALENDAR_ACCESS');
INSERT INTO qrtz_locks values('STATE_ACCESS');
INSERT INTO qrtz_locks values('MISFIRE_ACCESS');
