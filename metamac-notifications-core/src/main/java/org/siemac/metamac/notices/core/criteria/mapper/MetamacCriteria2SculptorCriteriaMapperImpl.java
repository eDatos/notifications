package org.siemac.metamac.notices.core.criteria.mapper;

import org.fornax.cartridges.sculptor.framework.domain.Property;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaPropertyRestriction;
import org.siemac.metamac.core.common.criteria.SculptorPropertyCriteria;
import org.siemac.metamac.core.common.criteria.SculptorPropertyCriteriaBase;
import org.siemac.metamac.core.common.criteria.mapper.MetamacCriteria2SculptorCriteria;
import org.siemac.metamac.core.common.criteria.mapper.MetamacCriteria2SculptorCriteria.CriteriaCallback;
import org.siemac.metamac.core.common.criteria.shared.MetamacCriteriaOrder;
import org.siemac.metamac.core.common.criteria.utils.CriteriaUtils;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.notices.core.criteria.NoticeCriteriaOrderEnum;
import org.siemac.metamac.notices.core.criteria.NoticeCriteriaPropertyEnum;
import org.siemac.metamac.notices.core.error.ServiceExceptionType;
import org.siemac.metamac.notices.core.notice.domain.Notice;
import org.siemac.metamac.notices.core.notice.domain.NoticeProperties;
import org.springframework.stereotype.Component;

@Component
public class MetamacCriteria2SculptorCriteriaMapperImpl implements MetamacCriteria2SculptorCriteriaMapper {

    private MetamacCriteria2SculptorCriteria<Notice> noticeCriteriaMapper = null;

    public MetamacCriteria2SculptorCriteriaMapperImpl() throws MetamacException {
        noticeCriteriaMapper = new MetamacCriteria2SculptorCriteria<Notice>(Notice.class, NoticeCriteriaOrderEnum.class, NoticeCriteriaPropertyEnum.class, new NoticeCriteriaCallback());
    }

    @Override
    public MetamacCriteria2SculptorCriteria<Notice> getNoticeCriteriaMapper() {
        return noticeCriteriaMapper;
    }

    private class NoticeCriteriaCallback implements CriteriaCallback {

        @Override
        public SculptorPropertyCriteriaBase retrieveProperty(MetamacCriteriaPropertyRestriction propertyRestriction) throws MetamacException {
            NoticeCriteriaPropertyEnum propertyEnum = NoticeCriteriaPropertyEnum.fromValue(propertyRestriction.getPropertyName());
            switch (propertyEnum) {
                case SUBJECT:
                    return new SculptorPropertyCriteria(NoticeProperties.subject(), propertyRestriction.getStringValue(), propertyRestriction.getOperationType());
                case ACKNOWLEDGE:
                    return new SculptorPropertyCriteria(NoticeProperties.receivers().acknowledge(), propertyRestriction.getBooleanValue(), propertyRestriction.getOperationType());
                case RECEIVER_USERNAME:
                    return new SculptorPropertyCriteria(NoticeProperties.receivers().username(), propertyRestriction.getStringValue(), propertyRestriction.getOperationType());
                case SENDING_APPLICATION:
                    return new SculptorPropertyCriteria(NoticeProperties.sendingApplication(), propertyRestriction.getStringValue(), propertyRestriction.getOperationType());
                case SENDING_USER:
                    return new SculptorPropertyCriteria(NoticeProperties.sendingUser(), propertyRestriction.getStringValue(), propertyRestriction.getOperationType());
                case TYPE:
                    return new SculptorPropertyCriteria(NoticeProperties.noticeType(), propertyRestriction.getEnumValue(), propertyRestriction.getOperationType());
                default:
                    throw new MetamacException(ServiceExceptionType.PARAMETER_INCORRECT, propertyRestriction.getPropertyName());
            }
        }

        @SuppressWarnings("unchecked")
        @Override
        public Property<Notice> retrievePropertyOrder(MetamacCriteriaOrder order) throws MetamacException {
            NoticeCriteriaOrderEnum noticeCriteriaOrderEnum = NoticeCriteriaOrderEnum.fromValue(order.getPropertyName());
            switch (noticeCriteriaOrderEnum) {
                case CREATED_DATE:
                    return CriteriaUtils.getDatetimedLeafProperty(NoticeProperties.createdDate(), Notice.class);
                default:
                    throw new MetamacException(ServiceExceptionType.PARAMETER_INCORRECT, order.getPropertyName());
            }
        }

        @SuppressWarnings("unchecked")
        @Override
        public Property<Notice> retrievePropertyOrderDefault() throws MetamacException {
            return CriteriaUtils.getDatetimedLeafProperty(NoticeProperties.createdDate(), Notice.class);
        }
    }
}
