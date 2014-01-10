-- -------------------------------------------------------------------------------------------------------------------------------------------
-- METAMAC-2049 - Las notificaciones deben poder tener una lista de mensajes y además los mensajes pueden tener asociados listas de recursos
-- -------------------------------------------------------------------------------------------------------------------------------------------

-- Eliminar columna antigua del mensaje
ALTER TABLE TB_NOTIFICATIONS DROP COLUMN MESSAGE;

-- Añadir lo necesario para los nuevos mensajes

CREATE sequence SEQ_MESSAGES;

CREATE TABLE TB_MESSAGES (
  ID NUMBER(19) NOT NULL,
  TEXT CLOB NOT NULL,
  VERSION NUMBER(19) NOT NULL,
  MESSAGE_FK NUMBER(19) NOT NULL
);

CREATE TABLE TB_MESSAGE_RESOURCES (
  RESOURCE_FK NUMBER(19) NOT NULL,
  MESSAGE_FK NUMBER(19) NOT NULL
);

ALTER TABLE TB_MESSAGES ADD CONSTRAINT PK_TB_MESSAGES
	PRIMARY KEY (ID)
;

ALTER TABLE TB_MESSAGE_RESOURCES ADD CONSTRAINT PK_TB_MESSAGE_RESOURCES
	PRIMARY KEY (RESOURCE_FK, MESSAGE_FK)
;

ALTER TABLE TB_MESSAGES ADD CONSTRAINT FK_TB_MESSAGES_MESSAGE_FK
	FOREIGN KEY (MESSAGE_FK) REFERENCES TB_NOTIFICATIONS (ID) DEFERRABLE initially IMMEDIATE
;

ALTER TABLE TB_MESSAGE_RESOURCES ADD CONSTRAINT FK_TB_MESSAGE_RESOURCES_RESO59
	FOREIGN KEY (RESOURCE_FK) REFERENCES TB_EXTERNAL_ITEMS (ID) DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_MESSAGE_RESOURCES ADD CONSTRAINT FK_TB_MESSAGE_RESOURCES_MESS04
	FOREIGN KEY (MESSAGE_FK) REFERENCES TB_MESSAGES (ID) DEFERRABLE initially IMMEDIATE
;