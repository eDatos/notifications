package org.siemac.metamac.notices.web.shared.criteria;

import org.siemac.metamac.web.common.shared.criteria.MetamacWebCriteria;

public class PaginationWebCriteria extends MetamacWebCriteria {

    private static final long serialVersionUID = -3883455642864577210L;

    private int               firstResult;
    private int               maxResults;

    public PaginationWebCriteria() {
    }

    public PaginationWebCriteria(String criteria, int firstResult, int maxResults) {
        super(criteria);
        setFirstResult(firstResult);
        setMaxResults(maxResults);
    }

    public int getFirstResult() {
        return firstResult;
    }

    public void setFirstResult(int firstResult) {
        this.firstResult = firstResult;
    }

    public int getMaxResults() {
        return maxResults;
    }

    public void setMaxResults(int maxResults) {
        this.maxResults = maxResults;
    }
}
