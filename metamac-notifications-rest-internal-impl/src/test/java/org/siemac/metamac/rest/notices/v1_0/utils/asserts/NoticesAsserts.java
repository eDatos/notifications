package org.siemac.metamac.rest.notices.v1_0.utils.asserts;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.notices.core.error.ServiceExceptionType;
import org.siemac.metamac.notices.core.notice.domain.App;
import org.siemac.metamac.notices.core.notice.domain.Message;
import org.siemac.metamac.notices.core.notice.domain.Receiver;
import org.siemac.metamac.notices.core.notice.domain.Role;
import org.siemac.metamac.notices.core.notice.domain.StatisticalOperation;
import org.siemac.metamac.rest.notices.v1_0.domain.Applications;
import org.siemac.metamac.rest.notices.v1_0.domain.Messages;
import org.siemac.metamac.rest.notices.v1_0.domain.Notice;
import org.siemac.metamac.rest.notices.v1_0.domain.Receivers;
import org.siemac.metamac.rest.notices.v1_0.domain.Roles;
import org.siemac.metamac.rest.notices.v1_0.domain.StatisticalOperations;

public class NoticesAsserts extends CommonAsserts {

    public static void assertEqualsNotice(org.siemac.metamac.notices.core.notice.domain.Notice doNotice, Notice restNotice) throws MetamacException {
        assertEquals(doNotice.getSendingApplication(), restNotice.getSendingApplication());
        assertEquals(doNotice.getSendingUser(), restNotice.getSendingUser());
        assertEquals(doNotice.getSubject(), restNotice.getSubject());
        assertEquals(doNotice.getExpirationDate(), restNotice.getExpirationDate());
        assertEquals(doNotice.getNoticeType().toString(), restNotice.getNoticeType().toString());
        assertEqualsStatisticalOperationsCollection(doNotice.getStatisticalOperations(), restNotice.getStatisticalOperations());
        assertEqualsReceiversCollection(doNotice.getReceivers(), restNotice.getReceivers());
        assertEqualsApplicationsCollection(doNotice.getApps(), restNotice.getApplications());
        assertEqualsRolesCollection(doNotice.getRoles(), restNotice.getRoles());
        assertEqualsMessagesCollection(doNotice.getMessages(), restNotice.getMessages());
    }

    private static void assertEqualsMessagesCollection(java.util.List<Message> doCollection, Messages restCollection) throws MetamacException {
        if (doCollection.isEmpty() && (restCollection == null || restCollection.getMessages().isEmpty())) {
            return;
        }
        assertEquals(doCollection.size(), restCollection.getTotal().intValue());

        for (Message doMessage : doCollection) {
            boolean found = false;
            for (org.siemac.metamac.rest.notices.v1_0.domain.Message restMessage : restCollection.getMessages()) {
                if (doMessage.getText().equals(restMessage.getText())) {
                    assertNotNull(doMessage.getNotice());
                    found = true;
                    assertEqualsExternalItemCollection(doMessage.getResources(), restMessage.getResources());
                    return;
                }
            }

            if (!found) {
                throw new MetamacException(ServiceExceptionType.UNKNOWN, "Not equals roles collection. Not found: " + doMessage.getText());
            }
        }
    }

    private static void assertEqualsRolesCollection(java.util.List<Role> doCollection, Roles restCollection) throws MetamacException {
        if (doCollection.isEmpty() && (restCollection == null || restCollection.getRoles().isEmpty())) {
            return;
        }
        assertEquals(doCollection.size(), restCollection.getTotal().intValue());

        for (Role doRole : doCollection) {
            boolean found = false;
            for (org.siemac.metamac.rest.notices.v1_0.domain.Role restRole : restCollection.getRoles()) {
                if (doRole.getName().equals(restRole.getName())) {
                    assertNotNull(doRole.getNotice());
                    found = true;
                    return;
                }
            }

            if (!found) {
                throw new MetamacException(ServiceExceptionType.UNKNOWN, "Not equals roles collection. Not found: " + doRole.getName());
            }
        }
    }

    private static void assertEqualsApplicationsCollection(java.util.List<App> doCollection, Applications restCollection) throws MetamacException {
        if (doCollection.isEmpty() && (restCollection == null || restCollection.getApplications().isEmpty())) {
            return;
        }
        assertEquals(doCollection.size(), restCollection.getTotal().intValue());

        for (App doApplication : doCollection) {
            boolean found = false;
            for (org.siemac.metamac.rest.notices.v1_0.domain.Application restApplication : restCollection.getApplications()) {
                if (doApplication.getName().equals(restApplication.getName())) {
                    assertNotNull(doApplication.getNotice());
                    found = true;
                    return;
                }
            }

            if (!found) {
                throw new MetamacException(ServiceExceptionType.UNKNOWN, "Not equals applications collection. Not found: " + doApplication.getName());
            }
        }

    }

    private static void assertEqualsReceiversCollection(java.util.List<Receiver> doCollection, Receivers restCollection) throws MetamacException {
        if (doCollection.isEmpty() && (restCollection == null || restCollection.getReceivers().isEmpty())) {
            return;
        }

        assertEquals(doCollection.size(), restCollection.getTotal().intValue());

        for (Receiver doReceiver : doCollection) {
            boolean found = false;
            for (org.siemac.metamac.rest.notices.v1_0.domain.Receiver restReceiver : restCollection.getReceivers()) {
                if (doReceiver.getUsername().equals(restReceiver.getUsername())) {
                    assertNotNull(doReceiver.getNotice());
                    found = true;
                    return;
                }
            }

            if (!found) {
                throw new MetamacException(ServiceExceptionType.UNKNOWN, "Not equals receivers collection. Not found: " + doReceiver.getUsername());
            }
        }

    }

    private static void assertEqualsStatisticalOperationsCollection(java.util.List<StatisticalOperation> doCollection, StatisticalOperations restCollection) throws MetamacException {
        if (doCollection.isEmpty() && (restCollection == null || restCollection.getStatisticalOperations().isEmpty())) {
            return;
        }
        assertEquals(doCollection.size(), restCollection.getTotal().intValue());

        for (StatisticalOperation doStatisticalOperation : doCollection) {
            boolean found = false;
            for (org.siemac.metamac.rest.notices.v1_0.domain.StatisticalOperation restStatisticalOperation : restCollection.getStatisticalOperations()) {
                if (doStatisticalOperation.getName().equals(restStatisticalOperation.getUrn())) {
                    assertNotNull(doStatisticalOperation.getNotice());
                    found = true;
                    return;
                }
            }

            if (!found) {
                throw new MetamacException(ServiceExceptionType.UNKNOWN, "Not equals statisticalOperations collection. Not found: " + doStatisticalOperation.getName());
            }
        }

    }
}
