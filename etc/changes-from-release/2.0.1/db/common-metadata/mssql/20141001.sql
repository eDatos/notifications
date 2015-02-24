-- -------------------------------------------------------------------------------------------------------------------
-- METAMAC-1984 - Descarga de manual de usuario
-- -------------------------------------------------------------------------------------------------------------------

insert into TB_DATA_CONFIGURATIONS (ID,VERSION,SYSTEM_PROPERTY,CONF_KEY,CONF_VALUE) values(FILL_WITH_SCHEMA_NAME.GetSequenceNextValue('DATA_CONFIGURATIONS'),1,1,'metamac.data.docs.notices.path','${metamac.data.path}/notices/docs');
UPDATE TB_SEQUENCES SET SEQUENCE_NEXT_VALUE = SEQUENCE_NEXT_VALUE + 1 WHERE SEQUENCE_NAME = 'DATA_CONFIGURATIONS';
