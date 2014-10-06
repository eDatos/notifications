-- -----------------------------------------------------------------------------------------
-- Actualizar nombre de manual de usuario
-- -----------------------------------------------------------------------------------------

UPDATE TB_DATA_CONFIGURATIONS
SET CONF_VALUE = 'Gestor_avisos-Manual_usuario.pdf'
WHERE CONF_KEY = 'metamac.notices.user_guide.file_name';
