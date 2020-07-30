CREATE USER gmoney WITH PASSWORD '?.@159MmmmM@++' CREATEDB;

CREATE SCHEMA  IF NOT EXISTS public AUTHORIZATION gmoney;

CREATE DATABASE IF NOT EXISTS gmoney_company_db
    WITH
    OWNER = gmoney
    ENCODING = 'UTF8'
    LC_COLLATE = 'French_France.1252'
    LC_CTYPE = 'French_France.1252'
    CONNECTION LIMIT = -1;

CREATE DATABASE IF NOT EXISTS gmoney_cron_db
    WITH
    OWNER = gmoney
    ENCODING = 'UTF8'
    LC_COLLATE = 'French_France.1252'
    LC_CTYPE = 'French_France.1252'
    CONNECTION LIMIT = -1;

CREATE DATABASE IF NOT EXISTS gmoney_loan_db
    WITH
    OWNER = gmoney
    ENCODING = 'UTF8'
    LC_COLLATE = 'French_France.1252'
    LC_CTYPE = 'French_France.1252'
    CONNECTION LIMIT = -1;

CREATE DATABASE IF NOT EXISTS gmoney_notification_db
    WITH
    OWNER = gmoney
    ENCODING = 'UTF8'
    LC_COLLATE = 'French_France.1252'
    LC_CTYPE = 'French_France.1252'
    CONNECTION LIMIT = -1;

CREATE DATABASE IF NOT EXISTS gmoney_partner_db
    WITH
    OWNER = gmoney
    ENCODING = 'UTF8'
    LC_COLLATE = 'French_France.1252'
    LC_CTYPE = 'French_France.1252'
    CONNECTION LIMIT = -1;

CREATE DATABASE IF NOT EXISTS gmoney_rule_db
    WITH
    OWNER = gmoney
    ENCODING = 'UTF8'
    LC_COLLATE = 'French_France.1252'
    LC_CTYPE = 'French_France.1252'
    CONNECTION LIMIT = -1;

CREATE DATABASE IF NOT EXISTS gmoney_transaction_db
    WITH
    OWNER = gmoney
    ENCODING = 'UTF8'
    LC_COLLATE = 'French_France.1252'
    LC_CTYPE = 'French_France.1252'
    CONNECTION LIMIT = -1;

CREATE DATABASE IF NOT EXISTS gmoney_uaa_db
    WITH
    OWNER = gmoney
    ENCODING = 'UTF8'
    LC_COLLATE = 'French_France.1252'
    LC_CTYPE = 'French_France.1252'
    CONNECTION LIMIT = -1;