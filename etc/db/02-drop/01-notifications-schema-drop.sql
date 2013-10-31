
    
-- ###########################################
-- # Drop
-- ###########################################
-- Drop index


-- Drop many to many relations
    
-- Drop normal entities
    
DROP TABLE TB_RECEIVERS CASCADE CONSTRAINTS PURGE;

DROP TABLE TB_NOTIFICATIONS CASCADE CONSTRAINTS PURGE;


-- Drop pk sequence
    
 	
drop sequence SEQ_NOTIFICATIONS;
 	
drop sequence SEQ_RECEIVERS;
 	


	