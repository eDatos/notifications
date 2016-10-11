-- ----------------------------------------------------------------------------------------------------
-- METAMAC-2060 - Cambiar tipo de statisticalOperations para que no sea ExternalItem sino String
-- ----------------------------------------------------------------------------------------------------
-- ------------------------------
-- Eliminar campos obsoletos
-- ------------------------------

DROP TABLE TB_NOTIF_OPES CASCADE CONSTRAINTS PURGE;


-- ------------------------------
-- Añadir nuevos campos
-- ------------------------------

CREATE sequence SEQ_STATISTICAL_OPERATIONS;

CREATE TABLE TB_STATISTICAL_OPERATIONS (
  ID NUMBER(19) NOT NULL,
  NAME VARCHAR2(255 CHAR) NOT NULL,
  VERSION NUMBER(19) NOT NULL,
  STATISTICAL_OPERATION_FK NUMBER(19) NOT NULL
);

ALTER TABLE TB_STATISTICAL_OPERATIONS ADD CONSTRAINT PK_TB_STATISTICAL_OPERATIONS
	PRIMARY KEY (ID)
;

ALTER TABLE TB_STATISTICAL_OPERATIONS ADD CONSTRAINT FK_TB_STATISTICAL_OPERATIONS39
	FOREIGN KEY (STATISTICAL_OPERATION_FK) REFERENCES TB_NOTIFICATIONS (ID) DEFERRABLE initially IMMEDIATE
;