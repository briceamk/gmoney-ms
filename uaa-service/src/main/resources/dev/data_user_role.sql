/***************************************
TABLE USER
***************************************/

INSERT INTO users (id, created_date, first_name, last_name, username, password, email, mobile, enabled, account_non_expired, credentials_non_expired, account_non_locked, state)
 SELECT
    '767cd8d7-9b42-44f9-b2b6-1a55b464d776', current_timestamp, '', 'Administrator', 'admin','{bcrypt}$2y$12$yHR0HI7sArOLPhWo385gd.FBDFIEqGhCnXUW4coWH.4dpO9Gg/q5.', 'brice.mbiandji@g2snet.com', '697649589', '1', '1', '1', '1', 'USER_CREATED'
WHERE NOT EXISTS (
    SELECT * FROM users WHERE id = '767cd8d7-9b42-44f9-b2b6-1a55b464d776'
);

/*********************************************************************************************************************
TABLE ROLE
*********************************************************************************************************************/

INSERT INTO role (id, created_date, name)
SELECT
 '1d053720-8032-4733-92bc-48b7bb1014d3',current_timestamp,'ROLE_ADMIN'
WHERE NOT EXISTS (
    SELECT * FROM role WHERE id = '1d053720-8032-4733-92bc-48b7bb1014d3'
);

INSERT INTO role (id, created_date, name)
SELECT
 'a07d9c1b-1b23-4732-9ccb-22e1b92a43c2',current_timestamp,'ROLE_MANAGER'
WHERE NOT EXISTS (
    SELECT * FROM role WHERE id = 'a07d9c1b-1b23-4732-9ccb-22e1b92a43c2'
);

INSERT INTO role (id, created_date, name)
SELECT
 'c5e85331-b4f3-49cc-b7c3-1cfbaa6be8a2',current_timestamp,'ROLE_USER'
WHERE NOT EXISTS (
    SELECT * FROM role WHERE id = 'c5e85331-b4f3-49cc-b7c3-1cfbaa6be8a2'
);

/*********************************************************************************************************************
TABLE USERS_ROLE
*********************************************************************************************************************/

INSERT INTO users_role (user_id, role_id)
    SELECT
        '767cd8d7-9b42-44f9-b2b6-1a55b464d776', '1d053720-8032-4733-92bc-48b7bb1014d3'
WHERE NOT EXISTS (
    SELECT * FROM users_role WHERE user_id = '767cd8d7-9b42-44f9-b2b6-1a55b464d776' AND role_id = '1d053720-8032-4733-92bc-48b7bb1014d3'
);

INSERT INTO users_role (user_id, role_id)
    SELECT
        '767cd8d7-9b42-44f9-b2b6-1a55b464d776', 'a07d9c1b-1b23-4732-9ccb-22e1b92a43c2'
WHERE NOT EXISTS (
    SELECT * FROM users_role WHERE user_id = '767cd8d7-9b42-44f9-b2b6-1a55b464d776' AND role_id = 'a07d9c1b-1b23-4732-9ccb-22e1b92a43c2'
);

INSERT INTO users_role (user_id, role_id)
    SELECT
        '767cd8d7-9b42-44f9-b2b6-1a55b464d776', 'c5e85331-b4f3-49cc-b7c3-1cfbaa6be8a2'
WHERE NOT EXISTS (
    SELECT * FROM users_role WHERE user_id = '767cd8d7-9b42-44f9-b2b6-1a55b464d776' AND role_id = 'c5e85331-b4f3-49cc-b7c3-1cfbaa6be8a2'
);
