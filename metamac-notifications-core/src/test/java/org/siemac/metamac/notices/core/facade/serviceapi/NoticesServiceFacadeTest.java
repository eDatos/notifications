package org.siemac.metamac.notices.core.facade.serviceapi;

import static org.junit.Assert.fail;

import org.fornax.cartridges.sculptor.framework.test.AbstractDbUnitJpaTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Spring based transactional test with DbUnit support.
 */
public class NoticesServiceFacadeTest extends AbstractDbUnitJpaTests implements NoticesServiceFacadeTestBase {

    @Autowired
    protected NoticesServiceFacade noticesServiceFacade;

    @Override
    @Test
    public void testFindNotices() throws Exception {
        // TODO Auto-generated method stub
        fail("testFindNotices not implemented");
    }
}
