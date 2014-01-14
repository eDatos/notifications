

-- ###########################################
-- # Create
-- ###########################################
-- Create pk sequence
    
 	
CREATE sequence SEQ_I18NSTRS;
 	
CREATE sequence SEQ_EXTERNAL_ITEMS;
 	
CREATE sequence SEQ_L10NSTRS;
 	
CREATE sequence SEQ_NOTIFICATIONS;
 	
CREATE sequence SEQ_APPS;
 	
CREATE sequence SEQ_MESSAGES;
 	
CREATE sequence SEQ_RECEIVERS;
 	
CREATE sequence SEQ_ROLES;
 	
CREATE sequence SEQ_STATISTICAL_OPERATIONS;
 	


-- Create normal entities
    
CREATE TABLE TB_INTERNATIONAL_STRINGS (
  ID NUMBER(19) NOT NULL,
  VERSION NUMBER(19) NOT NULL
);


CREATE TABLE TB_EXTERNAL_ITEMS (
  ID NUMBER(19) NOT NULL,
  CODE VARCHAR2(255 CHAR) NOT NULL,
  CODE_NESTED VARCHAR2(255 CHAR),
  URI VARCHAR2(4000 CHAR) NOT NULL,
  URN VARCHAR2(4000 CHAR),
  URN_PROVIDER VARCHAR2(4000 CHAR),
  MANAGEMENT_APP_URL VARCHAR2(4000 CHAR),
  VERSION NUMBER(19) NOT NULL,
  TITLE_FK NUMBER(19),
  TYPE VARCHAR2(255 CHAR) NOT NULL
);


CREATE TABLE TB_LOCALISED_STRINGS (
  ID NUMBER(19) NOT NULL,
  LABEL VARCHAR2(4000 CHAR) NOT NULL,
  LOCALE VARCHAR2(255 CHAR) NOT NULL,
  IS_UNMODIFIABLE NUMBER(1,0),
  VERSION NUMBER(19) NOT NULL,
  INTERNATIONAL_STRING_FK NUMBER(19) NOT NULL
);


CREATE TABLE TB_NOTIFICATIONS (
  ID NUMBER(19) NOT NULL,
  URN VARCHAR2(255 CHAR),
  SENDING_APPLICATION VARCHAR2(255 CHAR) NOT NULL,
  SENDING_USER VARCHAR2(255 CHAR),
  EXPIRATION_DATE_TZ VARCHAR2(50 CHAR),
  EXPIRATION_DATE TIMESTAMP ,
  MARK NUMBER(1,0) NOT NULL,
  SUBJECT VARCHAR2(4000 CHAR) NOT NULL,
  CREATED_DATE_TZ VARCHAR2(50 CHAR),
  CREATED_DATE TIMESTAMP,
  CREATED_BY VARCHAR2(50 CHAR),
  LAST_UPDATED_TZ VARCHAR2(50 CHAR),
  LAST_UPDATED TIMESTAMP,
  LAST_UPDATED_BY VARCHAR2(50 CHAR),
  VERSION NUMBER(19) NOT NULL,
  NOTIFICATION_TYPE VARCHAR2(255 CHAR) NOT NULL
);


CREATE TABLE TB_APPS (
  ID NUMBER(19) NOT NULL,
  NAME VARCHAR2(255 CHAR) NOT NULL,
  VERSION NUMBER(19) NOT NULL,
  APP_FK NUMBER(19) NOT NULL
);


CREATE TABLE TB_MESSAGES (
  ID NUMBER(19) NOT NULL,
  TEXT CLOB NOT NULL,
  VERSION NUMBER(19) NOT NULL,
  MESSAGE_FK NUMBER(19) NOT NULL
);


CREATE TABLE TB_RECEIVERS (
  ID NUMBER(19) NOT NULL,
  USERNAME VARCHAR2(255 CHAR) NOT NULL,
  ACKNOWLEDGE NUMBER(1,0) NOT NULL,
  VERSION NUMBER(19) NOT NULL,
  RECEIVER_FK NUMBER(19) NOT NULL
);


CREATE TABLE TB_ROLES (
  ID NUMBER(19) NOT NULL,
  NAME VARCHAR2(255 CHAR) NOT NULL,
  VERSION NUMBER(19) NOT NULL,
  ROLE_FK NUMBER(19) NOT NULL
);


CREATE TABLE TB_STATISTICAL_OPERATIONS (
  ID NUMBER(19) NOT NULL,
  NAME VARCHAR2(255 CHAR) NOT NULL,
  VERSION NUMBER(19) NOT NULL,
  STATISTICAL_OPERATION_FK NUMBER(19) NOT NULL
);



-- Create many to many relations
    
CREATE TABLE TB_MESSAGE_RESOURCES (
  RESOURCE_FK NUMBER(19) NOT NULL,
  MESSAGE_FK NUMBER(19) NOT NULL
);



-- Primary keys
    
ALTER TABLE TB_INTERNATIONAL_STRINGS ADD CONSTRAINT PK_TB_INTERNATIONAL_STRINGS
	PRIMARY KEY (ID)
;

ALTER TABLE TB_EXTERNAL_ITEMS ADD CONSTRAINT PK_TB_EXTERNAL_ITEMS
	PRIMARY KEY (ID)
;

ALTER TABLE TB_LOCALISED_STRINGS ADD CONSTRAINT PK_TB_LOCALISED_STRINGS
	PRIMARY KEY (ID)
;

ALTER TABLE TB_NOTIFICATIONS ADD CONSTRAINT PK_TB_NOTIFICATIONS
	PRIMARY KEY (ID)
;

ALTER TABLE TB_APPS ADD CONSTRAINT PK_TB_APPS
	PRIMARY KEY (ID)
;

ALTER TABLE TB_MESSAGES ADD CONSTRAINT PK_TB_MESSAGES
	PRIMARY KEY (ID)
;

ALTER TABLE TB_RECEIVERS ADD CONSTRAINT PK_TB_RECEIVERS
	PRIMARY KEY (ID)
;

ALTER TABLE TB_ROLES ADD CONSTRAINT PK_TB_ROLES
	PRIMARY KEY (ID)
;

ALTER TABLE TB_STATISTICAL_OPERATIONS ADD CONSTRAINT PK_TB_STATISTICAL_OPERATIONS
	PRIMARY KEY (ID)
;

    
ALTER TABLE TB_MESSAGE_RESOURCES ADD CONSTRAINT PK_TB_MESSAGE_RESOURCES
	PRIMARY KEY (RESOURCE_FK, MESSAGE_FK)
;


-- Unique constraints
    






ALTER TABLE TB_MESSAGES
	ADD CONSTRAINT UQ_TB_MESSAGES UNIQUE (ID)
;





ALTER TABLE TB_STATISTICAL_OPERATIONS
	ADD CONSTRAINT UQ_TB_STATISTICAL_OPERATIONS UNIQUE (ID)
;



-- Foreign key constraints
    

  
  
ALTER TABLE TB_EXTERNAL_ITEMS ADD CONSTRAINT FK_TB_EXTERNAL_ITEMS_TITLE_FK
	FOREIGN KEY (TITLE_FK) REFERENCES TB_INTERNATIONAL_STRINGS (ID) DEFERRABLE initially IMMEDIATE
;

  
ALTER TABLE TB_LOCALISED_STRINGS ADD CONSTRAINT FK_TB_LOCALISED_STRINGS_INTE13
	FOREIGN KEY (INTERNATIONAL_STRING_FK) REFERENCES TB_INTERNATIONAL_STRINGS (ID) DEFERRABLE initially IMMEDIATE
;

  
  
  
ALTER TABLE TB_APPS ADD CONSTRAINT FK_TB_APPS_APP_FK
	FOREIGN KEY (APP_FK) REFERENCES TB_NOTIFICATIONS (ID) DEFERRABLE initially IMMEDIATE
;

  
ALTER TABLE TB_MESSAGES ADD CONSTRAINT FK_TB_MESSAGES_MESSAGE_FK
	FOREIGN KEY (MESSAGE_FK) REFERENCES TB_NOTIFICATIONS (ID) DEFERRABLE initially IMMEDIATE
;

  
ALTER TABLE TB_RECEIVERS ADD CONSTRAINT FK_TB_RECEIVERS_RECEIVER_FK
	FOREIGN KEY (RECEIVER_FK) REFERENCES TB_NOTIFICATIONS (ID) DEFERRABLE initially IMMEDIATE
;

  
ALTER TABLE TB_ROLES ADD CONSTRAINT FK_TB_ROLES_ROLE_FK
	FOREIGN KEY (ROLE_FK) REFERENCES TB_NOTIFICATIONS (ID) DEFERRABLE initially IMMEDIATE
;

  
ALTER TABLE TB_STATISTICAL_OPERATIONS ADD CONSTRAINT FK_TB_STATISTICAL_OPERATIONS39
	FOREIGN KEY (STATISTICAL_OPERATION_FK) REFERENCES TB_NOTIFICATIONS (ID) DEFERRABLE initially IMMEDIATE
;

  

ALTER TABLE TB_MESSAGE_RESOURCES ADD CONSTRAINT FK_TB_MESSAGE_RESOURCES_RESO59
	FOREIGN KEY (RESOURCE_FK) REFERENCES TB_EXTERNAL_ITEMS (ID) DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_MESSAGE_RESOURCES ADD CONSTRAINT FK_TB_MESSAGE_RESOURCES_MESS04
	FOREIGN KEY (MESSAGE_FK) REFERENCES TB_MESSAGES (ID) DEFERRABLE initially IMMEDIATE
;

  

    

-- Index


	