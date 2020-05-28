/*********************************************************************************************************************
TABLE PERMISSION
*********************************************************************************************************************/

INSERT INTO permission (id, created_date, name)
SELECT
 'CREATE_MAIL_ID',current_timestamp,'CREATE_MAIL'
WHERE NOT EXISTS (
    SELECT * FROM permission WHERE id = 'CREATE_MAIL_ID'
);

INSERT INTO permission (id, created_date, name)
SELECT
 'UPDATE_MAIL_ID',current_timestamp,'UPDATE_MAIL'
WHERE NOT EXISTS (
    SELECT * FROM permission WHERE id = 'UPDATE_MAIL_ID'
);

INSERT INTO permission (id, created_date, name)
SELECT
 'READ_MAIL_ID',current_timestamp,'READ_MAIL'
WHERE NOT EXISTS (
    SELECT * FROM permission WHERE id = 'READ_MAIL_ID'
);

INSERT INTO permission (id, created_date, name)
SELECT
 'DELETE_MAIL_ID',current_timestamp,'DELETE_MAIL'
WHERE NOT EXISTS (
    SELECT * FROM permission WHERE id = 'DELETE_MAIL_ID'
);

INSERT INTO permission (id, created_date, name)
SELECT
 'SEND_MAIL_ID',current_timestamp,'SEND_MAIL'
WHERE NOT EXISTS (
    SELECT * FROM permission WHERE id = 'SEND_MAIL_ID'
);


INSERT INTO permission (id, created_date, name)
SELECT
 'SEND_ALL_MAIL_ID',current_timestamp,'SEND_ALL_MAIL'
WHERE NOT EXISTS (
    SELECT * FROM permission WHERE id = 'SEND_ALL_MAIL_ID'
);


/***************************
ROLE_ADMIN
***************************/
INSERT INTO role_permission (role_id, permission_id)
    SELECT
        '1d053720-8032-4733-92bc-48b7bb1014d3', 'CREATE_MAIL_ID'
WHERE NOT EXISTS (
    SELECT * FROM role_permission WHERE role_id = '1d053720-8032-4733-92bc-48b7bb1014d3' AND permission_id = 'CREATE_MAIL_ID'
);

INSERT INTO role_permission (role_id, permission_id)
    SELECT
        '1d053720-8032-4733-92bc-48b7bb1014d3', 'UPDATE_MAIL_ID'
WHERE NOT EXISTS (
    SELECT * FROM role_permission WHERE role_id = '1d053720-8032-4733-92bc-48b7bb1014d3' AND permission_id = 'UPDATE_MAIL_ID'
);

INSERT INTO role_permission (role_id, permission_id)
    SELECT
        '1d053720-8032-4733-92bc-48b7bb1014d3', 'READ_MAIL_ID'
WHERE NOT EXISTS (
    SELECT * FROM role_permission WHERE role_id = '1d053720-8032-4733-92bc-48b7bb1014d3' AND permission_id = 'READ_MAIL_ID'
);

INSERT INTO role_permission (role_id, permission_id)
    SELECT
        '1d053720-8032-4733-92bc-48b7bb1014d3', 'DELETE_MAIL_ID'
WHERE NOT EXISTS (
    SELECT * FROM role_permission WHERE role_id = '1d053720-8032-4733-92bc-48b7bb1014d3' AND permission_id = 'DELETE_MAIL_ID'
);


INSERT INTO role_permission (role_id, permission_id)
    SELECT
        '1d053720-8032-4733-92bc-48b7bb1014d3', 'SEND_MAIL_ID'
WHERE NOT EXISTS (
    SELECT * FROM role_permission WHERE role_id = '1d053720-8032-4733-92bc-48b7bb1014d3' AND permission_id = 'SEND_MAIL_ID'
);


INSERT INTO role_permission (role_id, permission_id)
    SELECT
        '1d053720-8032-4733-92bc-48b7bb1014d3', 'SEND_ALL_MAIL_ID'
WHERE NOT EXISTS (
    SELECT * FROM role_permission WHERE role_id = '1d053720-8032-4733-92bc-48b7bb1014d3' AND permission_id = 'SEND_ALL_MAIL_ID'
);

/***************************
ROLE_MANAGER
***************************/

INSERT INTO role_permission (role_id, permission_id)
    SELECT
        'a07d9c1b-1b23-4732-9ccb-22e1b92a43c2', 'CREATE_MAIL_ID'
WHERE NOT EXISTS (
    SELECT * FROM role_permission WHERE role_id = 'a07d9c1b-1b23-4732-9ccb-22e1b92a43c2' AND permission_id = 'CREATE_MAIL_ID'
);

INSERT INTO role_permission (role_id, permission_id)
    SELECT
        'a07d9c1b-1b23-4732-9ccb-22e1b92a43c2', 'UPDATE_MAIL_ID'
WHERE NOT EXISTS (
    SELECT * FROM role_permission WHERE role_id = 'a07d9c1b-1b23-4732-9ccb-22e1b92a43c2' AND permission_id = 'UPDATE_MAIL_ID'
);

INSERT INTO role_permission (role_id, permission_id)
    SELECT
        'a07d9c1b-1b23-4732-9ccb-22e1b92a43c2', 'READ_MAIL_ID'
WHERE NOT EXISTS (
    SELECT * FROM role_permission WHERE role_id = 'a07d9c1b-1b23-4732-9ccb-22e1b92a43c2' AND permission_id = 'READ_MAIL_ID'
);

INSERT INTO role_permission (role_id, permission_id)
    SELECT
        'a07d9c1b-1b23-4732-9ccb-22e1b92a43c2', 'DELETE_MAIL_ID'
WHERE NOT EXISTS (
    SELECT * FROM role_permission WHERE role_id = 'a07d9c1b-1b23-4732-9ccb-22e1b92a43c2' AND permission_id = 'DELETE_MAIL_ID'
);


INSERT INTO role_permission (role_id, permission_id)
    SELECT
        'a07d9c1b-1b23-4732-9ccb-22e1b92a43c2', 'SEND_MAIL_ID'
WHERE NOT EXISTS (
    SELECT * FROM role_permission WHERE role_id = 'a07d9c1b-1b23-4732-9ccb-22e1b92a43c2' AND permission_id = 'SEND_MAIL_ID'
);


INSERT INTO role_permission (role_id, permission_id)
    SELECT
        'a07d9c1b-1b23-4732-9ccb-22e1b92a43c2', 'SEND_ALL_MAIL_ID'
WHERE NOT EXISTS (
    SELECT * FROM role_permission WHERE role_id = 'a07d9c1b-1b23-4732-9ccb-22e1b92a43c2' AND permission_id = 'SEND_ALL_MAIL_ID'
);

