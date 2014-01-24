package org.siemac.metamac.statistical.resources.core.utils.mocks.factories;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.siemac.metamac.core.common.test.utils.mocks.configuration.MockDescriptor;
import org.siemac.metamac.core.common.test.utils.mocks.configuration.MockFactory;
import org.siemac.metamac.notifications.core.notice.domain.Notification;
import org.siemac.metamac.notifications.core.utils.mocks.templates.NotificationsPersistedDoMocks;

public abstract class MetamacNotificationsMockFactory<EntityMock> extends MockFactory<EntityMock> {

    public static final String INIT_VERSION   = "001.000";
    public static final String SECOND_VERSION = "002.000";
    public static final String THIRD_VERSION  = "003.000";

    protected static NotificationsPersistedDoMocks getNotificationsPersistedDoMocks() {
        return NotificationsPersistedDoMocks.getInstance();
    }

    protected static Notification getNotificationMock(String id) {
        return NotificationMockFactory.getInstance().getMock(id);
    }

    protected static MockDescriptor getNotificationMockDescriptor(String id) {
        return NotificationMockFactory.getInstance().getMockWithDependencies(id);
    }

    protected static void registerNotificationMock(String id, Notification notification) {
        NotificationMockFactory.getInstance().registerMock(id, notification);
    }

    protected static List<Object> buildObjectList(Object... objs) {
        return new ArrayList<Object>(Arrays.asList(objs));
    }

}
