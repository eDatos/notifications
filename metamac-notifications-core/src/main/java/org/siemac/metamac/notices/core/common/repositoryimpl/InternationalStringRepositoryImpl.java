package org.siemac.metamac.notices.core.common.repositoryimpl;

import java.util.Collection;

import org.springframework.stereotype.Repository;

/**
 * Repository implementation for InternationalString
 */
@Repository("internationalStringRepository")
public class InternationalStringRepositoryImpl extends InternationalStringRepositoryBase {

    public InternationalStringRepositoryImpl() {
    }

    @Override
    public void deleteInternationalStringsEfficiently(Collection<Long> internationalStringToDelete) {
        throw new UnsupportedOperationException("deleteInternationalStringsEfficiently not implemented");

    }
}
