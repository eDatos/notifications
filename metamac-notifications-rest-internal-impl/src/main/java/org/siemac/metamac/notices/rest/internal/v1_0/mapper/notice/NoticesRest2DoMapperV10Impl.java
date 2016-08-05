package org.siemac.metamac.notices.rest.internal.v1_0.mapper.notice;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.util.CoreCommonUtil;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.notices.core.common.domain.ExternalItem;
import org.siemac.metamac.notices.core.notice.enume.domain.NoticeType;
import org.siemac.metamac.notices.rest.internal.v1_0.mapper.base.BaseRest2DoMapperV10Impl;
import org.siemac.metamac.notices.rest.internal.v1_0.mapper.base.CommonRest2DoMapperV10;
import org.siemac.metamac.rest.notices.v1_0.domain.Application;
import org.siemac.metamac.rest.notices.v1_0.domain.Applications;
import org.siemac.metamac.rest.notices.v1_0.domain.Message;
import org.siemac.metamac.rest.notices.v1_0.domain.Messages;
import org.siemac.metamac.rest.notices.v1_0.domain.Notice;
import org.siemac.metamac.rest.notices.v1_0.domain.Receivers;
import org.siemac.metamac.rest.notices.v1_0.domain.ResourceInternal;
import org.siemac.metamac.rest.notices.v1_0.domain.Role;
import org.siemac.metamac.rest.notices.v1_0.domain.Roles;
import org.siemac.metamac.rest.notices.v1_0.domain.StatisticalOperation;
import org.siemac.metamac.rest.notices.v1_0.domain.StatisticalOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NoticesRest2DoMapperV10Impl extends BaseRest2DoMapperV10Impl implements NoticesRest2DoMapperV10 {

    @Autowired
    private CommonRest2DoMapperV10 commonRest2DoMapper;

    @Override
    public org.siemac.metamac.notices.core.notice.domain.Notice noticeRestToEntity(ServiceContext ctx, Notice source) throws MetamacException {
        if (source == null) {
            return null;
        }

        org.siemac.metamac.notices.core.notice.domain.Notice target = new org.siemac.metamac.notices.core.notice.domain.Notice(source.getSendingApplication(), source.getSubject(),
                NoticeType.valueOf(source.getNoticeType().name()));
        target.setSendingUser(source.getSendingUser());
        target.setExpirationDate(CoreCommonUtil.transformDateToDateTime(source.getExpirationDate()));

        target.getMessages().addAll(messagesRestToEntity(source.getMessages(), target));
        target.getReceivers().addAll(receiversRestToEntity(source.getReceivers(), target));
        target.getApps().addAll(applicationsRestToEntity(source.getApplications(), target));
        target.getStatisticalOperations().addAll(statisticalOperationsRestToEntity(source.getStatisticalOperations(), target));
        target.getRoles().addAll(rolesRestToEntity(source.getRoles(), target));

        return target;
    }

    private List<org.siemac.metamac.notices.core.notice.domain.Role> rolesRestToEntity(Roles source, org.siemac.metamac.notices.core.notice.domain.Notice notice) {
        List<org.siemac.metamac.notices.core.notice.domain.Role> target = new ArrayList<org.siemac.metamac.notices.core.notice.domain.Role>();
        if (source != null) {
            Set<String> rolCodes = new HashSet<String>();
            for (Role role : source.getRoles()) {
                if (!rolCodes.contains(role.getName())) {
                    rolCodes.add(role.getName());
                    org.siemac.metamac.notices.core.notice.domain.Role roleElement = new org.siemac.metamac.notices.core.notice.domain.Role();
                    roleElement.setName(role.getName());
                    roleElement.setNotice(notice);
                    target.add(roleElement);
                }
            }
        }
        return target;
    }

    private List<org.siemac.metamac.notices.core.notice.domain.StatisticalOperation> statisticalOperationsRestToEntity(StatisticalOperations source,
            org.siemac.metamac.notices.core.notice.domain.Notice notice) {
        List<org.siemac.metamac.notices.core.notice.domain.StatisticalOperation> target = new ArrayList<org.siemac.metamac.notices.core.notice.domain.StatisticalOperation>();
        if (source != null) {
            Set<String> rolCodes = new HashSet<String>();
            for (StatisticalOperation statisticalOperation : source.getStatisticalOperations()) {
                if (!rolCodes.contains(statisticalOperation.getUrn())) {
                    rolCodes.add(statisticalOperation.getUrn());
                    org.siemac.metamac.notices.core.notice.domain.StatisticalOperation statisticalOperationElement = new org.siemac.metamac.notices.core.notice.domain.StatisticalOperation();
                    statisticalOperationElement.setName(statisticalOperation.getUrn());
                    statisticalOperationElement.setNotice(notice);
                    target.add(statisticalOperationElement);
                }
            }
        }
        return target;
    }

    private List<org.siemac.metamac.notices.core.notice.domain.App> applicationsRestToEntity(Applications source, org.siemac.metamac.notices.core.notice.domain.Notice notice) {
        List<org.siemac.metamac.notices.core.notice.domain.App> target = new ArrayList<org.siemac.metamac.notices.core.notice.domain.App>();
        if (source != null) {
            Set<String> applicationCodes = new HashSet<String>();
            for (Application application : source.getApplications()) {
                if (!applicationCodes.contains(application.getName())) {
                    applicationCodes.add(application.getName());
                    org.siemac.metamac.notices.core.notice.domain.App app = new org.siemac.metamac.notices.core.notice.domain.App();
                    app.setName(application.getName());
                    app.setNotice(notice);
                    target.add(app);
                }
            }
        }
        return target;
    }

    private List<org.siemac.metamac.notices.core.notice.domain.Receiver> receiversRestToEntity(Receivers source, org.siemac.metamac.notices.core.notice.domain.Notice notice) {
        List<org.siemac.metamac.notices.core.notice.domain.Receiver> target = new ArrayList<org.siemac.metamac.notices.core.notice.domain.Receiver>();

        if (source != null) {
            Set<String> usernamesInNotices = new HashSet<String>();
            for (org.siemac.metamac.rest.notices.v1_0.domain.Receiver sourceReceiver : source.getReceivers()) {
                if (!usernamesInNotices.contains(sourceReceiver.getUsername())) {
                    usernamesInNotices.add(sourceReceiver.getUsername());
                    org.siemac.metamac.notices.core.notice.domain.Receiver receiverElement = new org.siemac.metamac.notices.core.notice.domain.Receiver();
                    receiverElement.setUsername(sourceReceiver.getUsername());
                    receiverElement.setNotice(notice);
                    target.add(receiverElement);
                }
            }
        }
        return target;
    }

    private List<org.siemac.metamac.notices.core.notice.domain.Message> messagesRestToEntity(Messages source, org.siemac.metamac.notices.core.notice.domain.Notice notice) throws MetamacException {
        List<org.siemac.metamac.notices.core.notice.domain.Message> target = new ArrayList<org.siemac.metamac.notices.core.notice.domain.Message>();

        // Messages
        if (source != null) {
            for (Message sourceMessage : source.getMessages()) {
                if (sourceMessage != null) {
                    org.siemac.metamac.notices.core.notice.domain.Message targetMessage = new org.siemac.metamac.notices.core.notice.domain.Message(sourceMessage.getText());
                    targetMessage.setNotice(notice);

                    if (sourceMessage.getResources() != null && !sourceMessage.getResources().getResources().isEmpty()) {
                        for (ResourceInternal sourceResource : sourceMessage.getResources().getResources()) {
                            ExternalItem targetResource = commonRest2DoMapper.externalItemRestToExternalItemDo(sourceResource);
                            targetMessage.addResource(targetResource);
                        }
                    }

                    target.add(targetMessage);
                }
            }
        }

        return target;
    }
}
