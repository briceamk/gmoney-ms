DROP ROLE IF EXISTS gmoney;
DROP DATABASE IF EXISTS gmoney_account_db;
DROP DATABASE IF EXISTS gmoney_compnay_db;
DROP DATABASE IF EXISTS gmoney_cron_db;
DROP DATABASE IF EXISTS gmoney_loan_db;
DROP DATABASE IF EXISTS gmoney_notification_db;
DROP DATABASE IF EXISTS gmoney_rule_db;
DROP DATABASE IF EXISTS gmoney_partner_db;
DROP DATABASE IF EXISTS gmoney_transaction_db;
DROP DATABASE IF EXISTS gmoney_uaa_db;
CREATE ROLE gmoney WITH
	LOGIN
	NOSUPERUSER
	CREATEDB
	NOCREATEROLE
	INHERIT
	NOREPLICATION
	CONNECTION LIMIT -1
	PASSWORD '?.@159MmmmM@..!';
CREATE DATABASE gmoney_account_db
    WITH 
    OWNER = gmoney
    ENCODING = 'UTF8'
    LC_COLLATE = 'en_US.utf8'
    LC_CTYPE = 'en_US.utf8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;
CREATE DATABASE gmoney_compnay_db
    WITH 
    OWNER = gmoney
    ENCODING = 'UTF8'
    LC_COLLATE = 'en_US.utf8'
    LC_CTYPE = 'en_US.utf8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;
CREATE DATABASE gmoney_cron_db
    WITH 
    OWNER = gmoney
    ENCODING = 'UTF8'
    LC_COLLATE = 'en_US.utf8'
    LC_CTYPE = 'en_US.utf8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;
CREATE DATABASE gmoney_loan_db
    WITH 
    OWNER = gmoney
    ENCODING = 'UTF8'
    LC_COLLATE = 'en_US.utf8'
    LC_CTYPE = 'en_US.utf8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;
CREATE DATABASE gmoney_notification_db
    WITH 
    OWNER = gmoney
    ENCODING = 'UTF8'
    LC_COLLATE = 'en_US.utf8'
    LC_CTYPE = 'en_US.utf8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;
CREATE DATABASE gmoney_partner_db
    WITH 
    OWNER = gmoney
    ENCODING = 'UTF8'
    LC_COLLATE = 'en_US.utf8'
    LC_CTYPE = 'en_US.utf8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;
CREATE DATABASE gmoney_rule_db
    WITH 
    OWNER = gmoney
    ENCODING = 'UTF8'
    LC_COLLATE = 'en_US.utf8'
    LC_CTYPE = 'en_US.utf8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;
CREATE DATABASE gmoney_transaction_db
    WITH 
    OWNER = gmoney
    ENCODING = 'UTF8'
    LC_COLLATE = 'en_US.utf8'
    LC_CTYPE = 'en_US.utf8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;
CREATE DATABASE gmoney_uaa_db
    WITH 
    OWNER = gmoney
    ENCODING = 'UTF8'
    LC_COLLATE = 'en_US.utf8'
    LC_CTYPE = 'en_US.utf8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;
