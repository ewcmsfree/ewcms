-- Delete plugin tables
DROP TABLE plugin_leaderchannel_articlermc;
DROP TABLE plugin_leaderchannel;
DROP TABLE plugin_matter_annex;
DROP TABLE plugin_matter_citizen;
DROP TABLE plugin_online_advisory;
DROP TABLE plugin_position_leader;
DROP TABLE plugin_position;
DROP TABLE plugin_leaderchannel_articlermc;
DROP TABLE plugin_leaderchannel;
DROP TABLE plugin_workingbody_articlermc;
DROP TABLE plugin_workingbody_matter;
DROP TABLE plugin_workingbody_organ;
DROP TABLE plugin_leader;
DROP TABLE plugin_matter;
DROP TABLE plugin_workingbody;
DROP TABLE plugin_site_member_article;
DROP TABLE plugin_site_member;
DROP TABLE component_auth_user;
DROP TABLE component_auth_userinfo;
DROP TABLE component_comment;
DROP TABLE component_comment_counter;
DROP TABLE component_comment_reply;
DROP TABLE component_counter;
DROP TABLE component_counter_log;
DROP TABLE component_interaction;
DROP TABLE component_interaction_backratio;
DROP TABLE component_interaction_speak;

--Update core


--Update content
ALTER TABLE doc_article ALTER COLUMN id type bigint;
ALTER TABLE doc_article RENAME COLUMN comment_flag TO comment;
ALTER TABLE doc_article RENAME COLUMN link_addr TO url;
ALTER TABLE doc_article ADD COLUMN total integer;
ALTER TABLE doc_article ADD COLUMN delete boolean;
ALTER TABLE doc_article ADD COLUMN inside boolean;
ALTER TABLE doc_article ADD COLUMN modified timestamp without time zone;
ALTER TABLE doc_article ADD COLUMN reviewprocess_id bigint;
ALTER TABLE doc_article ADD COLUMN published timestamp without time zone;
ALTER TABLE doc_article ADD COLUMN status character varying(255);
ALTER TABLE doc_article ADD COLUMN createtime timestamp without time zone;

-- 新增/修改doc_articlermc字段
ALTER TABLE doc_articlermc ALTER COLUMN id type bigint;
ALTER TABLE doc_articlermc ADD COLUMN reference boolean;
ALTER TABLE doc_articlermc ADD COLUMN sort bigint;
ALTER TABLE doc_articlermc ADD COLUMN top boolean;

-- 更新doc_article表记录
UPDATE doc_article AS a 
SET total = 1,
    delete = b.delete_flag,
    inside = false,
    modified = b.modified,
    published = b.published,
    status = b.status,
    createtime = b.createtime,
    url = b.url
FROM doc_articlermc AS b 
WHERE a.id = b.article_id;

-- 更新doc_articlermc表记录
UPDATE doc_articlermc AS a
SET top = b.top_flag
FROM doc_article AS b
WHERE a.article_id = b.id;

-- 删除doc_article字段
ALTER TABLE doc_article DROP COLUMN annex_flag;
ALTER TABLE doc_article DROP COLUMN comment_flag;
ALTER TABLE doc_article DROP COLUMN copy_flag;
ALTER TABLE doc_article DROP COLUMN copyout_flag;
ALTER TABLE doc_article DROP COLUMN hot_flag;
ALTER TABLE doc_article DROP COLUMN image_flag;
ALTER TABLE doc_article DROP COLUMN recommend_flag;
ALTER TABLE doc_article DROP COLUMN top_flag;
ALTER TABLE doc_article DROP COLUMN video_flag;
ALTER TABLE doc_article DROP COLUMN short_title_style;
ALTER TABLE doc_article DROP COLUMN sub_title_style;
ALTER TABLE doc_article DROP COLUMN title_style;
ALTER TABLE doc_article DROP COLUMN eauthor;

-- 删除doc_articlermc字段
ALTER TABLE doc_articlermc DROP COLUMN createtime;
ALTER TABLE doc_articlermc DROP COLUMN delete_author;
ALTER TABLE doc_articlermc DROP COLUMN delete_flag;
ALTER TABLE doc_articlermc DROP COLUMN delete_time;
ALTER TABLE doc_articlermc DROP COLUMN modified;
ALTER TABLE doc_articlermc DROP COLUMN published;
ALTER TABLE doc_articlermc DROP COLUMN restore_author;
ALTER TABLE doc_articlermc DROP COLUMN status;
ALTER TABLE doc_articlermc DROP COLUMN url;

-- 表doc_article重命名为content_article
ALTER TABLE doc_article RENAME TO content_article;
-- 序列seq_doc_article_id重命名为seq_content_article_id
ALTER TABLE seq_doc_article_id RENAME TO seq_content_article_id;

-- 表doc_articlermc重命名为content_article_main
ALTER TABLE doc_articlermc RENAME TO content_article_main;
-- 序列seq_doc_articlermc_id重命名为seq_content_srticle_id
ALTER TABLE seq_doc_articlermc_id RENAME TO seq_content_article_main_id;

-- 表doc_content重命名为content_content
ALTER TABLE doc_content RENAME TO content_content;
-- 序列seq_doc_content_id重命名为seq_content_content_id
ALTER TABLE seq_doc_articlermc_id RENAME TO seq_content_content_id;

-- 表doc_related重命名为content_relation
ALTER TABLE doc_related RENAME TO content_relation;
-- 序列seq_doc_related_id重命名为seq_content_relation_id
ALTER TABLE seq_doc_related_id RENAME TO seq_content_relation_id;

-- 删除doc_articlermc_refchannel表
DROP TABLE doc_articlermc_refchannel;

-- 删除doc_recommend表
DROP TABLE doc_recommend;

-- 删除doc_articlermc_citizen表;
DROP TABLE doc_articlermc_citizen;

-- 删除doc_citizen表
DROP TABLE doc_citizen;

-- 删除doc_sharearticle表
DROP TABLE doc_sharearticle;

---- resource
ALTER TABLE res_resource RENAME TO content_resource;
ALTER TABLE content_resource ADD COLUMN create_time timestamp without time zone;
UPDATE content_resource SET create_time = upload_time;
ALTER TABLE content_resource ALTER COLUMN create_time SET NOT NULL;
ALTER TABLE content_resource RENAME COLUMN upload_time TO update_time;
ALTER TABLE content_resource DROP COLUMN new_name;
ALTER TABLE content_resource RENAME COLUMN resource_path TO path;
ALTER TABLE content_resource RENAME COLUMN resource_releasepath TO uri;



