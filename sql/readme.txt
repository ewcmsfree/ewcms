数据库备份
pg_dump.exe --host localhost --username postgres --encoding UTF8 --file C:\ewcms_rc.dump ewcms_rc

数据库恢复
psql.exe --host localhost --username postgres --dbname ewcms_rc < E:\work\ewcms_rc.dump

修改plugin_interaction数据表：增加id字段 nextval('seq_plugin_interaction_id'::regclass),增加counter默认值为0
修改plugin_interaction_speak数据表：增加id字段nextval('seq_plugin_interaction_speak_id'::regclass)
修改plugin_online_advisory数据表：增加id字段 nextval('seq_plugin_online_advisory_id'::regclass)

set JAVA_OPTS=-Xms512m -Xmx1024m -XX:PermSize=128M -XX:MaxNewSize=256m -XX:MaxPermSize=512m

在%TOMCAT_HOM%/conf/server.xml其中：
把<Connector connectionTimeout="20000" port="8080" protocol="HTTP/1.1" redirectPort="8443"/>替换成
<Connector connectionTimeout="20000" executor="tomcatThreadPool" maxKeepAliveRequests="1" maxThreads="150" port="8080" protocol="org.apache.coyote.http11.Http11NioProtocol" redirectPort="8443" URIEncoding="UTF-8"/>