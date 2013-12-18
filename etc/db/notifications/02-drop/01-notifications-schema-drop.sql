
    
-- ###########################################
-- # Drop
-- ###########################################
-- Drop index


-- Drop many to many relations
    
DROP TABLE TB_NOTIF_OPES CASCADE CONSTRAINTS PURGE;

-- Drop normal entities
    
DROP TABLE TB_ROLES CASCADE CONSTRAINTS PURGE;

DROP TABLE TB_RECEIVERS CASCADE CONSTRAINTS PURGE;

DROP TABLE TB_APPS CASCADE CONSTRAINTS PURGE;

DROP TABLE TB_NOTIFICATIONS CASCADE CONSTRAINTS PURGE;

DROP TABLE TB_LOCALISED_STRINGS CASCADE CONSTRAINTS PURGE;

DROP TABLE TB_EXTERNAL_ITEMS CASCADE CONSTRAINTS PURGE;

DROP TABLE TB_INTERNATIONAL_STRINGS CASCADE CONSTRAINTS PURGE;


-- Drop pk sequence
    
 	
drop sequence SEQ_I18NSTRS;
 	
drop sequence SEQ_EXTERNAL_ITEMS;
 	
drop sequence SEQ_L10NSTRS;
 	
drop sequence SEQ_NOTIFICATIONS;
 	
drop sequence SEQ_APPS;
 	
drop sequence SEQ_RECEIVERS;
 	
drop sequence SEQ_ROLES;
 	


	