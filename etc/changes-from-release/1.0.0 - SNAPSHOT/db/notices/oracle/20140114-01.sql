-- --------------------------------------------------------------------------------------------
-- METAMAC-2058 - Añadir asunto a las notificaciones
-- --------------------------------------------------------------------------------------------
ALTER TABLE TB_NOTIFICATIONS ADD SUBJECT VARCHAR2(4000 CHAR) NOT NULL;