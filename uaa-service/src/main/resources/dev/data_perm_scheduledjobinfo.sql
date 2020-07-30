/*********************************************************************************************************************
TABLE PERMISSION
*********************************************************************************************************************/

INSERT INTO permission (id, created_date, name)
SELECT
 'CREATE_JOB_INFO_ID',current_timestamp,'CREATE_JOB_INFO'
WHERE NOT EXISTS (
    SELECT * FROM permission WHERE id = 'CREATE_JOB_INFO_ID'
);

INSERT INTO permission (id, created_date, name)
SELECT
 'UPDATE_JOB_INFO_ID',current_timestamp,'UPDATE_JOB_INFO'
WHERE NOT EXISTS (
    SELECT * FROM permission WHERE id = 'UPDATE_JOB_INFO_ID'
);

INSERT INTO permission (id, created_date, name)
SELECT
 'READ_JOB_INFO_ID',current_timestamp,'READ_JOB_INFO'
WHERE NOT EXISTS (
    SELECT * FROM permission WHERE id = 'READ_JOB_INFO_ID'
);

INSERT INTO permission (id, created_date, name)
SELECT
 'DELETE_JOB_INFO_ID',current_timestamp,'DELETE_JOB_INFO'
WHERE NOT EXISTS (
    SELECT * FROM permission WHERE id = 'DELETE_JOB_INFO_ID'
);

INSERT INTO permission (id, created_date, name)
SELECT
 'START_ALL_SCHEDULER_ID',current_timestamp,'START_ALL_SCHEDULER'
WHERE NOT EXISTS (
    SELECT * FROM permission WHERE id = 'START_ALL_SCHEDULER_ID'
);


INSERT INTO permission (id, created_date, name)
SELECT
 'NEW_SCHEDULER_ID',current_timestamp,'NEW_SCHEDULER'
WHERE NOT EXISTS (
    SELECT * FROM permission WHERE id = 'NEW_SCHEDULER_ID'
);


INSERT INTO permission (id, created_date, name)
SELECT
 'RE_SCHEDULER_ID',current_timestamp,'RE_SCHEDULER'
WHERE NOT EXISTS (
    SELECT * FROM permission WHERE id = 'RE_SCHEDULER_ID'
);


INSERT INTO permission (id, created_date, name)
SELECT
 'UN_SCHEDULER_ID',current_timestamp,'UN_SCHEDULER'
WHERE NOT EXISTS (
    SELECT * FROM permission WHERE id = 'UN_SCHEDULER_ID'
);


INSERT INTO permission (id, created_date, name)
SELECT
 'DELETE_SCHEDULER_ID',current_timestamp,'DELETE_SCHEDULER'
WHERE NOT EXISTS (
    SELECT * FROM permission WHERE id = 'DELETE_SCHEDULER_ID'
);

INSERT INTO permission (id, created_date, name)
SELECT
 'PAUSE_SCHEDULER_ID',current_timestamp,'PAUSE_SCHEDULER'
WHERE NOT EXISTS (
    SELECT * FROM permission WHERE id = 'PAUSE_SCHEDULER_ID'
);

INSERT INTO permission (id, created_date, name)
SELECT
 'RESUME_SCHEDULER_ID',current_timestamp,'RESUME_SCHEDULER'
WHERE NOT EXISTS (
    SELECT * FROM permission WHERE id = 'RESUME_SCHEDULER_ID'
);

INSERT INTO permission (id, created_date, name)
SELECT
 'START_SCHEDULER_ID',current_timestamp,'START_SCHEDULER'
WHERE NOT EXISTS (
    SELECT * FROM permission WHERE id = 'START_SCHEDULER_ID'
);



/***************************
ROLE_ADMIN
***************************/
INSERT INTO role_permission (role_id, permission_id)
    SELECT
        '1d053720-8032-4733-92bc-48b7bb1014d3', 'CREATE_JOB_INFO_ID'
WHERE NOT EXISTS (
    SELECT * FROM role_permission WHERE role_id = '1d053720-8032-4733-92bc-48b7bb1014d3' AND permission_id = 'CREATE_JOB_INFO_ID'
);

INSERT INTO role_permission (role_id, permission_id)
    SELECT
        '1d053720-8032-4733-92bc-48b7bb1014d3', 'UPDATE_JOB_INFO_ID'
WHERE NOT EXISTS (
    SELECT * FROM role_permission WHERE role_id = '1d053720-8032-4733-92bc-48b7bb1014d3' AND permission_id = 'UPDATE_JOB_INFO_ID'
);

INSERT INTO role_permission (role_id, permission_id)
    SELECT
        '1d053720-8032-4733-92bc-48b7bb1014d3', 'READ_JOB_INFO_ID'
WHERE NOT EXISTS (
    SELECT * FROM role_permission WHERE role_id = '1d053720-8032-4733-92bc-48b7bb1014d3' AND permission_id = 'READ_JOB_INFO_ID'
);

INSERT INTO role_permission (role_id, permission_id)
    SELECT
        '1d053720-8032-4733-92bc-48b7bb1014d3', 'DELETE_JOB_INFO_ID'
WHERE NOT EXISTS (
    SELECT * FROM role_permission WHERE role_id = '1d053720-8032-4733-92bc-48b7bb1014d3' AND permission_id = 'DELETE_JOB_INFO_ID'
);


INSERT INTO role_permission (role_id, permission_id)
    SELECT
        '1d053720-8032-4733-92bc-48b7bb1014d3', 'START_ALL_SCHEDULER_ID'
WHERE NOT EXISTS (
    SELECT * FROM role_permission WHERE role_id = '1d053720-8032-4733-92bc-48b7bb1014d3' AND permission_id = 'START_ALL_SCHEDULER_ID'
);

INSERT INTO role_permission (role_id, permission_id)
    SELECT
        '1d053720-8032-4733-92bc-48b7bb1014d3', 'NEW_SCHEDULER_ID'
WHERE NOT EXISTS (
    SELECT * FROM role_permission WHERE role_id = '1d053720-8032-4733-92bc-48b7bb1014d3' AND permission_id = 'NEW_SCHEDULER_ID'
);


INSERT INTO role_permission (role_id, permission_id)
    SELECT
        '1d053720-8032-4733-92bc-48b7bb1014d3', 'RE_SCHEDULER_ID'
WHERE NOT EXISTS (
    SELECT * FROM role_permission WHERE role_id = '1d053720-8032-4733-92bc-48b7bb1014d3' AND permission_id = 'RE_SCHEDULER_ID'
);


INSERT INTO role_permission (role_id, permission_id)
    SELECT
        '1d053720-8032-4733-92bc-48b7bb1014d3', 'UN_SCHEDULER_ID'
WHERE NOT EXISTS (
    SELECT * FROM role_permission WHERE role_id = '1d053720-8032-4733-92bc-48b7bb1014d3' AND permission_id = 'UN_SCHEDULER_ID'
);


INSERT INTO role_permission (role_id, permission_id)
    SELECT
        '1d053720-8032-4733-92bc-48b7bb1014d3', 'DELETE_SCHEDULER_ID'
WHERE NOT EXISTS (
    SELECT * FROM role_permission WHERE role_id = '1d053720-8032-4733-92bc-48b7bb1014d3' AND permission_id = 'DELETE_SCHEDULER_ID'
);


INSERT INTO role_permission (role_id, permission_id)
    SELECT
        '1d053720-8032-4733-92bc-48b7bb1014d3', 'PAUSE_SCHEDULER_ID'
WHERE NOT EXISTS (
    SELECT * FROM role_permission WHERE role_id = '1d053720-8032-4733-92bc-48b7bb1014d3' AND permission_id = 'PAUSE_SCHEDULER_ID'
);


INSERT INTO role_permission (role_id, permission_id)
    SELECT
        '1d053720-8032-4733-92bc-48b7bb1014d3', 'RESUME_SCHEDULER_ID'
WHERE NOT EXISTS (
    SELECT * FROM role_permission WHERE role_id = '1d053720-8032-4733-92bc-48b7bb1014d3' AND permission_id = 'RESUME_SCHEDULER_ID'
);


INSERT INTO role_permission (role_id, permission_id)
    SELECT
        '1d053720-8032-4733-92bc-48b7bb1014d3', 'START_SCHEDULER_ID'
WHERE NOT EXISTS (
    SELECT * FROM role_permission WHERE role_id = '1d053720-8032-4733-92bc-48b7bb1014d3' AND permission_id = 'START_SCHEDULER_ID'
);



