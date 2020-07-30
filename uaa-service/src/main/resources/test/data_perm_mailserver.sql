/*********************************************************************************************************************
TABLE PERMISSION
*********************************************************************************************************************/

INSERT INTO permission (id, created_date, name)
SELECT
 'CREATE_MAIL_SERVER_ID',current_timestamp,'CREATE_MAIL_SERVER'
WHERE NOT EXISTS (
    SELECT * FROM permission WHERE id = 'CREATE_MAIL_SERVER_ID'
);

INSERT INTO permission (id, created_date, name)
SELECT
 'UPDATE_MAIL_SERVER_ID',current_timestamp,'UPDATE_MAIL_SERVER'
WHERE NOT EXISTS (
    SELECT * FROM permission WHERE id = 'UPDATE_MAIL_SERVER_ID'
);

INSERT INTO permission (id, created_date, name)
SELECT
 'READ_MAIL_SERVER_ID',current_timestamp,'READ_MAIL_SERVER'
WHERE NOT EXISTS (
    SELECT * FROM permission WHERE id = 'READ_MAIL_SERVER_ID'
);

INSERT INTO permission (id, created_date, name)
SELECT
 'DELETE_MAIL_SERVER_ID',current_timestamp,'DELETE_MAIL_SERVER'
WHERE NOT EXISTS (
    SELECT * FROM permission WHERE id = 'DELETE_MAIL_SERVER_ID'
);

INSERT INTO permission (id, created_date, name)
SELECT
 'TEST_MAIL_SERVER_ID',current_timestamp,'TEST_MAIL_SERVER'
WHERE NOT EXISTS (
    SELECT * FROM permission WHERE id = 'TEST_MAIL_SERVER_ID'
);

/***************************
ROLE_ADMIN
***************************/
INSERT INTO role_permission (role_id, permission_id)
    SELECT
        '1d053720-8032-4733-92bc-48b7bb1014d3', 'CREATE_MAIL_SERVER_ID'
WHERE NOT EXISTS (
    SELECT * FROM role_permission WHERE role_id = '1d053720-8032-4733-92bc-48b7bb1014d3' AND permission_id = 'CREATE_MAIL_SERVER_ID'
);

INSERT INTO role_permission (role_id, permission_id)
    SELECT
        '1d053720-8032-4733-92bc-48b7bb1014d3', 'UPDATE_MAIL_SERVER_ID'
WHERE NOT EXISTS (
    SELECT * FROM role_permission WHERE role_id = '1d053720-8032-4733-92bc-48b7bb1014d3' AND permission_id = 'UPDATE_MAIL_SERVER_ID'
);

INSERT INTO role_permission (role_id, permission_id)
    SELECT
        '1d053720-8032-4733-92bc-48b7bb1014d3', 'READ_MAIL_SERVER_ID'
WHERE NOT EXISTS (
    SELECT * FROM role_permission WHERE role_id = '1d053720-8032-4733-92bc-48b7bb1014d3' AND permission_id = 'READ_MAIL_SERVER_ID'
);

INSERT INTO role_permission (role_id, permission_id)
    SELECT
        '1d053720-8032-4733-92bc-48b7bb1014d3', 'DELETE_MAIL_SERVER_ID'
WHERE NOT EXISTS (
    SELECT * FROM role_permission WHERE role_id = '1d053720-8032-4733-92bc-48b7bb1014d3' AND permission_id = 'DELETE_MAIL_SERVER_ID'
);


INSERT INTO role_permission (role_id, permission_id)
    SELECT
        '1d053720-8032-4733-92bc-48b7bb1014d3', 'TEST_MAIL_SERVER_ID'
WHERE NOT EXISTS (
    SELECT * FROM role_permission WHERE role_id = '1d053720-8032-4733-92bc-48b7bb1014d3' AND permission_id = 'TEST_MAIL_SERVER_ID'
);



