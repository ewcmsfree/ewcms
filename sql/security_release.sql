--删除数据
Delete From auth_user_authorities;
Delete From auth_group_authorities;
Delete From auth_group_members;
Delete From auth_userinfo;
Delete From auth_user;
Delete From auth_group;
Delete From auth_authority;


--角色(权限)设定
Insert Into auth_authority (name,remark) Values ('ROLE_ADMIN','管理员角色');

--用户组
Insert Into auth_group (id,name,remark) Values (1,'admin','管理员组');
ALTER SEQUENCE seq_auth_group RESTART WITH 2;

--用户组权限
Insert Into auth_group_authorities (group_id,authority_name) Values (1,'ROLE_ADMIN');

--用户
Insert Into auth_user (username,enabled,locked,password) Values ('xyadmin',true,false,'e80e2a1d98930d2030f3f577c3af62e2');
Insert Into auth_user (username,enabled,locked,password) Values ('rduser',true,false,'f840c89953d98f88faf9a2f84290f9b7');
Insert Into auth_user (username,enabled,locked,password) Values ('zxuser',true,false,'f9cfca83cae81e7d2f0cd8124f02a785');

--用户信息
Insert Into auth_userinfo (username,name) Values ('admin','admin');
Insert Into auth_userinfo (username,name) Values ('rduser','人大用户');
Insert Into auth_userinfo (username,name) Values ('zxuser','政协用户');

--用户组成员
Insert Into auth_group_members (group_id,username) Values (1,'admin');
Insert Into auth_group_members (group_id,username) Values (1,'rduser');
Insert Into auth_group_members (group_id,username) Values (1,'zxuser');

