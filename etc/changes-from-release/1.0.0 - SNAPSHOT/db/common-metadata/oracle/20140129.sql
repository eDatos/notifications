-- ------------------------------------------------------------------
-- METAMAC-2103 - [CORE] [API] Refactor notification por notices
-- ------------------------------------------------------------------

-- Actualizar propiedades de base de datos
UPDATE TB_DATA_CONFIGURATIONS t SET CONF_KEY = REPLACE(t.CONF_KEY, 'metamac.notifications', 'metamac.notices');

commit;