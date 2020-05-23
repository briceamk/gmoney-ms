CREATE SCHEMA  IF NOT EXISTS public AUTHORIZATION java;

CREATE TABLE IF NOT EXISTS oauth_client_details
(
    client_id character varying(255)  NOT NULL,
    client_secret character varying(255)  NOT NULL,
    web_server_redirect_uri character varying(2048)  DEFAULT NULL::character varying,
    scope character varying(255)  DEFAULT NULL::character varying,
    access_token_validity integer,
    refresh_token_validity integer,
    resource_ids character varying(1024)  DEFAULT NULL::character varying,
    authorized_grant_types character varying(1024)  DEFAULT NULL::character varying,
    authorities character varying(1024)  DEFAULT NULL::character varying,
    additional_information character varying(4096)  DEFAULT NULL::character varying,
    autoapprove character varying(255)  DEFAULT NULL::character varying,
    CONSTRAINT oauth_client_details_pkey PRIMARY KEY (client_id)
);

CREATE TABLE IF NOT EXISTS permission
(
    id character varying(255)  NOT NULL,
    created_date timestamp without time zone NOT NULL,
    last_modified_date timestamp without time zone,
    created_uid character varying(255)  DEFAULT NULL::character varying,
    last_modified_uid character varying(255)  DEFAULT NULL::character varying,
    name character varying(255)  NOT NULL,
    CONSTRAINT permission_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS role
(
    id character varying(64)  NOT NULL,
    created_date timestamp without time zone NOT NULL,
    created_uid character varying(64) ,
    last_modified_date timestamp without time zone,
    last_modified_uid character varying(64) ,
    name character varying(255)  NOT NULL,
    CONSTRAINT role_pkey PRIMARY KEY (id),
    CONSTRAINT uk_8sewwnpamngi6b1dwaa88askk UNIQUE (name)

);


CREATE TABLE IF NOT EXISTS role_permission
(
    role_id character varying(64)  NOT NULL,
    permission_id character varying(64)  NOT NULL,
    CONSTRAINT role_permission_pkey PRIMARY KEY (role_id, permission_id),
    CONSTRAINT fka6jx8n8xkesmjmv6jqug6bg68 FOREIGN KEY (role_id)
        REFERENCES role (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fkf8yllw1ecvwqy3ehyxawqa1qp FOREIGN KEY (permission_id)
        REFERENCES permission (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE IF NOT EXISTS users
(
    id character varying(64)  NOT NULL,
    created_date timestamp without time zone NOT NULL,
    created_uid character varying(64) ,
    last_modified_date timestamp without time zone,
    last_modified_uid character varying(64) ,
    account_non_expired boolean,
    account_non_locked boolean,
    credentials_non_expired boolean,
    email character varying(128)  NOT NULL,
    enabled boolean,
    first_name character varying(64),
    last_name character varying(64)  NOT NULL,
    mobile character varying(12)  NOT NULL,
    password character varying(255)  NOT NULL,
    username character varying(32)  NOT NULL,
    CONSTRAINT users_pkey PRIMARY KEY (id),
    CONSTRAINT uk_63cf888pmqtt5tipcne79xsbm UNIQUE (mobile)
,
    CONSTRAINT uk_6dotkott2kjsp8vw4d0m25fb7 UNIQUE (email)
,
    CONSTRAINT uk_r43af9ap4edm43mmtq01oddj6 UNIQUE (username)

);

CREATE TABLE IF NOT EXISTS users_role
(
    user_id character varying(64)  NOT NULL,
    role_id character varying(64)  NOT NULL,
    CONSTRAINT users_role_pkey PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk3qjq7qsiigxa82jgk0i0wuq3g FOREIGN KEY (role_id)
        REFERENCES role (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fkqpe36jsen4rslwfx5i6dj2fy8 FOREIGN KEY (user_id)
        REFERENCES users (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);