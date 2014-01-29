-- ------------------------------------------------------------------
-- METAMAC-2103 - [CORE] [API] Refactor notification por notices
-- ------------------------------------------------------------------

-- Cambiar nombre de la tabla
ALTER TABLE TB_NOTIFICATIONS RENAME TO TB_NOTICES;

-- Cambia nombre de la columna NOTIFICATION-TYPE
ALTER TABLE TB_NOTICES RENAME COLUMN NOTIFICATION_TYPE TO NOTICE_TYPE;

-- Cambiar el identificador de la secuencia
RENAME SEQ_NOTIFICATIONS TO SEQ_NOTICES;

commit;