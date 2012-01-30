-- ACL Schema SQL for PostgreSQL

/*
DROP TABLE acl_entry;
DROP TABLE acl_object_identity;
DROP TABLE acl_class;
DROP TABLE acl_sid;
*/

CREATE TABLE acl_sid(
    id BIGSERIAL NOT NULL PRIMARY KEY,
    principal BOOLEAN NOT NULL,
    sid VARCHAR(100) NOT NULL,
    CONSTRAINT unique_uk_1 UNIQUE(sid,principal)
);

CREATE TABLE acl_class(
    id BIGSERIAL NOT NULL PRIMARY KEY,
    class VARCHAR(100) NOT NULL,
    CONSTRAINT unique_uk_2 UNIQUE(class)
);

CREATE TABLE acl_object_identity(
    id BIGSERIAL PRIMARY KEY,
    object_id_class BIGINT NOT NULL,
    object_id_identity BIGINT NOT NULL,
    parent_object BIGINT,
    owner_sid BIGINT,
    entries_inheriting BOOLEAN NOT NULL,
    CONSTRAINT unique_uk_3 UNIQUE(object_id_class,object_id_identity),
    CONSTRAINT foreign_fk_1 FOREIGN KEY(parent_object)REFERENCES acl_object_identity(id),
    CONSTRAINT foreign_fk_2 FOREIGN KEY(object_id_class)REFERENCES acl_class(id),
    CONSTRAINT foreign_fk_3 FOREIGN KEY(owner_sid)REFERENCES acl_sid(id)
);

CREATE TABLE acl_entry(
    id BIGSERIAL PRIMARY KEY,
    acl_object_identity BIGINT NOT NULL,
    ace_order INT NOT NULL,
    sid bigint NOT NULL,
    mask INTEGER NOT NULL,
    granting BOOLEAN NOT NULL,
    audit_success BOOLEAN NOT NULL,
    audit_failure BOOLEAN NOT NULL,
    CONSTRAINT unique_uk_4 UNIQUE(acl_object_identity,ace_order),
    CONSTRAINT foreign_fk_4 FOREIGN KEY(acl_object_identity) REFERENCES acl_object_identity(id),
    CONSTRAINT foreign_fk_5 FOREIGN KEY(sid) REFERENCES acl_sid(id)
);

-- Quartz Schema SQL for PostgreSQL

/*
DROP TABLE qrtz_job_listeners;
DROP TABLE qrtz_trigger_listeners;
DROP TABLE qrtz_fired_triggers;
DROP TABLE qrtz_paused_trigger_grps;
DROP TABLE qrtz_scheduler_state;
DROP TABLE qrtz_locks;
DROP TABLE qrtz_simple_triggers;
DROP TABLE qrtz_cron_triggers;
DROP TABLE qrtz_blob_triggers;
DROP TABLE qrtz_triggers;
DROP TABLE qrtz_job_details;
DROP TABLE qrtz_calendars;
*/

CREATE TABLE qrtz_job_details
  (
    job_name  VARCHAR(200) NOT NULL,
    job_group VARCHAR(200) NOT NULL,
    description VARCHAR(250) NULL,
    job_class_name   VARCHAR(250) NOT NULL, 
    is_durable BOOL NOT NULL,
    is_volatile BOOL NOT NULL,
    is_stateful BOOL NOT NULL,
    requests_recovery BOOL NOT NULL,
    job_data BYTEA NULL,
    PRIMARY KEY (job_name,job_group)
);

CREATE TABLE qrtz_job_listeners
  (
    job_name  VARCHAR(200) NOT NULL, 
    job_group VARCHAR(200) NOT NULL,
    job_listener VARCHAR(200) NOT NULL,
    PRIMARY KEY (job_name,job_group,job_listener),
    FOREIGN KEY (job_name,job_group) 
	REFERENCES qrtz_job_details(job_name,job_group) 
);

CREATE TABLE qrtz_triggers
  (
    trigger_name VARCHAR(200) NOT NULL,
    trigger_group VARCHAR(200) NOT NULL,
    job_name  VARCHAR(200) NOT NULL, 
    job_group VARCHAR(200) NOT NULL,
    is_volatile BOOL NOT NULL,
    description VARCHAR(250) NULL,
    next_fire_time BIGINT NULL,
    prev_fire_time BIGINT NULL,
    priority INTEGER NULL,
    trigger_state VARCHAR(16) NOT NULL,
    trigger_type VARCHAR(8) NOT NULL,
    start_time BIGINT NOT NULL,
    end_time BIGINT NULL,
    calendar_name VARCHAR(200) NULL,
    misfire_instr SMALLINT NULL,
    job_data bytea NULL,
    PRIMARY KEY (trigger_name,trigger_group),
    FOREIGN KEY (job_name,job_group) 
	REFERENCES qrtz_job_details(job_name,job_group) 
);

CREATE TABLE qrtz_simple_triggers
  (
    trigger_name VARCHAR(200) NOT NULL,
    trigger_group VARCHAR(200) NOT NULL,
    repeat_count BIGINT NOT NULL,
    repeat_interval BIGINT NOT NULL,
    times_triggered BIGINT NOT NULL,
    PRIMARY KEY (trigger_name,trigger_group),
    FOREIGN KEY (trigger_name,trigger_group) 
	REFERENCES qrtz_triggers(trigger_name,trigger_group)
);

CREATE TABLE qrtz_cron_triggers
  (
    trigger_name VARCHAR(200) NOT NULL,
    trigger_group VARCHAR(200) NOT NULL,
    cron_expression VARCHAR(120) NOT NULL,
    time_zone_id VARCHAR(80),
    PRIMARY KEY (trigger_name,trigger_group),
    FOREIGN KEY (trigger_name,trigger_group) 
	REFERENCES qrtz_triggers(trigger_name,trigger_group)
);

CREATE TABLE qrtz_blob_triggers
  (
    trigger_name VARCHAR(200) NOT NULL,
    trigger_group VARCHAR(200) NOT NULL,
    blob_data BYTEA NULL,
    PRIMARY KEY (trigger_name,trigger_group),
    FOREIGN KEY (trigger_name,trigger_group) 
    REFERENCES qrtz_triggers(trigger_name,trigger_group)
);

CREATE TABLE qrtz_trigger_listeners
  (
    trigger_name  VARCHAR(200) NOT NULL, 
    trigger_group VARCHAR(200) NOT NULL,
    trigger_listener VARCHAR(200) NOT NULL,
    PRIMARY KEY (trigger_name,trigger_group,trigger_listener),
    FOREIGN KEY (trigger_name,trigger_group) 
	REFERENCES qrtz_triggers(trigger_name,trigger_group)
);


CREATE TABLE qrtz_calendars
  (
    calendar_name  VARCHAR(200) NOT NULL, 
    calendar BYTEA NOT NULL,
    PRIMARY KEY (calendar_name)
);


CREATE TABLE qrtz_paused_trigger_grps
  (
    trigger_group  VARCHAR(200) NOT NULL, 
    PRIMARY KEY (trigger_group)
);

CREATE TABLE qrtz_fired_triggers 
  (
    entry_id VARCHAR(95) NOT NULL,
    trigger_name VARCHAR(200) NOT NULL,
    trigger_group VARCHAR(200) NOT NULL,
    is_volatile BOOL NOT NULL,
    instance_name VARCHAR(200) NOT NULL,
    fired_time BIGINT NOT NULL,
    priority INTEGER NOT NULL,
    state VARCHAR(16) NOT NULL,
    job_name VARCHAR(200) NULL,
    job_group VARCHAR(200) NULL,
    is_stateful BOOL NULL,
    requests_recovery BOOL NULL,
    PRIMARY KEY (entry_id)
);

CREATE TABLE qrtz_scheduler_state 
  (
    instance_name VARCHAR(200) NOT NULL,
    last_checkin_time BIGINT NOT NULL,
    checkin_interval BIGINT NOT NULL,
    PRIMARY KEY (instance_name)
);

CREATE TABLE qrtz_locks
  (
    lock_name  VARCHAR(40) NOT NULL, 
    PRIMARY KEY (lock_name)
);

CREATE INDEX idx_qrtz_j_req_recovery ON qrtz_job_details(requests_recovery);
CREATE INDEX idx_qrtz_t_next_fire_time ON qrtz_triggers(next_fire_time);
CREATE INDEX idx_qrtz_t_state ON qrtz_triggers(trigger_state);
CREATE INDEX idx_qrtz_t_nft_st ON qrtz_triggers(next_fire_time,trigger_state);
CREATE INDEX idx_qrtz_t_volatile ON qrtz_triggers(is_volatile);
CREATE INDEX idx_qrtz_ft_trig_name ON qrtz_fired_triggers(trigger_name);
CREATE INDEX idx_qrtz_ft_trig_group ON qrtz_fired_triggers(trigger_group);
CREATE INDEX idx_qrtz_ft_trig_nm_gp ON qrtz_fired_triggers(trigger_name,trigger_group);
CREATE INDEX idx_qrtz_ft_trig_volatile ON qrtz_fired_triggers(is_volatile);
CREATE INDEX idx_qrtz_ft_trig_inst_name ON qrtz_fired_triggers(instance_name);
CREATE INDEX idx_qrtz_ft_job_name ON qrtz_fired_triggers(job_name);
CREATE INDEX idx_qrtz_ft_job_group ON qrtz_fired_triggers(job_group);
CREATE INDEX idx_qrtz_ft_job_stateful ON qrtz_fired_triggers(is_stateful);
CREATE INDEX idx_qrtz_ft_job_req_recovery ON qrtz_fired_triggers(requests_recovery);