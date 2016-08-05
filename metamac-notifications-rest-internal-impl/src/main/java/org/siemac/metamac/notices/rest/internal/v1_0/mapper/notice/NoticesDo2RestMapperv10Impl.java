package org.siemac.metamac.notices.rest.internal.v1_0.mapper.notice;

import java.math.BigInteger;
import java.util.List;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.util.CoreCommonUtil;
import org.siemac.metamac.core.common.util.shared.UrnUtils;
import org.siemac.metamac.notices.core.notice.domain.App;
import org.siemac.metamac.notices.core.notice.domain.Message;
import org.siemac.metamac.notices.core.notice.domain.Receiver;
import org.siemac.metamac.notices.core.notice.domain.Role;
import org.siemac.metamac.notices.core.notice.domain.StatisticalOperation;
import org.siemac.metamac.notices.rest.internal.constants.NoticesRestConstants;
import org.siemac.metamac.notices.rest.internal.v1_0.mapper.base.CommonDo2RestMapperV10;
import org.siemac.metamac.rest.common.v1_0.domain.ResourceLink;
import org.siemac.metamac.rest.notices.v1_0.domain.Application;
import org.siemac.metamac.rest.notices.v1_0.domain.Applications;
import org.siemac.metamac.rest.notices.v1_0.domain.Messages;
import org.siemac.metamac.rest.notices.v1_0.domain.Notice;
import org.siemac.metamac.rest.notices.v1_0.domain.NoticeType;
import org.siemac.metamac.rest.notices.v1_0.domain.Receivers;
import org.siemac.metamac.rest.notices.v1_0.domain.Roles;
import org.siemac.metamac.rest.notices.v1_0.domain.StatisticalOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NoticesDo2RestMapperv10Impl implements NoticesDo2RestMapperV10 {

    @Autowired
    private CommonDo2RestMapperV10 commonDo2RestMapper;

    @Override
    public Notice noticeEntity2Rest(ServiceContext ctx, org.siemac.metamac.notices.core.notice.domain.Notice source) throws MetamacException {
        if (source == null) {
            return null;
        }

        Notice target = new Notice();

        target.setKind(NoticesRestConstants.KIND_NOTICE);
        target.setId(UrnUtils.removePrefix(source.getUrn()));
        target.setUrn(source.getUrn());
        target.setSelfLink(toNoticeSelfLink(UrnUtils.removePrefix(source.getUrn())));
        target.setParentLink(toNoticeParentLink(source));

        target.setNoticeType(NoticeType.fromValue(source.getNoticeType().getName()));
        target.setSendingApplication(source.getSendingApplication());
        target.setSendingUser(source.getSendingUser());
        target.setSendingDate(CoreCommonUtil.transformDateTimeToDate(source.getCreatedDate()));
        target.setExpirationDate(CoreCommonUtil.transformDateTimeToDate(source.getExpirationDate()));
        target.setSubject(source.getSubject());

        target.setMessages(messagesToRest(source.getMessages()));
        target.setRoles(rolesToRest(source.getRoles()));
        target.setApplications(applicationsToRest(source.getApps()));
        target.setStatisticalOperations(statisticalOperationsToRest(source.getStatisticalOperations()));
        target.setReceivers(receiversToRest(source.getReceivers()));

        return target;
    }

    private ResourceLink toNoticeSelfLink(String code) {
        String link = commonDo2RestMapper.toResourceLink(NoticesRestConstants.LINK_SUBPATH_NOTICES, code);
        return commonDo2RestMapper.uriToResourceLink(NoticesRestConstants.KIND_NOTICE, link);
    }

    private ResourceLink toNoticeParentLink(org.siemac.metamac.notices.core.notice.domain.Notice source) {
        return toNoticeSelfLink(null);
    }

    private StatisticalOperations statisticalOperationsToRest(List<StatisticalOperation> source) {
        if (source.isEmpty()) {
            return null;
        }

        StatisticalOperations statisticalOperations = new StatisticalOperations();
        for (StatisticalOperation statisticalOperation : source) {
            org.siemac.metamac.rest.notices.v1_0.domain.StatisticalOperation targetStatisticalOperation = new org.siemac.metamac.rest.notices.v1_0.domain.StatisticalOperation();
            targetStatisticalOperation.setUrn(statisticalOperation.getName());
            statisticalOperations.getStatisticalOperations().add(targetStatisticalOperation);
        }
        if (!statisticalOperations.getStatisticalOperations().isEmpty()) {
            statisticalOperations.setTotal(new BigInteger(String.valueOf(statisticalOperations.getStatisticalOperations().size())));
        }
        return statisticalOperations;
    }

    private Roles rolesToRest(List<Role> source) {
        if (source.isEmpty()) {
            return null;
        }

        Roles roles = new Roles();
        for (Role role : source) {
            org.siemac.metamac.rest.notices.v1_0.domain.Role targetRole = new org.siemac.metamac.rest.notices.v1_0.domain.Role();
            targetRole.setName(role.getName());
            roles.getRoles().add(targetRole);
        }
        if (!roles.getRoles().isEmpty()) {
            roles.setTotal(new BigInteger(String.valueOf(roles.getRoles().size())));
        }

        return roles;
    }

    private Applications applicationsToRest(List<App> source) {
        if (source.isEmpty()) {
            return null;
        }
        Applications applications = new Applications();
        for (org.siemac.metamac.notices.core.notice.domain.App app : source) {
            Application targetApplication = new Application();
            targetApplication.setName(app.getName());
            applications.getApplications().add(targetApplication);
        }
        if (!applications.getApplications().isEmpty()) {
            applications.setTotal(new BigInteger(String.valueOf(applications.getApplications().size())));
        }
        return applications;
    }

    private Receivers receiversToRest(List<Receiver> source) {
        if (source.isEmpty()) {
            return null;
        }

        Receivers receivers = new Receivers();
        for (org.siemac.metamac.notices.core.notice.domain.Receiver sourceReceiver : source) {
            org.siemac.metamac.rest.notices.v1_0.domain.Receiver targetReceiver = new org.siemac.metamac.rest.notices.v1_0.domain.Receiver();
            targetReceiver.setUsername(sourceReceiver.getUsername());
            receivers.getReceivers().add(targetReceiver);
        }
        if (!receivers.getReceivers().isEmpty()) {
            receivers.setTotal(new BigInteger(String.valueOf(receivers.getReceivers().size())));
        }
        return receivers;
    }

    private Messages messagesToRest(List<Message> source) throws MetamacException {
        if (source.isEmpty()) {
            return null;
        }

        Messages messages = new Messages();
        for (Message sourceMessage : source) {
            org.siemac.metamac.rest.notices.v1_0.domain.Message targetMessage = new org.siemac.metamac.rest.notices.v1_0.domain.Message();
            targetMessage.setText(sourceMessage.getText());
            targetMessage.setResources(commonDo2RestMapper.externalItemEntityListToRest(sourceMessage.getResources()));
            messages.getMessages().add(targetMessage);

        }

        if (!messages.getMessages().isEmpty()) {
            messages.setTotal(new BigInteger(String.valueOf(messages.getMessages().size())));
        }
        return messages;
    }
}
