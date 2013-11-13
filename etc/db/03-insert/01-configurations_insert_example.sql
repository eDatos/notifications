-- ###########################################
-- # Insert
-- ###########################################

-- CHANNELS: MAIL 
insert into TB_CONFIGURATIONS (CONF_KEY,CONF_VALUE) values ('metamac.notifications.channel.mail.host','smtp.gmail.com');
insert into TB_CONFIGURATIONS (CONF_KEY,CONF_VALUE) values ('metamac.notifications.channel.mail.port','465');
insert into TB_CONFIGURATIONS (CONF_KEY,CONF_VALUE) values ('metamac.notifications.channel.mail.username','xxxx@xxxx.com');
insert into TB_CONFIGURATIONS (CONF_KEY,CONF_VALUE) values ('metamac.notifications.channel.mail.password','xxxx');
insert into TB_CONFIGURATIONS (CONF_KEY,CONF_VALUE) values ('metamac.notifications.channel.mail.protocol','smtps');

-- DATASOURCE: ORACLE
insert into TB_CONFIGURATIONS (CONF_KEY,CONF_VALUE) values ('metamac.notifications.db.url','jdbc:oracle:thin:@FILL_ME_WITH_HOST:FILL_ME_WITH_PORT:XE');
insert into TB_CONFIGURATIONS (CONF_KEY,CONF_VALUE) values ('metamac.notifications.db.username','FILL_ME_WITH_USERNAME');
insert into TB_CONFIGURATIONS (CONF_KEY,CONF_VALUE) values ('metamac.notifications.db.password','FILL_ME_WITH_PASSWORD');
insert into TB_CONFIGURATIONS (CONF_KEY,CONF_VALUE) values ('metamac.notifications.db.driver_name','oracle.jdbc.OracleDriver');

insert into TB_CONFIGURATIONS (CONF_KEY,CONF_VALUE) values ('metamac.notifications.user_guide.file_name','Gestor_notificaciones-Manual_usuario.pdf');

