package org.siemac.metamac.notices.core.utils.mocks.templates;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.siemac.metamac.common.test.utils.MetamacMocks;
import org.siemac.metamac.core.common.constants.CoreCommonConstants;
import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
import org.siemac.metamac.core.common.enume.utils.TypeExternalArtefactsEnumUtils;
import org.siemac.metamac.notices.core.common.domain.ExternalItem;
import org.siemac.metamac.notices.core.common.domain.InternationalString;
import org.siemac.metamac.notices.core.common.domain.LocalisedString;
import org.siemac.metamac.notices.core.notice.domain.Message;
import org.siemac.metamac.notices.core.notice.domain.Notice;
import org.siemac.metamac.notices.core.notice.domain.Receiver;
import org.siemac.metamac.notices.core.notice.domain.Role;
import org.siemac.metamac.notices.core.notice.domain.StatisticalOperation;
import org.siemac.metamac.notices.core.notice.enume.domain.NoticeType;

public abstract class NoticesDoMocks extends MetamacMocks {

    // -----------------------------------------------------------------
    // NOTIFICATIONS
    // -----------------------------------------------------------------

    public static Notice mockNoticeWithResources() {
        Notice notice = buildNotice();
        notice.getMessages().add(mockMessageWithResources());
        return notice;
    }

    public static Notice mockNoticeWithoutResources() {
        Notice notice = buildNotice();
        notice.getMessages().add(mockMessageWithoutResources());
        return notice;
    }

    public static Notice mockNoticeWithoutResources(String urn, String sendingApplication, String messageText) {
        Notice notice = mockNoticeWithoutResources();
        notice.setUrn(urn);
        notice.setSendingApplication(sendingApplication);
        notice.getMessages().get(0).setText(messageText);

        return notice;
    }

    private static Notice buildNotice() {
        Notice notice = new Notice(mockString(6), mockSentence(4), NoticeType.NOTIFICATION);
        notice.setCreatedDate(new DateTime(2012, 1, 1, 1, 1, 1, 1));
        notice.setExpirationDate(new DateTime(2013, 1, 1, 1, 1, 1, 1));

        // Role
        Role role = new Role();
        role.setName("ADMINISTRADOR");
        notice.addRole(role);

        // StatisticalOperation
        StatisticalOperation statisticalOperation = new StatisticalOperation();
        statisticalOperation.setName("urn:siemac:org.siemac.metamac.infomodel.statisticaloperations.Operation=operation01");
        notice.addStatisticalOperation(statisticalOperation);

        // Receivers
        for (int i = 1; i < 6; i++) {
            Receiver receiverElement = new Receiver();
            receiverElement.setUsername("user-" + i);
            notice.addReceiver(receiverElement);
        }
        return notice;
    }

    // -----------------------------------------------------------------
    // MESSAGES
    // -----------------------------------------------------------------

    public static Message mockMessageWithoutResources() {
        Message message = new Message(mockSentence(10));
        return message;
    }

    public static Message mockMessageWithResources() {
        Message message = mockMessageWithoutResources();
        message.getResources().add(mockStatisticalOperationExternalItem());
        message.getResources().add(mockStatisticalOperationExternalItem());
        return message;
    }

    // -----------------------------------------------------------------
    // EXTERNAL ITEMS
    // -----------------------------------------------------------------

    public static ExternalItem mockCommonConfigurationExternalItem() {
        String code = mockCode();
        return mockExternalItem(code, mockCommonConfigurationUrn(code), TypeExternalArtefactsEnum.CONFIGURATION);
    }

    public static ExternalItem mockStatisticalOperationExternalItem() {
        String code = mockCode();
        return mockStatisticalOperationExternalItem(code);
    }

    public static ExternalItem mockStatisticalOperationExternalItem(String code) {
        return mockExternalItem(code, mockStatisticalOperationUrn(code), TypeExternalArtefactsEnum.STATISTICAL_OPERATION);
    }

    public static ExternalItem mockStatisticalOperationInstanceExternalItem() {
        String code = mockCode();
        return mockStatisticalOperationInstanceExternalItem(code);
    }

    public static ExternalItem mockStatisticalOperationInstanceExternalItem(String code) {
        return mockExternalItem(code, mockStatisticalOperationInstanceUrn(code), TypeExternalArtefactsEnum.STATISTICAL_OPERATION_INSTANCE);
    }

    public static ExternalItem mockAgencyExternalItem() {
        String code = mockCode();
        return mockAgencyExternalItem(code);
    }

    public static ExternalItem mockAgencyExternalItem(String code) {
        return mockExternalItem(code, mockAgencyUrn(code), TypeExternalArtefactsEnum.AGENCY);
    }

    public static ExternalItem mockAgencyExternalItem(String code, String codeNested) {
        return mockExternalItem(code, codeNested, mockAgencyUrn(code), TypeExternalArtefactsEnum.AGENCY);
    }

    public static ExternalItem mockOrganizationUnitExternalItem() {
        String code = mockCode();
        return mockOrganizationUnitExternalItem(code);
    }

    public static ExternalItem mockOrganizationUnitExternalItem(String code) {
        return mockExternalItem(code, mockOrganizationUnitUrn(code), TypeExternalArtefactsEnum.ORGANISATION_UNIT);
    }

    public static ExternalItem mockConceptExternalItem() {
        String code = mockCode();
        return mockConceptExternalItem(code);
    }

    public static ExternalItem mockConceptExternalItem(String code) {
        return mockExternalItem(code, mockConceptUrn(code), TypeExternalArtefactsEnum.CONCEPT);
    }

    public static ExternalItem mockConceptSchemeExternalItem() {
        String code = mockCode();
        return mockExternalItem(code, mockConceptSchemeUrn(code), TypeExternalArtefactsEnum.CONCEPT_SCHEME);
    }

    public static ExternalItem mockCodeListSchemeExternalItem() {
        String code = mockCode();
        return mockExternalItem(code, mockCodeListUrn(code), TypeExternalArtefactsEnum.CODELIST);
    }

    public static ExternalItem mockCodeExternalItem() {
        String code = mockCode();
        return mockCodeExternalItem(code);
    }

    public static ExternalItem mockCodeExternalItem(String code) {
        return mockExternalItem(code, mockCodeUrn(code), TypeExternalArtefactsEnum.CODE);
    }

    public static ExternalItem mockCodeExternalItem(String code, String title) {
        return mockExternalItem(code, mockCodeUrn(code), TypeExternalArtefactsEnum.CODE);
    }

    public static ExternalItem mockCategoryExternalItem(String code) {
        return mockExternalItem(code, mockCategoryUrn(code), TypeExternalArtefactsEnum.CATEGORY);
    }

    public static ExternalItem mockDsdExternalItem() {
        String code = mockCode();
        return mockDsdExternalItem(code);
    }

    public static ExternalItem mockDsdExternalItem(String code) {
        return mockExternalItem(code, mockDsdUrn(code), TypeExternalArtefactsEnum.DATASTRUCTURE);
    }

    public static ExternalItem mockDimensionExternalItem() {
        String code = mockCode();
        return mockExternalItem(code, mockDimensionUrn(code), TypeExternalArtefactsEnum.DIMENSION);
    }

    public static ExternalItem mockConfigurationExternalItem() {
        String code = mockCode();
        return mockExternalItem(code, mockCommonConfigurationUrn(code), TypeExternalArtefactsEnum.CONFIGURATION);
    }

    public static ExternalItem mockExternalItem(String code, String codeNested, String uri, String urnProvider, String urn, TypeExternalArtefactsEnum type) {
        ExternalItem target = new ExternalItem();
        target.setVersion(Long.valueOf(0));
        target.setCode(code);
        target.setCodeNested(codeNested);
        target.setUri(uri);
        target.setUrn(urn);
        target.setUrnProvider(urnProvider);
        target.setType(type);
        return target;
    }

    public static ExternalItem mockExternalItem(String code, String codeNested, String uri, String urnProvider, String urn, TypeExternalArtefactsEnum type, InternationalString title,
            String managementAppUrl) {
        ExternalItem target = mockExternalItem(code, codeNested, uri, urnProvider, urn, type);
        target.setTitle(title);
        target.setManagementAppUrl(managementAppUrl);
        return target;
    }

    private static ExternalItem mockExternalItem(String code, String urn, TypeExternalArtefactsEnum type) {
        return mockExternalItem(code, null, urn, type);
    }

    private static ExternalItem mockExternalItem(String code, String codeNested, String urn, TypeExternalArtefactsEnum type) {
        String uri = CoreCommonConstants.API_LATEST_WITH_SLASHES + code;
        String urnProvider = urn + ":provider";
        InternationalString title = mockInternationalStringMetadata(code, "title");
        String managementAppUrl = CoreCommonConstants.URL_SEPARATOR + code;

        if (TypeExternalArtefactsEnumUtils.isExternalItemOfCommonMetadataApp(type)) {
            urnProvider = null;
        } else if (TypeExternalArtefactsEnumUtils.isExternalItemOfStatisticalOperationsApp(type)) {
            urnProvider = null;
        } else if (TypeExternalArtefactsEnumUtils.isExternalItemOfSrmApp(type)) {
            // nothing to do with urnInternal because it's ok for SrmExternalItems
            if (StringUtils.isBlank(codeNested)) {
                if (TypeExternalArtefactsEnum.AGENCY.equals(type) || TypeExternalArtefactsEnum.CATEGORY.equals(type)) {
                    codeNested = code;
                }
            }
        } else {
            fail("Unexpected type of ExternalItem:" + type);
        }

        ExternalItem item = mockExternalItem(code, codeNested, uri, urnProvider, urn, type, title, managementAppUrl);
        return item;
    }

    // -----------------------------------------------------------------
    // INTERNATIONAL STRING
    // -----------------------------------------------------------------

    public static InternationalString mockInternationalString() {
        return mockInternationalStringMetadata(null, mockString(10));
    }

    public static InternationalString mockInternationalStringMetadata(String resource, String metadata) {
        assertNotNull(metadata);

        InternationalString internationalString = new InternationalString();
        internationalString.setVersion(Long.valueOf(0));
        {
            LocalisedString es = new LocalisedString();
            if (resource != null) {
                es.setLabel(metadata + "-" + resource + " en Espanol");
            } else {
                es.setLabel(metadata + " en Espanol");
            }
            es.setLocale("es");
            es.setVersion(Long.valueOf(0));
            internationalString.addText(es);
        }
        {
            LocalisedString en = new LocalisedString();
            if (resource != null) {
                en.setLabel(metadata + "-" + resource + " in English");
            } else {
                en.setLabel(metadata + " in English");
            }
            en.setLocale("en");
            en.setVersion(Long.valueOf(0));
            internationalString.addText(en);
        }
        return internationalString;
    }

    /**
     * Mock an InternationalString with one locale
     */
    public static InternationalString mockInternationalString(String locale, String label) {
        InternationalString target = new InternationalString();
        LocalisedString localisedString = new LocalisedString();
        localisedString.setLocale(locale);
        localisedString.setLabel(label);
        target.addText(localisedString);
        return target;
    }

    /**
     * Mock an InternationalString with two locales
     */
    public static InternationalString mockInternationalString(String locale01, String label01, String locale02, String label02) {
        InternationalString target = new InternationalString();
        LocalisedString localisedString01 = new LocalisedString();
        localisedString01.setLocale(locale01);
        localisedString01.setLabel(label01);
        target.addText(localisedString01);

        LocalisedString localisedString02 = new LocalisedString();
        localisedString02.setLocale(locale02);
        localisedString02.setLabel(label02);
        target.addText(localisedString02);
        return target;
    }

    /**
     * Mock an InternationalString with two locales
     */
    public static Map<String, String> mockInternationalStringAsMap(String locale01, String label01, String locale02, String label02) {
        Map<String, String> target = new HashMap<String, String>();
        if (locale01 != null) {
            target.put(locale01, label01);
        }
        if (locale02 != null) {
            target.put(locale02, label02);
        }
        return target;
    }

}