/*********************************************************************************************************************
TABLE PERMISSION
*********************************************************************************************************************/

INSERT INTO permission (id, created_date, name)
SELECT
 'CREATE_LOAN_ID',current_timestamp,'CREATE_LOAN'
WHERE NOT EXISTS (
    SELECT * FROM permission WHERE id = 'CREATE_LOAN_ID'
);

INSERT INTO permission (id, created_date, name)
SELECT
 'UPDATE_LOAN_ID',current_timestamp,'UPDATE_LOAN'
WHERE NOT EXISTS (
    SELECT * FROM permission WHERE id = 'UPDATE_LOAN_ID'
);

INSERT INTO permission (id, created_date, name)
SELECT
 'READ_LOAN_ID',current_timestamp,'READ_LOAN'
WHERE NOT EXISTS (
    SELECT * FROM permission WHERE id = 'READ_LOAN_ID'
);

INSERT INTO permission (id, created_date, name)
SELECT
 'DELETE_LOAN_ID',current_timestamp,'DELETE_LOAN'
WHERE NOT EXISTS (
    SELECT * FROM permission WHERE id = 'DELETE_LOAN_ID'
);


/***************************
ROLE_USER
***************************/
INSERT INTO role_permission (role_id, permission_id)
    SELECT
        'c5e85331-b4f3-49cc-b7c3-1cfbaa6be8a2', 'CREATE_LOAN_ID'
WHERE NOT EXISTS (
    SELECT * FROM role_permission WHERE role_id = 'c5e85331-b4f3-49cc-b7c3-1cfbaa6be8a2' AND permission_id = 'CREATE_LOAN_ID'
);

INSERT INTO role_permission (role_id, permission_id)
    SELECT
        'c5e85331-b4f3-49cc-b7c3-1cfbaa6be8a2', 'UPDATE_LOAN_ID'
WHERE NOT EXISTS (
    SELECT * FROM role_permission WHERE role_id = 'c5e85331-b4f3-49cc-b7c3-1cfbaa6be8a2' AND permission_id = 'UPDATE_LOAN_ID'
);

INSERT INTO role_permission (role_id, permission_id)
    SELECT
        'c5e85331-b4f3-49cc-b7c3-1cfbaa6be8a2', 'READ_LOAN_ID'
WHERE NOT EXISTS (
    SELECT * FROM role_permission WHERE role_id = 'c5e85331-b4f3-49cc-b7c3-1cfbaa6be8a2' AND permission_id = 'READ_LOAN_ID'
);

INSERT INTO role_permission (role_id, permission_id)
    SELECT
        'c5e85331-b4f3-49cc-b7c3-1cfbaa6be8a2', 'DELETE_LOAN_ID'
WHERE NOT EXISTS (
    SELECT * FROM role_permission WHERE role_id = 'c5e85331-b4f3-49cc-b7c3-1cfbaa6be8a2' AND permission_id = 'DELETE_LOAN_ID'
);
