/*********************************************************************************************************************
TABLE PERMISSION
*********************************************************************************************************************/

INSERT INTO permission (id, created_date, name)
SELECT
 'CREATE_USER_ID',current_timestamp,'CREATE_USER'
WHERE NOT EXISTS (
    SELECT * FROM permission WHERE id = 'CREATE_USER_ID'
);

INSERT INTO permission (id, created_date, name)
SELECT
 'UPDATE_USER_ID',current_timestamp,'UPDATE_USER'
WHERE NOT EXISTS (
    SELECT * FROM permission WHERE id = 'UPDATE_USER_ID'
);

INSERT INTO permission (id, created_date, name)
SELECT
 'READ_USER_ID',current_timestamp,'READ_USER'
WHERE NOT EXISTS (
    SELECT * FROM permission WHERE id = 'READ_USER_ID'
);

INSERT INTO permission (id, created_date, name)
SELECT
 'DELETE_USER_ID',current_timestamp,'DELETE_USER'
WHERE NOT EXISTS (
    SELECT * FROM permission WHERE id = 'DELETE_USER_ID'
);


/***************************
ROLE_ADMIN
***************************/
INSERT INTO role_permission (role_id, permission_id)
    SELECT
        '1d053720-8032-4733-92bc-48b7bb1014d3', 'CREATE_USER_ID'
WHERE NOT EXISTS (
    SELECT * FROM role_permission WHERE role_id = '1d053720-8032-4733-92bc-48b7bb1014d3' AND permission_id = 'CREATE_USER_ID'
);

INSERT INTO role_permission (role_id, permission_id)
    SELECT
        '1d053720-8032-4733-92bc-48b7bb1014d3', 'UPDATE_USER_ID'
WHERE NOT EXISTS (
    SELECT * FROM role_permission WHERE role_id = '1d053720-8032-4733-92bc-48b7bb1014d3' AND permission_id = 'UPDATE_USER_ID'
);

INSERT INTO role_permission (role_id, permission_id)
    SELECT
        '1d053720-8032-4733-92bc-48b7bb1014d3', 'READ_USER_ID'
WHERE NOT EXISTS (
    SELECT * FROM role_permission WHERE role_id = '1d053720-8032-4733-92bc-48b7bb1014d3' AND permission_id = 'READ_USER_ID'
);

INSERT INTO role_permission (role_id, permission_id)
    SELECT
        '1d053720-8032-4733-92bc-48b7bb1014d3', 'DELETE_USER_ID'
WHERE NOT EXISTS (
    SELECT * FROM role_permission WHERE role_id = '1d053720-8032-4733-92bc-48b7bb1014d3' AND permission_id = 'DELETE_USER_ID'
);

/***************************
ROLE_MANAGER
***************************/

INSERT INTO role_permission (role_id, permission_id)
    SELECT
        'a07d9c1b-1b23-4732-9ccb-22e1b92a43c2', 'READ_USER_ID'
WHERE NOT EXISTS (
    SELECT * FROM role_permission WHERE role_id = 'a07d9c1b-1b23-4732-9ccb-22e1b92a43c2' AND permission_id = 'READ_USER_ID'
);

INSERT INTO role_permission (role_id, permission_id)
    SELECT
        'a07d9c1b-1b23-4732-9ccb-22e1b92a43c2', 'UPDATE_USER_ID'
WHERE NOT EXISTS (
    SELECT * FROM role_permission WHERE role_id = 'a07d9c1b-1b23-4732-9ccb-22e1b92a43c2' AND permission_id = 'UPDATE_USER_ID'
);

/***************************
ROLE_USER
***************************/

INSERT INTO role_permission (role_id, permission_id)
    SELECT
        'c5e85331-b4f3-49cc-b7c3-1cfbaa6be8a2', 'READ_USER_ID'
WHERE NOT EXISTS (
    SELECT * FROM role_permission WHERE role_id = 'c5e85331-b4f3-49cc-b7c3-1cfbaa6be8a2' AND permission_id = 'READ_USER_ID'
);
