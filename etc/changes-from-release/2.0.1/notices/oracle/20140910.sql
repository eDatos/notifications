-- -----------------------------------------------------------------------------------------
-- METAMAC-2048 - Eliminar el campo mark de las notificaciones
-- -----------------------------------------------------------------------------------------
ALTER TABLE TB_NOTICES DROP COLUMN MARK;
commit;