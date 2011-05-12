Delete From doc_content;
Delete From doc_article;
Delete From site_channel;
Update site_config  Set defdetailtpl_id = null,defhometpl_id = null,deflisttpl_id = null Where id= 1;
Delete From site_template;
Delete From site_site;
Delete From site_config;
Insert Into site_config(id,serverdir) Values (1,'/home/wangwei/html');
Insert Into site_site (id,sitename) Values (1,'root');
Insert Into site_site (id,sitename,siteurl,siteroot,publicenable,parent_id,config_id) Values (2,'测试站点','test','http://test.jict.org',true,1,1);
Insert Into site_template(id,name,path,title,site_id) Values (1,'index.html','test/index.html','首页',2);
Insert Into site_template(id,name,path,title,site_id) Values (2,'index.html','test/list.html','首页',2);
Insert Into site_template(id,name,path,title,site_id) Values (3,'index.html','test/detail.html','首页',2);
Update site_config Set defdetailtpl_id = 3,defhometpl_id=1,deflisttpl_id=2 Where id= 1;
Insert Into site_channel(id,name,realdir,realurl,site_id) Values (1,'root','/home/wangwei/html','http://test.jict.org',2);
Insert Into site_channel(id,name,dir,realdir,realurl,parent_id,site_id,publicenable,listsize,maxsize) Values (2,'首页','','/home/wangwei/html/','http://test.jict.org',1,2,true,20,9999);
Insert Into site_channel(id,name,dir,realdir,realurl,parent_id,site_id,publicenable,listsize,maxsize) Values (3,'透视浔阳','tsxy','/home/wangwei/html/tsxy','http://test.jict.org/tsxy',1,2,true,20,9999);
Insert Into site_channel(id,name,dir,realdir,realurl,parent_id,site_id,publicenable,listsize,maxsize) Values (4,'领导之窗','ldzc','/home/wangwei/html/ldzc','http://test.jict.org/ldzc',1,2,true,20,9999);
Insert Into site_channel(id,name,dir,realdir,realurl,parent_id,site_id,publicenable,listsize,maxsize) Values (5,'政务公开','zwgk','/home/wangwei/html/zwgk','http://test.jict.org/zwgk',1,2,true,20,9999);
Insert Into site_channel(id,name,dir,realdir,realurl,parent_id,site_id,publicenable,listsize,maxsize) Values (6,'网上办事','wsbs','/home/wangwei/html/wsbs','http://test.jict.org/wsbs',1,2,true,20,9999);
Insert Into site_channel(id,name,dir,realdir,realurl,parent_id,site_id,publicenable,listsize,maxsize) Values (7,'政民互动','zmhd','/home/wangwei/html/zmhd','http://test.jict.org/zmhd',1,2,true,20,9999);
Insert Into site_channel(id,name,dir,realdir,realurl,parent_id,site_id,publicenable,listsize,maxsize) Values (8,'投资浔阳','tzxy','/home/wangwei/html/tzxy','http://test.jict.org/tzxy',1,2,true,20,9999);
Insert Into site_channel(id,name,dir,realdir,realurl,parent_id,site_id,publicenable,listsize,maxsize) Values (9,'新闻','new','/home/wangwei/html/new','http://test.jict.org/new',1,2,true,20,9999);
Insert Into site_channel(id,name,dir,realdir,realurl,parent_id,site_id,publicenable,listsize,maxsize,hashome) Values (10,'浔阳新闻','xunyan','/home/wangwei/html/new/xunyan','http://test.jict.org/new/xunyan',9,2,true,20,9999,false);

Insert Into doc_article(
id,author,comment_flag,count,delete_flag,delete_time,
image,key_word,modified,origin,published,short_title,status,
sub_title,summary,tag,title,top_flag,channel_id)
Values
(1,'wangwei',true,20,false,'2010-01-01','','test','2010-01-01','','2010-01-01','测试文章1','PRERELEASE','',
'测试Freemarker1','','单元测试文章1',true,10);

Insert Into doc_article(
id,author,comment_flag,count,delete_flag,delete_time,
image,key_word,modified,origin,published,short_title,status,
sub_title,summary,tag,title,top_flag,channel_id)
Values
(2,'wangwei',true,20,false,'2010-01-01','','test','2010-01-01','','2010-01-01','测试文章2','PRERELEASE','',
'测试Freemarker2','','单元测试文章2',true,10);

Insert Into doc_article(
id,author,comment_flag,count,delete_flag,delete_time,
image,key_word,modified,origin,published,short_title,status,
sub_title,summary,tag,title,top_flag,channel_id)
Values
(3,'wangwei',true,20,false,'2010-01-05','','test','2010-01-05','','2010-01-05','测试文章3','PRERELEASE','',
'测试Freemarker3','','单元测试文章3',true,10);

Insert Into doc_article(
id,author,comment_flag,count,delete_flag,delete_time,
image,key_word,modified,origin,published,short_title,status,
sub_title,summary,tag,title,top_flag,channel_id)
Values
(4,'wangwei',true,20,false,'2010-01-07','','test','2010-01-07','','2010-01-07','测试文章4','PRERELEASE','',
'测试Freemarker4','','单元测试文章4',true,10);

Insert Into doc_article(
id,author,comment_flag,count,delete_flag,delete_time,
image,key_word,modified,origin,published,short_title,status,
sub_title,summary,tag,title,top_flag,channel_id)
Values
(5,'wangwei',true,20,false,'2010-01-07','','test','2010-01-07','','2010-01-07','测试文章5','PRERELEASE','',
'测试Freemarker5','','单元测试文章5',true,10);

Insert Into doc_article(
id,author,comment_flag,count,delete_flag,delete_time,
image,key_word,modified,origin,published,short_title,status,
sub_title,summary,tag,title,top_flag,channel_id)
Values
(6,'wangwei',true,20,false,'2010-01-08','','test','2010-01-08','','2010-01-08','测试文章6','PRERELEASE','',
'测试Freemarker6','','单元测试文章6',true,10);

Insert Into doc_article(
id,author,comment_flag,count,delete_flag,delete_time,
image,key_word,modified,origin,published,short_title,status,
sub_title,summary,tag,title,top_flag,channel_id)
Values
(7,'wangwei',true,20,false,'2010-01-07','','test','2010-01-07','','2010-01-07','测试文章7','PRERELEASE','',
'测试Freemarker7','','单元测试文章7',true,10);

Insert Into doc_article(
id,author,comment_flag,count,delete_flag,delete_time,
image,key_word,modified,origin,published,short_title,status,
sub_title,summary,tag,title,top_flag,channel_id)
Values
(8,'wangwei',true,20,false,'2010-01-07','','test','2010-01-07','','2010-01-07','测试文章8','PRERELEASE','',
'测试Freemarker8','','单元测试文章8',true,10);

Insert Into doc_article(
id,author,comment_flag,count,delete_flag,delete_time,
image,key_word,modified,origin,published,short_title,status,
sub_title,summary,tag,title,top_flag,channel_id)
Values
(9,'wangwei',true,20,false,'2010-01-10','','test','2010-01-10','','2010-01-10','测试文章9','PRERELEASE','',
'测试Freemarker9','','单元测试文章9',true,10);

Insert Into doc_article(
id,author,comment_flag,count,delete_flag,delete_time,
image,key_word,modified,origin,published,short_title,status,
sub_title,summary,tag,title,top_flag,channel_id)
Values
(10,'wangwei',true,20,false,'2010-01-10','','test','2010-01-10','','2010-01-10','测试文章10','PRERELEASE','',
'测试Freemarker10','','单元测试文章10',true,10);

Insert Into doc_content(id,detail,page,article_id) values
(1,'由龙首崖南下，山石累累，有一巨石，状如雄狮涉涧，故称”狮子岩“。崖下有一山洞，洞门仅容一人出入，洞中空约6立方米，洞顶有一”天窗“，仍透光明，名 “文殊洞”。洞左有文殊崖 ，崖下有一卧石，方如印。石缀绝壁而不坠，有片峰薄如屏风，下有断痕，迎风而不折，令人叫绝。其下就是清凉台。
        顺崖左侧拾级而下，沿崖下岭脊有一条逶迤小道，道两旁的卧石山崖上，均镌有现代名人石刻。这些巨幅石刻，今意萧萧，渗透高山的精魂，吸引着游人一睹其“芳容”，领略现代文明的风采。
        从龙首崖旁小亭中侧看龙首崖，只见绝壁陡起，深谷万丈。崖底至崖顶似一硕大的撑天方柱，顶部横卧向外突出系两巨石构成，崖逢中一虬松生于悬崖间如龙须，人称“卧龙松。
        龙首崖不但奇险，而且具有神境的意韵。当团团云絮自崖下山谷慢慢向半山空旷处汇聚，缓缓流动向崖上漫过来时，白茫茫，铺天盖地，云游浪卷，峰峦渐渐隐入云中，此时的龙首崖，宛如一条苍龙，在云雾中翱翔潜游。具有一种“气萧萧以瑟瑟，风飕飕“的森严气氛，雨后初雾，苍崖如洗，龙首崖又显得格外的清新，在阳光下，闪烁着淡淡的青光。远远望去一条苍龙昂首的图案，清晰地贴在蔚蓝蔚蓝的天幕，就像一幅具有浓烈诗意的剪纸画。当严冬时节，白茫茫的雪将龙首崖全部吞没后，又别有一番风趣。只见峰峦深壑银白一片，天灰蒙蒙，地昏沉沉，四周寂静无声。在这个寂静的白色的世界里，一条银色蛟龙横卧在岭中，似乎在拥抱着一个晶莹的梦......',1,1);
Insert Into doc_content(id,detail,page,article_id) values
(2,'由龙首崖南下，山石累累，有一巨石，状如雄狮涉涧，故称”狮子岩“。崖下有一山洞，洞门仅容一人出入，洞中空约6立方米，洞顶有一”天窗“，仍透光明，名 “文殊洞”。洞左有文殊崖 ，崖下有一卧石，方如印。石缀绝壁而不坠，有片峰薄如屏风，下有断痕，迎风而不折，令人叫绝。其下就是清凉台。
        顺崖左侧拾级而下，沿崖下岭脊有一条逶迤小道，道两旁的卧石山崖上，均镌有现代名人石刻。这些巨幅石刻，今意萧萧，渗透高山的精魂，吸引着游人一睹其“芳容”，领略现代文明的风采。
        从龙首崖旁小亭中侧看龙首崖，只见绝壁陡起，深谷万丈。崖底至崖顶似一硕大的撑天方柱，顶部横卧向外突出系两巨石构成，崖逢中一虬松生于悬崖间如龙须，人称“卧龙松。
        龙首崖不但奇险，而且具有神境的意韵。当团团云絮自崖下山谷慢慢向半山空旷处汇聚，缓缓流动向崖上漫过来时，白茫茫，铺天盖地，云游浪卷，峰峦渐渐隐入云中，此时的龙首崖，宛如一条苍龙，在云雾中翱翔潜游。具有一种“气萧萧以瑟瑟，风飕飕“的森严气氛，雨后初雾，苍崖如洗，龙首崖又显得格外的清新，在阳光下，闪烁着淡淡的青光。远远望去一条苍龙昂首的图案，清晰地贴在蔚蓝蔚蓝的天幕，就像一幅具有浓烈诗意的剪纸画。当严冬时节，白茫茫的雪将龙首崖全部吞没后，又别有一番风趣。只见峰峦深壑银白一片，天灰蒙蒙，地昏沉沉，四周寂静无声。在这个寂静的白色的世界里，一条银色蛟龙横卧在岭中，似乎在拥抱着一个晶莹的梦......',2,1);
Insert Into doc_content(id,detail,page,article_id) values
(3,'由龙首崖南下，山石累累，有一巨石，状如雄狮涉涧，故称”狮子岩“。崖下有一山洞，洞门仅容一人出入，洞中空约6立方米，洞顶有一”天窗“，仍透光明，名 “文殊洞”。洞左有文殊崖 ，崖下有一卧石，方如印。石缀绝壁而不坠，有片峰薄如屏风，下有断痕，迎风而不折，令人叫绝。其下就是清凉台。
        顺崖左侧拾级而下，沿崖下岭脊有一条逶迤小道，道两旁的卧石山崖上，均镌有现代名人石刻。这些巨幅石刻，今意萧萧，渗透高山的精魂，吸引着游人一睹其“芳容”，领略现代文明的风采。
        从龙首崖旁小亭中侧看龙首崖，只见绝壁陡起，深谷万丈。崖底至崖顶似一硕大的撑天方柱，顶部横卧向外突出系两巨石构成，崖逢中一虬松生于悬崖间如龙须，人称“卧龙松。
        龙首崖不但奇险，而且具有神境的意韵。当团团云絮自崖下山谷慢慢向半山空旷处汇聚，缓缓流动向崖上漫过来时，白茫茫，铺天盖地，云游浪卷，峰峦渐渐隐入云中，此时的龙首崖，宛如一条苍龙，在云雾中翱翔潜游。具有一种“气萧萧以瑟瑟，风飕飕“的森严气氛，雨后初雾，苍崖如洗，龙首崖又显得格外的清新，在阳光下，闪烁着淡淡的青光。远远望去一条苍龙昂首的图案，清晰地贴在蔚蓝蔚蓝的天幕，就像一幅具有浓烈诗意的剪纸画。当严冬时节，白茫茫的雪将龙首崖全部吞没后，又别有一番风趣。只见峰峦深壑银白一片，天灰蒙蒙，地昏沉沉，四周寂静无声。在这个寂静的白色的世界里，一条银色蛟龙横卧在岭中，似乎在拥抱着一个晶莹的梦......',1,2);

Insert Into doc_content(id,detail,page,article_id) values
(4,'由龙首崖南下，山石累累，有一巨石，状如雄狮涉涧，故称”狮子岩“。崖下有一山洞，洞门仅容一人出入，洞中空约6立方米，洞顶有一”天窗“，仍透光明，名 “文殊洞”。洞左有文殊崖 ，崖下有一卧石，方如印。石缀绝壁而不坠，有片峰薄如屏风，下有断痕，迎风而不折，令人叫绝。其下就是清凉台。
        顺崖左侧拾级而下，沿崖下岭脊有一条逶迤小道，道两旁的卧石山崖上，均镌有现代名人石刻。这些巨幅石刻，今意萧萧，渗透高山的精魂，吸引着游人一睹其“芳容”，领略现代文明的风采。
        从龙首崖旁小亭中侧看龙首崖，只见绝壁陡起，深谷万丈。崖底至崖顶似一硕大的撑天方柱，顶部横卧向外突出系两巨石构成，崖逢中一虬松生于悬崖间如龙须，人称“卧龙松。
        龙首崖不但奇险，而且具有神境的意韵。当团团云絮自崖下山谷慢慢向半山空旷处汇聚，缓缓流动向崖上漫过来时，白茫茫，铺天盖地，云游浪卷，峰峦渐渐隐入云中，此时的龙首崖，宛如一条苍龙，在云雾中翱翔潜游。具有一种“气萧萧以瑟瑟，风飕飕“的森严气氛，雨后初雾，苍崖如洗，龙首崖又显得格外的清新，在阳光下，闪烁着淡淡的青光。远远望去一条苍龙昂首的图案，清晰地贴在蔚蓝蔚蓝的天幕，就像一幅具有浓烈诗意的剪纸画。当严冬时节，白茫茫的雪将龙首崖全部吞没后，又别有一番风趣。只见峰峦深壑银白一片，天灰蒙蒙，地昏沉沉，四周寂静无声。在这个寂静的白色的世界里，一条银色蛟龙横卧在岭中，似乎在拥抱着一个晶莹的梦......',1,3);

Insert Into doc_content(id,detail,page,article_id) values
(5,'由龙首崖南下，山石累累，有一巨石，状如雄狮涉涧，故称”狮子岩“。崖下有一山洞，洞门仅容一人出入，洞中空约6立方米，洞顶有一”天窗“，仍透光明，名 “文殊洞”。洞左有文殊崖 ，崖下有一卧石，方如印。石缀绝壁而不坠，有片峰薄如屏风，下有断痕，迎风而不折，令人叫绝。其下就是清凉台。
        顺崖左侧拾级而下，沿崖下岭脊有一条逶迤小道，道两旁的卧石山崖上，均镌有现代名人石刻。这些巨幅石刻，今意萧萧，渗透高山的精魂，吸引着游人一睹其“芳容”，领略现代文明的风采。
        从龙首崖旁小亭中侧看龙首崖，只见绝壁陡起，深谷万丈。崖底至崖顶似一硕大的撑天方柱，顶部横卧向外突出系两巨石构成，崖逢中一虬松生于悬崖间如龙须，人称“卧龙松。
        龙首崖不但奇险，而且具有神境的意韵。当团团云絮自崖下山谷慢慢向半山空旷处汇聚，缓缓流动向崖上漫过来时，白茫茫，铺天盖地，云游浪卷，峰峦渐渐隐入云中，此时的龙首崖，宛如一条苍龙，在云雾中翱翔潜游。具有一种“气萧萧以瑟瑟，风飕飕“的森严气氛，雨后初雾，苍崖如洗，龙首崖又显得格外的清新，在阳光下，闪烁着淡淡的青光。远远望去一条苍龙昂首的图案，清晰地贴在蔚蓝蔚蓝的天幕，就像一幅具有浓烈诗意的剪纸画。当严冬时节，白茫茫的雪将龙首崖全部吞没后，又别有一番风趣。只见峰峦深壑银白一片，天灰蒙蒙，地昏沉沉，四周寂静无声。在这个寂静的白色的世界里，一条银色蛟龙横卧在岭中，似乎在拥抱着一个晶莹的梦......',1,4);

Insert Into doc_content(id,detail,page,article_id) values
(6,'由龙首崖南下，山石累累，有一巨石，状如雄狮涉涧，故称”狮子岩“。崖下有一山洞，洞门仅容一人出入，洞中空约6立方米，洞顶有一”天窗“，仍透光明，名 “文殊洞”。洞左有文殊崖 ，崖下有一卧石，方如印。石缀绝壁而不坠，有片峰薄如屏风，下有断痕，迎风而不折，令人叫绝。其下就是清凉台。
        顺崖左侧拾级而下，沿崖下岭脊有一条逶迤小道，道两旁的卧石山崖上，均镌有现代名人石刻。这些巨幅石刻，今意萧萧，渗透高山的精魂，吸引着游人一睹其“芳容”，领略现代文明的风采。
        从龙首崖旁小亭中侧看龙首崖，只见绝壁陡起，深谷万丈。崖底至崖顶似一硕大的撑天方柱，顶部横卧向外突出系两巨石构成，崖逢中一虬松生于悬崖间如龙须，人称“卧龙松。
        龙首崖不但奇险，而且具有神境的意韵。当团团云絮自崖下山谷慢慢向半山空旷处汇聚，缓缓流动向崖上漫过来时，白茫茫，铺天盖地，云游浪卷，峰峦渐渐隐入云中，此时的龙首崖，宛如一条苍龙，在云雾中翱翔潜游。具有一种“气萧萧以瑟瑟，风飕飕“的森严气氛，雨后初雾，苍崖如洗，龙首崖又显得格外的清新，在阳光下，闪烁着淡淡的青光。远远望去一条苍龙昂首的图案，清晰地贴在蔚蓝蔚蓝的天幕，就像一幅具有浓烈诗意的剪纸画。当严冬时节，白茫茫的雪将龙首崖全部吞没后，又别有一番风趣。只见峰峦深壑银白一片，天灰蒙蒙，地昏沉沉，四周寂静无声。在这个寂静的白色的世界里，一条银色蛟龙横卧在岭中，似乎在拥抱着一个晶莹的梦......',1,5);

Insert Into doc_content(id,detail,page,article_id) values
(7,'由龙首崖南下，山石累累，有一巨石，状如雄狮涉涧，故称”狮子岩“。崖下有一山洞，洞门仅容一人出入，洞中空约6立方米，洞顶有一”天窗“，仍透光明，名 “文殊洞”。洞左有文殊崖 ，崖下有一卧石，方如印。石缀绝壁而不坠，有片峰薄如屏风，下有断痕，迎风而不折，令人叫绝。其下就是清凉台。
        顺崖左侧拾级而下，沿崖下岭脊有一条逶迤小道，道两旁的卧石山崖上，均镌有现代名人石刻。这些巨幅石刻，今意萧萧，渗透高山的精魂，吸引着游人一睹其“芳容”，领略现代文明的风采。
        从龙首崖旁小亭中侧看龙首崖，只见绝壁陡起，深谷万丈。崖底至崖顶似一硕大的撑天方柱，顶部横卧向外突出系两巨石构成，崖逢中一虬松生于悬崖间如龙须，人称“卧龙松。
        龙首崖不但奇险，而且具有神境的意韵。当团团云絮自崖下山谷慢慢向半山空旷处汇聚，缓缓流动向崖上漫过来时，白茫茫，铺天盖地，云游浪卷，峰峦渐渐隐入云中，此时的龙首崖，宛如一条苍龙，在云雾中翱翔潜游。具有一种“气萧萧以瑟瑟，风飕飕“的森严气氛，雨后初雾，苍崖如洗，龙首崖又显得格外的清新，在阳光下，闪烁着淡淡的青光。远远望去一条苍龙昂首的图案，清晰地贴在蔚蓝蔚蓝的天幕，就像一幅具有浓烈诗意的剪纸画。当严冬时节，白茫茫的雪将龙首崖全部吞没后，又别有一番风趣。只见峰峦深壑银白一片，天灰蒙蒙，地昏沉沉，四周寂静无声。在这个寂静的白色的世界里，一条银色蛟龙横卧在岭中，似乎在拥抱着一个晶莹的梦......',2,5);

Insert Into doc_content(id,detail,page,article_id) values
(8,'由龙首崖南下，山石累累，有一巨石，状如雄狮涉涧，故称”狮子岩“。崖下有一山洞，洞门仅容一人出入，洞中空约6立方米，洞顶有一”天窗“，仍透光明，名 “文殊洞”。洞左有文殊崖 ，崖下有一卧石，方如印。石缀绝壁而不坠，有片峰薄如屏风，下有断痕，迎风而不折，令人叫绝。其下就是清凉台。
        顺崖左侧拾级而下，沿崖下岭脊有一条逶迤小道，道两旁的卧石山崖上，均镌有现代名人石刻。这些巨幅石刻，今意萧萧，渗透高山的精魂，吸引着游人一睹其“芳容”，领略现代文明的风采。
        从龙首崖旁小亭中侧看龙首崖，只见绝壁陡起，深谷万丈。崖底至崖顶似一硕大的撑天方柱，顶部横卧向外突出系两巨石构成，崖逢中一虬松生于悬崖间如龙须，人称“卧龙松。
        龙首崖不但奇险，而且具有神境的意韵。当团团云絮自崖下山谷慢慢向半山空旷处汇聚，缓缓流动向崖上漫过来时，白茫茫，铺天盖地，云游浪卷，峰峦渐渐隐入云中，此时的龙首崖，宛如一条苍龙，在云雾中翱翔潜游。具有一种“气萧萧以瑟瑟，风飕飕“的森严气氛，雨后初雾，苍崖如洗，龙首崖又显得格外的清新，在阳光下，闪烁着淡淡的青光。远远望去一条苍龙昂首的图案，清晰地贴在蔚蓝蔚蓝的天幕，就像一幅具有浓烈诗意的剪纸画。当严冬时节，白茫茫的雪将龙首崖全部吞没后，又别有一番风趣。只见峰峦深壑银白一片，天灰蒙蒙，地昏沉沉，四周寂静无声。在这个寂静的白色的世界里，一条银色蛟龙横卧在岭中，似乎在拥抱着一个晶莹的梦......',3,5);

Insert Into doc_content(id,detail,page,article_id) values
(9,'由龙首崖南下，山石累累，有一巨石，状如雄狮涉涧，故称”狮子岩“。崖下有一山洞，洞门仅容一人出入，洞中空约6立方米，洞顶有一”天窗“，仍透光明，名 “文殊洞”。洞左有文殊崖 ，崖下有一卧石，方如印。石缀绝壁而不坠，有片峰薄如屏风，下有断痕，迎风而不折，令人叫绝。其下就是清凉台。
        顺崖左侧拾级而下，沿崖下岭脊有一条逶迤小道，道两旁的卧石山崖上，均镌有现代名人石刻。这些巨幅石刻，今意萧萧，渗透高山的精魂，吸引着游人一睹其“芳容”，领略现代文明的风采。
        从龙首崖旁小亭中侧看龙首崖，只见绝壁陡起，深谷万丈。崖底至崖顶似一硕大的撑天方柱，顶部横卧向外突出系两巨石构成，崖逢中一虬松生于悬崖间如龙须，人称“卧龙松。
        龙首崖不但奇险，而且具有神境的意韵。当团团云絮自崖下山谷慢慢向半山空旷处汇聚，缓缓流动向崖上漫过来时，白茫茫，铺天盖地，云游浪卷，峰峦渐渐隐入云中，此时的龙首崖，宛如一条苍龙，在云雾中翱翔潜游。具有一种“气萧萧以瑟瑟，风飕飕“的森严气氛，雨后初雾，苍崖如洗，龙首崖又显得格外的清新，在阳光下，闪烁着淡淡的青光。远远望去一条苍龙昂首的图案，清晰地贴在蔚蓝蔚蓝的天幕，就像一幅具有浓烈诗意的剪纸画。当严冬时节，白茫茫的雪将龙首崖全部吞没后，又别有一番风趣。只见峰峦深壑银白一片，天灰蒙蒙，地昏沉沉，四周寂静无声。在这个寂静的白色的世界里，一条银色蛟龙横卧在岭中，似乎在拥抱着一个晶莹的梦......',1,6);

Insert Into doc_content(id,detail,page,article_id) values
(10,'由龙首崖南下，山石累累，有一巨石，状如雄狮涉涧，故称”狮子岩“。崖下有一山洞，洞门仅容一人出入，洞中空约6立方米，洞顶有一”天窗“，仍透光明，名 “文殊洞”。洞左有文殊崖 ，崖下有一卧石，方如印。石缀绝壁而不坠，有片峰薄如屏风，下有断痕，迎风而不折，令人叫绝。其下就是清凉台。
        顺崖左侧拾级而下，沿崖下岭脊有一条逶迤小道，道两旁的卧石山崖上，均镌有现代名人石刻。这些巨幅石刻，今意萧萧，渗透高山的精魂，吸引着游人一睹其“芳容”，领略现代文明的风采。
        从龙首崖旁小亭中侧看龙首崖，只见绝壁陡起，深谷万丈。崖底至崖顶似一硕大的撑天方柱，顶部横卧向外突出系两巨石构成，崖逢中一虬松生于悬崖间如龙须，人称“卧龙松。
        龙首崖不但奇险，而且具有神境的意韵。当团团云絮自崖下山谷慢慢向半山空旷处汇聚，缓缓流动向崖上漫过来时，白茫茫，铺天盖地，云游浪卷，峰峦渐渐隐入云中，此时的龙首崖，宛如一条苍龙，在云雾中翱翔潜游。具有一种“气萧萧以瑟瑟，风飕飕“的森严气氛，雨后初雾，苍崖如洗，龙首崖又显得格外的清新，在阳光下，闪烁着淡淡的青光。远远望去一条苍龙昂首的图案，清晰地贴在蔚蓝蔚蓝的天幕，就像一幅具有浓烈诗意的剪纸画。当严冬时节，白茫茫的雪将龙首崖全部吞没后，又别有一番风趣。只见峰峦深壑银白一片，天灰蒙蒙，地昏沉沉，四周寂静无声。在这个寂静的白色的世界里，一条银色蛟龙横卧在岭中，似乎在拥抱着一个晶莹的梦......',2,6);

Insert Into doc_content(id,detail,page,article_id) values
(11,'由龙首崖南下，山石累累，有一巨石，状如雄狮涉涧，故称”狮子岩“。崖下有一山洞，洞门仅容一人出入，洞中空约6立方米，洞顶有一”天窗“，仍透光明，名 “文殊洞”。洞左有文殊崖 ，崖下有一卧石，方如印。石缀绝壁而不坠，有片峰薄如屏风，下有断痕，迎风而不折，令人叫绝。其下就是清凉台。
        顺崖左侧拾级而下，沿崖下岭脊有一条逶迤小道，道两旁的卧石山崖上，均镌有现代名人石刻。这些巨幅石刻，今意萧萧，渗透高山的精魂，吸引着游人一睹其“芳容”，领略现代文明的风采。
        从龙首崖旁小亭中侧看龙首崖，只见绝壁陡起，深谷万丈。崖底至崖顶似一硕大的撑天方柱，顶部横卧向外突出系两巨石构成，崖逢中一虬松生于悬崖间如龙须，人称“卧龙松。
        龙首崖不但奇险，而且具有神境的意韵。当团团云絮自崖下山谷慢慢向半山空旷处汇聚，缓缓流动向崖上漫过来时，白茫茫，铺天盖地，云游浪卷，峰峦渐渐隐入云中，此时的龙首崖，宛如一条苍龙，在云雾中翱翔潜游。具有一种“气萧萧以瑟瑟，风飕飕“的森严气氛，雨后初雾，苍崖如洗，龙首崖又显得格外的清新，在阳光下，闪烁着淡淡的青光。远远望去一条苍龙昂首的图案，清晰地贴在蔚蓝蔚蓝的天幕，就像一幅具有浓烈诗意的剪纸画。当严冬时节，白茫茫的雪将龙首崖全部吞没后，又别有一番风趣。只见峰峦深壑银白一片，天灰蒙蒙，地昏沉沉，四周寂静无声。在这个寂静的白色的世界里，一条银色蛟龙横卧在岭中，似乎在拥抱着一个晶莹的梦......',3,6);

Insert Into doc_content(id,detail,page,article_id) values
(12,'由龙首崖南下，山石累累，有一巨石，状如雄狮涉涧，故称”狮子岩“。崖下有一山洞，洞门仅容一人出入，洞中空约6立方米，洞顶有一”天窗“，仍透光明，名 “文殊洞”。洞左有文殊崖 ，崖下有一卧石，方如印。石缀绝壁而不坠，有片峰薄如屏风，下有断痕，迎风而不折，令人叫绝。其下就是清凉台。
        顺崖左侧拾级而下，沿崖下岭脊有一条逶迤小道，道两旁的卧石山崖上，均镌有现代名人石刻。这些巨幅石刻，今意萧萧，渗透高山的精魂，吸引着游人一睹其“芳容”，领略现代文明的风采。
        从龙首崖旁小亭中侧看龙首崖，只见绝壁陡起，深谷万丈。崖底至崖顶似一硕大的撑天方柱，顶部横卧向外突出系两巨石构成，崖逢中一虬松生于悬崖间如龙须，人称“卧龙松。
        龙首崖不但奇险，而且具有神境的意韵。当团团云絮自崖下山谷慢慢向半山空旷处汇聚，缓缓流动向崖上漫过来时，白茫茫，铺天盖地，云游浪卷，峰峦渐渐隐入云中，此时的龙首崖，宛如一条苍龙，在云雾中翱翔潜游。具有一种“气萧萧以瑟瑟，风飕飕“的森严气氛，雨后初雾，苍崖如洗，龙首崖又显得格外的清新，在阳光下，闪烁着淡淡的青光。远远望去一条苍龙昂首的图案，清晰地贴在蔚蓝蔚蓝的天幕，就像一幅具有浓烈诗意的剪纸画。当严冬时节，白茫茫的雪将龙首崖全部吞没后，又别有一番风趣。只见峰峦深壑银白一片，天灰蒙蒙，地昏沉沉，四周寂静无声。在这个寂静的白色的世界里，一条银色蛟龙横卧在岭中，似乎在拥抱着一个晶莹的梦......',1,7);

Insert Into doc_content(id,detail,page,article_id) values
(13,'由龙首崖南下，山石累累，有一巨石，状如雄狮涉涧，故称”狮子岩“。崖下有一山洞，洞门仅容一人出入，洞中空约6立方米，洞顶有一”天窗“，仍透光明，名 “文殊洞”。洞左有文殊崖 ，崖下有一卧石，方如印。石缀绝壁而不坠，有片峰薄如屏风，下有断痕，迎风而不折，令人叫绝。其下就是清凉台。
        顺崖左侧拾级而下，沿崖下岭脊有一条逶迤小道，道两旁的卧石山崖上，均镌有现代名人石刻。这些巨幅石刻，今意萧萧，渗透高山的精魂，吸引着游人一睹其“芳容”，领略现代文明的风采。
        从龙首崖旁小亭中侧看龙首崖，只见绝壁陡起，深谷万丈。崖底至崖顶似一硕大的撑天方柱，顶部横卧向外突出系两巨石构成，崖逢中一虬松生于悬崖间如龙须，人称“卧龙松。
        龙首崖不但奇险，而且具有神境的意韵。当团团云絮自崖下山谷慢慢向半山空旷处汇聚，缓缓流动向崖上漫过来时，白茫茫，铺天盖地，云游浪卷，峰峦渐渐隐入云中，此时的龙首崖，宛如一条苍龙，在云雾中翱翔潜游。具有一种“气萧萧以瑟瑟，风飕飕“的森严气氛，雨后初雾，苍崖如洗，龙首崖又显得格外的清新，在阳光下，闪烁着淡淡的青光。远远望去一条苍龙昂首的图案，清晰地贴在蔚蓝蔚蓝的天幕，就像一幅具有浓烈诗意的剪纸画。当严冬时节，白茫茫的雪将龙首崖全部吞没后，又别有一番风趣。只见峰峦深壑银白一片，天灰蒙蒙，地昏沉沉，四周寂静无声。在这个寂静的白色的世界里，一条银色蛟龙横卧在岭中，似乎在拥抱着一个晶莹的梦......',1,8);

Insert Into doc_content(id,detail,page,article_id) values
(14,'由龙首崖南下，山石累累，有一巨石，状如雄狮涉涧，故称”狮子岩“。崖下有一山洞，洞门仅容一人出入，洞中空约6立方米，洞顶有一”天窗“，仍透光明，名 “文殊洞”。洞左有文殊崖 ，崖下有一卧石，方如印。石缀绝壁而不坠，有片峰薄如屏风，下有断痕，迎风而不折，令人叫绝。其下就是清凉台。
        顺崖左侧拾级而下，沿崖下岭脊有一条逶迤小道，道两旁的卧石山崖上，均镌有现代名人石刻。这些巨幅石刻，今意萧萧，渗透高山的精魂，吸引着游人一睹其“芳容”，领略现代文明的风采。
        从龙首崖旁小亭中侧看龙首崖，只见绝壁陡起，深谷万丈。崖底至崖顶似一硕大的撑天方柱，顶部横卧向外突出系两巨石构成，崖逢中一虬松生于悬崖间如龙须，人称“卧龙松。
        龙首崖不但奇险，而且具有神境的意韵。当团团云絮自崖下山谷慢慢向半山空旷处汇聚，缓缓流动向崖上漫过来时，白茫茫，铺天盖地，云游浪卷，峰峦渐渐隐入云中，此时的龙首崖，宛如一条苍龙，在云雾中翱翔潜游。具有一种“气萧萧以瑟瑟，风飕飕“的森严气氛，雨后初雾，苍崖如洗，龙首崖又显得格外的清新，在阳光下，闪烁着淡淡的青光。远远望去一条苍龙昂首的图案，清晰地贴在蔚蓝蔚蓝的天幕，就像一幅具有浓烈诗意的剪纸画。当严冬时节，白茫茫的雪将龙首崖全部吞没后，又别有一番风趣。只见峰峦深壑银白一片，天灰蒙蒙，地昏沉沉，四周寂静无声。在这个寂静的白色的世界里，一条银色蛟龙横卧在岭中，似乎在拥抱着一个晶莹的梦......',1,10);


