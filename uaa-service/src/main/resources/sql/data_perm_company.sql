/*********************************************************************************************************************
TABLE PERMISSION
*********************************************************************************************************************/

INSERT INTO permission (id, created_date, name)
SELECT
 'CREATE_COMPANY_ID',current_timestamp,'CREATE_COMPANY'
WHERE NOT EXISTS (
    SELECT * FROM permission WHERE id = 'CREATE_COMPANY_ID'
);

INSERT INTO permission (id, created_date, name)
SELECT
 'UPDATE_COMPANY_ID',current_timestamp,'UPDATE_COMPANY'
WHERE NOT EXISTS (
    SELECT * FROM permission WHERE id = 'UPDATE_COMPANY_ID'
);

INSERT INTO permission (id, created_date, name)
SELECT
 'READ_COMPANY_ID',current_timestamp,'READ_COMPANY'
WHERE NOT EXISTS (
    SELECT * FROM permission WHERE id = 'READ_COMPANY_ID'
);

INSERT INTO permission (id, created_date, name)
SELECT
 'DELETE_COMPANY_ID',current_timestamp,'DELETE_COMPANY'
WHERE NOT EXISTS (
    SELECT * FROM permission WHERE id = 'DELETE_COMPANY_ID'
);

/*********************************************************************************************************************
TABLE ROLE_PERMISSION
*********************************************************************************************************************/
/***************************
ROLE_ADMIN
***************************/
INSERT INTO role_permission (role_id, permission_id)
    SELECT
        '1d053720-8032-4733-92bc-48b7bb1014d3', 'CREATE_COMPANY_ID'
WHERE NOT EXISTS (
    SELECT * FROM role_permission WHERE role_id = '1d053720-8032-4733-92bc-48b7bb1014d3' AND permission_id = 'CREATE_COMPANY_ID'
);

INSERT INTO role_permission (role_id, permission_id)
    SELECT
        '1d053720-8032-4733-92bc-48b7bb1014d3', 'UPDATE_COMPANY_ID'
WHERE NOT EXISTS (
    SELECT * FROM role_permission WHERE role_id = '1d053720-8032-4733-92bc-48b7bb1014d3' AND permission_id = 'UPDATE_COMPANY_ID'
);

INSERT INTO role_permission (role_id, permission_id)
    SELECT
        '1d053720-8032-4733-92bc-48b7bb1014d3', 'READ_COMPANY_ID'
WHERE NOT EXISTS (
    SELECT * FROM role_permission WHERE role_id = '1d053720-8032-4733-92bc-48b7bb1014d3' AND permission_id = 'READ_COMPANY_ID'
);

INSERT INTO role_permission (role_id, permission_id)
    SELECT
        '1d053720-8032-4733-92bc-48b7bb1014d3', 'DELETE_COMPANY_ID'
WHERE NOT EXISTS (
    SELECT * FROM role_permission WHERE role_id = '1d053720-8032-4733-92bc-48b7bb1014d3' AND permission_id = 'DELETE_COMPANY_ID'
);

/***************************
ROLE_MANAGER
***************************/

INSERT INTO role_permission (role_id, permission_id)
    SELECT
        'a07d9c1b-1b23-4732-9ccb-22e1b92a43c2', 'READ_COMPANY_ID'
WHERE NOT EXISTS (
    SELECT * FROM role_permission WHERE role_id = 'a07d9c1b-1b23-4732-9ccb-22e1b92a43c2' AND permission_id = 'READ_COMPANY_ID'
);

/***************************
ROLE_USER
***************************/

INSERT INTO role_permission (role_id, permission_id)
    SELECT
        'c5e85331-b4f3-49cc-b7c3-1cfbaa6be8a2', 'READ_COMPANY_ID'
WHERE NOT EXISTS (
    SELECT * FROM role_permission WHERE role_id = 'c5e85331-b4f3-49cc-b7c3-1cfbaa6be8a2' AND permission_id = 'READ_COMPANY_ID'
);
