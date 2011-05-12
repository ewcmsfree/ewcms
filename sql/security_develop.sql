--删除数据
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
--浔阳附加角色
Insert Into auth_authority (name,remark) Values ('ROLE_LEADING','领导之窗管理');
Insert Into auth_authority (name,remark) Values ('ROLE_ONLINEOFFICE','在线办事');
Insert Into auth_authority (name,remark) Values ('ROLE_INTERACTION','互动管理');
Insert Into auth_authority (name,remark) Values ('ROLE_ZHENGXIE','政协管理');
Insert Into auth_authority (name,remark) Values ('ROLE_RENDA','人大管理');

--用户组
Insert Into auth_group (name,remark) Values ('GROUP_ADMIN','管理员组');
Insert Into auth_group (name,remark) Values ('GROUP_USER','用户组');
Insert Into auth_group (name,remark) Values ('GROUP_NONE','无权限用户');

--用户组权限
Insert Into auth_group_authorities (group_name,authority_name) Values ('GROUP_ADMIN','ROLE_ADMIN');
Insert Into auth_group_authorities (group_name,authority_name) Values ('GROUP_USER','ROLE_USER');

--用户
Insert Into auth_user (username,enabled,password,create_time) Values ('admin',true,'b594510740d2ac4261c1b2fe87850d08',now());
Insert Into auth_user (username,enabled,password,create_time) Values ('user',true,'e14576586777603bd62a8ade7d10661a',now());
Insert Into auth_user (username,enabled,password,create_time) Values ('editor',true,'fa222dd7cdd79d37ecf1691ee3a6b84a',now());
Insert Into auth_user (username,enabled,password,create_time) Values ('resource',true,'59df2ebe79dce7f5eba56d079240ad29',now());
Insert Into auth_user (username,enabled,password,create_time) Values ('wangwei',true,'1fe7853bfddb38f4f0d9ad58fcf231bb',now());
Insert Into auth_user (username,enabled,password,create_time) Values ('lock',true,'243e3a2a7ae950c6ea1e17a0f393bf20',now());
Insert Into auth_user (username,enabled,password,create_time) Values ('unenabled',false,'caa58d7a295c3ed4e7cc029645645519',now());
Insert Into auth_user (username,enabled,password,create_time) Values ('none',true,'df46bace82a8354114d4a5b2dc26be88',now());

--用户信息
Insert Into auth_userinfo (username,name) Values ('admin','admin');
Insert Into auth_userinfo (username,name) Values ('user','user');
Insert Into auth_userinfo (username,name) Values ('editor','editor');
Insert Into auth_userinfo (username,name) Values ('resource','resource');
Insert Into auth_userinfo (username,name) Values ('wangwei','wangwei');
Insert Into auth_userInfo (username,name) Values ('lock','lock');
Insert Into auth_userInfo (username,name) Values ('unenabled','unenabled');
Insert Into auth_userInfo (username,name) Values ('none','none');

--用户组成员
Insert Into auth_group_members (group_name,username) Values ('GROUP_ADMIN','admin');
Insert Into auth_group_members (group_name,username) Values ('GROUP_USER','user');
Insert Into auth_group_members (group_name,username) Values ('GROUP_USER','lock');
Insert Into auth_group_members (group_name,username) Values ('GROUP_USER','unenabled');
Insert Into auth_group_members (group_name,username) Values ('GROUP_NONE','none');

--用户权限
Insert Into auth_user_authorities (username,authority_name) Values ('editor','ROLE_EDITOR');
Insert Into auth_user_authorities (username,authority_name) Values ('resource','ROLE_RESOURCE');
Insert Into auth_user_authorities (username,authority_name) Values ('wangwei','ROLE_EDITOR');
Insert Into auth_user_authorities (username,authority_name) Values ('wangwei','ROLE_RESOURCE');
Insert Into auth_user_authorities (username,authority_name) Values ('wangwei','ROLE_LEADING');
Insert Into auth_user_authorities (username,authority_name) Values ('wangwei','ROLE_ONLINEOFFICE');
Insert Into auth_user_authorities (username,authority_name) Values ('wangwei','ROLE_INTERACTION');
Insert Into auth_user_authorities (username,authority_name) Values ('wangwei','ROLE_ZHENGXIE');
Insert Into auth_user_authorities (username,authority_name) Values ('wangwei','ROLE_RENDA');
