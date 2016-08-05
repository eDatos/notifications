package org.siemac.metamac.notices.core.bean;

import java.util.Properties;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.siemac.metamac.core.common.exception.MetamacExceptionBuilder;
import org.siemac.metamac.notices.core.conf.NoticesConfigurationService;
import org.siemac.metamac.notices.core.constants.NoticesConfigurationConstants;
import org.siemac.metamac.notices.core.error.ServiceExceptionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

@Component
public class CustomJavaMailSenderImpl extends JavaMailSenderImpl {

    private final static Logger         logger         = LoggerFactory.getLogger(CustomJavaMailSenderImpl.class);

    private final String                PROTOCOL_SMTP  = "SMTP";
    private final String                PROTOCOL_SMTPS = "SMTPS";

    @Autowired
    private NoticesConfigurationService configurationService;

    @PostConstruct
    public void initSender() throws Exception {
        // Initialize properties

        // Conf
        setHost(configurationService.retrieveChannelMailHost());
        setPort(Integer.parseInt(configurationService.retrieveChannelMailPort()));
        setUsername(configurationService.retrieveChannelMailUsername());
        setPassword(configurationService.retrieveChannelMailPassword());
        setDefaultEncoding("UTF-8");

        // Protocol
        Properties javaMailProperties = new Properties();
        boolean error = false;
        String retrieveChannelMailProtocol = configurationService.retrieveChannelMailProtocol();
        if (StringUtils.isNotEmpty(retrieveChannelMailProtocol)) {
            if (PROTOCOL_SMTP.equals(retrieveChannelMailProtocol.toUpperCase())) {
                javaMailProperties.put("mail.smtp.auth", true);
            } else if (PROTOCOL_SMTPS.equals(retrieveChannelMailProtocol.toUpperCase())) {
                javaMailProperties.put("mail.smtps.auth", true);
                javaMailProperties.put("mail.smtp.starttls.enable", true);
                javaMailProperties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                javaMailProperties.put("mail.smtp.socketFactory.fallback", false);
            } else {
                error = true;
            }
        } else {
            error = true;
        }

        if (error) {
            logger.error(ServiceExceptionType.CONFIGURATION_PROPERTY_INVALID.getCode(), "Servicio de correo no configurado correctamente, especifique protocolo SMTP o SMTPS");
            throw MetamacExceptionBuilder.builder().withExceptionItems(ServiceExceptionType.CONFIGURATION_PROPERTY_INVALID)
                    .withMessageParameters(NoticesConfigurationConstants.METAMAC_NOTIFICATION_CHANNEL_MAIL_PROTOCOL).build();
        }
        setJavaMailProperties(javaMailProperties);
    }

}
