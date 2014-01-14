package org.siemac.metamac.statistical.resources.core.utils.sql;

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
import org.siemac.metamac.notifications.core.common.domain.ExternalItem;
import org.siemac.metamac.notifications.core.common.domain.InternationalString;
import org.siemac.metamac.notifications.core.common.domain.LocalisedString;
import org.siemac.metamac.notifications.core.notice.domain.Message;
import org.siemac.metamac.notifications.core.notice.domain.Notification;
import org.siemac.metamac.notifications.core.notice.domain.Receiver;
import org.siemac.metamac.notifications.core.notice.domain.Role;
import org.siemac.metamac.notifications.core.notice.enume.domain.NotificationType;

public class NotificationsDoMocks extends MetamacMocks {

    // -----------------------------------------------------------------
    // NOTIFICATIONS
    // -----------------------------------------------------------------

    public static Notification mockNotificationWithResources_TYPE_NOTIFICATION() {
        Notification notification = buildNotification();
        notification.getMessages().add(mockMessageWithResources());
        return notification;
    }

    public static Notification mockNotificationWithoutResources_TYPE_NOTIFICATION() {
        Notification notification = buildNotification();
        notification.getMessages().add(mockMessageWithoutResources());
        return notification;
    }

    public static Notification mockNotificationWithoutResources_TYPE_NOTIFICATION(String urn, String sendingApplication, String messageText) {
        Notification notification = mockNotificationWithoutResources_TYPE_NOTIFICATION();
        notification.setUrn(urn);
        notification.setSendingApplication(sendingApplication);
        notification.getMessages().get(0).setText(messageText);

        return notification;
    }

    private static Notification buildNotification() {
        Notification notification = new Notification(mockString(6), mockSentence(4), NotificationType.NOTIFICATION);
        notification.setCreatedDate(new DateTime());
        notification.setExpirationDate(new DateTime(2013, 1, 1, 1, 1, 1, 1));
        Role role = new Role();
        role.setName("ADMIN");
        notification.addRole(role);

        // Receivers
        for (int i = 0; i < 5; i++) {
            Receiver receiverElement = new Receiver();
            receiverElement.setUsername("user-" + i);
            notification.addReceiver(receiverElement);
        }
        return notification;
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

    // -----------------------------------------------------------------
    // SENTENCE
    // -----------------------------------------------------------------

    private static String mockSentence(int words) {
        StringBuilder sentence = new StringBuilder();
        for (int i = 0; i < words; i++) {
            sentence.append(mockString(6)).append(" ");
        }
        return sentence.toString();
    }
}