package org.siemac.metamac.notices.core.channel.mail.serviceimpl;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.notices.core.notice.domain.Notice;

public interface MailChannelService {

    public void sendMail(ServiceContext serviceContext, Notice notice, String[] mailsTo, String replyTo) throws MetamacException;

}
