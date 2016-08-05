package org.siemac.metamac.notices.core.aop;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.siemac.metamac.core.common.aop.FlushingInterceptorBase;

public class FlushingInterceptor extends FlushingInterceptorBase {

    @Override
    @PersistenceContext(unitName = "NotificationsEntityManagerFactory")
    protected void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}