--CREATE DATABASE oauth2;


--USE oauth2;

DROP TABLE IF EXISTS oauth_access_token;
DROP TABLE IF EXISTS oauth_refresh_token;
DROP TABLE IF EXISTS oauth_client_details;
DROP TABLE IF EXISTS role_user;
DROP TABLE IF EXISTS permission_role;
DROP TABLE IF EXISTS permission;
DROP TABLE IF EXISTS role;
DROP TABLE IF EXISTS userdb;
DROP TABLE IF EXISTS litige;
commit;

CREATE TABLE oauth_access_token (
       token_id VARCHAR(256) DEFAULT NULL,
       token BYTEA,
       authentication_id VARCHAR(256) DEFAULT NULL,
       user_name VARCHAR(256) DEFAULT NULL,
       client_id VARCHAR(256) DEFAULT NULL,
       authentication BYTEA,
       refresh_token VARCHAR(256) DEFAULT NULL
     );

CREATE TABLE oauth_refresh_token (
       token_id VARCHAR(256) DEFAULT NULL,
       token BYTEA,
       authentication BYTEA
     );

CREATE TABLE oauth_client_details (
  client_id varchar(255) NOT NULL,
  resource_ids varchar(255) DEFAULT NULL,
  client_secret varchar(255) DEFAULT NULL,
  scope varchar(255) DEFAULT NULL,
  authorized_grant_types varchar(255) DEFAULT NULL,
  web_server_redirect_uri varchar(255) DEFAULT NULL,
  authorities varchar(255) DEFAULT NULL,
  access_token_validity int DEFAULT NULL,
  refresh_token_validity int DEFAULT NULL,
  additional_information varchar(4096) DEFAULT NULL,
  autoapprove varchar(255) DEFAULT NULL,
  PRIMARY KEY (client_id)
);

INSERT INTO oauth_client_details VALUES ('adminapp','mw/adminapp,ms/admin,ms/user','{bcrypt}$2a$10$EOs8VROb14e7ZnydvXECA.4LoIhPOoFHKvVF/iBZ/ker17Eocz4Vi','role_admin,role_superadmin','authorization_code,password,refresh_token,implicit',NULL,NULL,900,3600,'{}',NULL);

CREATE TABLE permission (
  id NUMERIC(19,0) NOT NULL,
  name varchar(60) NOT NULL,
  created_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  version numeric(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (id),
  UNIQUE (name)
);

INSERT INTO permission (id,name,created_on,updated_on,version) VALUES (1,'can_delete_user','1970-01-01 00:00:00','1970-01-01 00:00:00',0);
INSERT INTO permission (id,name,created_on,updated_on,version) VALUES (2,'can_create_user','1970-01-01 00:00:00','1970-01-01 00:00:00',0);
INSERT INTO permission (id,name,created_on,updated_on,version) VALUES (3,'can_update_user','1970-01-01 00:00:00','1970-01-01 00:00:00',0);
INSERT INTO permission (id,name,created_on,updated_on,version) VALUES (4,'can_read_user','1970-01-01 00:00:00','1970-01-01 00:00:00',0);

CREATE TABLE role (
  id NUMERIC(19,0) NOT NULL,
  name varchar(60) NOT NULL,
  created_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  version numeric(20)  NOT NULL DEFAULT '0',
  PRIMARY KEY (id),
  UNIQUE (name)
) ;

INSERT INTO role (id,name,created_on,updated_on,version) VALUES (1,'role_admin','1970-01-01 00:00:00','1970-01-01 00:00:00',0);
INSERT INTO role (id,name,created_on,updated_on,version) VALUES (2,'role_user','1970-01-01 00:00:00','1970-01-01 00:00:00',0);

CREATE TABLE permission_role (
  permission_id NUMERIC(19,0) NOT NULL,
  role_id NUMERIC(19,0) NOT NULL,
  created_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  version numeric(20)  NOT NULL DEFAULT '0',
  PRIMARY KEY (permission_id,role_id),
  CONSTRAINT permission_role_fk1 FOREIGN KEY (permission_id) REFERENCES permission (id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT permission_role_fk2 FOREIGN KEY (role_id) REFERENCES role (id) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO permission_role (permission_id,role_id,created_on,updated_on,version) VALUES (1,1,'1970-01-01 00:00:00','1970-01-01 00:00:00',0);
INSERT INTO permission_role (permission_id,role_id,created_on,updated_on,version) VALUES (2,1,'1970-01-01 00:00:00','1970-01-01 00:00:00',0);
INSERT INTO permission_role (permission_id,role_id,created_on,updated_on,version) VALUES (3,1,'1970-01-01 00:00:00','1970-01-01 00:00:00',0);
INSERT INTO permission_role (permission_id,role_id,created_on,updated_on,version) VALUES (4,1,'1970-01-01 00:00:00','1970-01-01 00:00:00',0);
INSERT INTO permission_role (permission_id,role_id,created_on,updated_on,version) VALUES (4,2,'1970-01-01 00:00:00','1970-01-01 00:00:00',0);

CREATE TABLE userdb (
  id NUMERIC(19,0) NOT NULL,
  username varchar(24) NOT NULL,
  password varchar(200) NOT NULL,
  email varchar(255) NOT NULL,
  enabled boolean,
  account_expired boolean,
  credentials_expired boolean,
  account_locked boolean,
  created_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  version numeric(20)  NOT NULL DEFAULT '0',
  PRIMARY KEY (id),
  UNIQUE (username),
  UNIQUE (email)
);

INSERT INTO userdb (id,username,password,email,enabled,account_expired,credentials_expired,account_locked,created_on,updated_on,version) VALUES (1,'admin','{bcrypt}$2a$10$EOs8VROb14e7ZnydvXECA.4LoIhPOoFHKvVF/iBZ/ker17Eocz4Vi','admin@example.com',true,false,false,false,'1970-01-01 00:00:00','1970-01-01 00:00:00',0);
INSERT INTO userdb (id,username,password,email,enabled,account_expired,credentials_expired,account_locked,created_on,updated_on,version) VALUES (2,'user','{bcrypt}$2a$10$EOs8VROb14e7ZnydvXECA.4LoIhPOoFHKvVF/iBZ/ker17Eocz4Vi','user@example.com',true,false,false,false,'1970-01-01 00:00:00','1970-01-01 00:00:00',0);

CREATE TABLE role_user (
  role_id NUMERIC(19,0)  NOT NULL,
  user_id NUMERIC(19,0)  NOT NULL,
  created_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  version numeric(20)  NOT NULL DEFAULT '0',
  PRIMARY KEY (role_id,user_id),
  CONSTRAINT role_user_fk1 FOREIGN KEY (role_id) REFERENCES role (id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT role_user_fk2 FOREIGN KEY (user_id) REFERENCES userdb (id) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO role_user VALUES (1,1,'1970-01-01 00:00:00','1970-01-01 00:00:00',0);
INSERT INTO role_user VALUES (2,2,'1970-01-01 00:00:00','1970-01-01 00:00:00',0);

CREATE TABLE litige(
	id NUMERIC(19,0) NOT NULL,
	objet varchar(200),
	localite varchar(200),
	creation TIMESTAMP,
	requerant varchar(200),
	agent varchar(200),
	PRIMARY KEY (id)
);