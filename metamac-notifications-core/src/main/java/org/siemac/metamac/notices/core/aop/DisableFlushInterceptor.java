package org.siemac.metamac.notices.core.aop;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.siemac.metamac.core.common.aop.DisableFlushInterceptorBase;

public class DisableFlushInterceptor extends DisableFlushInterceptorBase {

    @Override
    @PersistenceContext(unitName = "NotificationsEntityManagerFactory")
    protected void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}