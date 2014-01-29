package org.siemac.metamac.notices.core.utils.mocks.factories;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.siemac.metamac.core.common.test.utils.mocks.configuration.MockDescriptor;
import org.siemac.metamac.core.common.test.utils.mocks.configuration.MockFactory;
import org.siemac.metamac.notices.core.notice.domain.Notice;
import org.siemac.metamac.notices.core.utils.mocks.templates.NoticesPersistedDoMocks;

public abstract class MetamacNoticesMockFactory<EntityMock> extends MockFactory<EntityMock> {

    public static final String INIT_VERSION   = "001.000";
    public static final String SECOND_VERSION = "002.000";
    public static final String THIRD_VERSION  = "003.000";

    protected static NoticesPersistedDoMocks getNoticesPersistedDoMocks() {
        return NoticesPersistedDoMocks.getInstance();
    }

    protected static Notice getNoticeMock(String id) {
        return NoticeMockFactory.getInstance().getMock(id);
    }

    protected static MockDescriptor getNoticeMockDescriptor(String id) {
        return NoticeMockFactory.getInstance().getMockWithDependencies(id);
    }

    protected static void registerNoticeMock(String id, Notice notice) {
        NoticeMockFactory.getInstance().registerMock(id, notice);
    }

    protected static List<Object> buildObjectList(Object... objs) {
        return new ArrayList<Object>(Arrays.asList(objs));
    }

}
