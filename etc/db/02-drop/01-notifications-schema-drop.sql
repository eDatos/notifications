
    
-- ###########################################
-- # Drop
-- ###########################################
-- Drop index


-- Drop many to many relations
    
-- Drop normal entities
    
DROP TABLE TB_RECEIVERS CASCADE CONSTRAINTS PURGE;

DROP TABLE TB_NOTIFICATIONS CASCADE CONSTRAINTS PURGE;

DROP TABLE TB_LOCALISED_STRINGS CASCADE CONSTRAINTS PURGE;

DROP TABLE TB_EXTERNAL_ITEMS CASCADE CONSTRAINTS PURGE;

DROP TABLE TB_INTERNATIONAL_STRINGS CASCADE CONSTRAINTS PURGE;


-- Drop pk sequence
    
 	
drop sequence SEQ_I18NSTRS;
 	
drop sequence SEQ_EXTERNAL_ITEMS;
 	
drop sequence SEQ_L10NSTRS;
 	
drop sequence SEQ_NOTIFICATIONS;
 	
drop sequence SEQ_RECEIVERS;
 	


	