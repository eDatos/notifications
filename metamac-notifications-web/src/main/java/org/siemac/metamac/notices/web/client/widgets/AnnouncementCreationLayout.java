package org.siemac.metamac.notices.web.client.widgets;

import static org.siemac.metamac.notices.web.client.NoticesWeb.getConstants;

import org.siemac.metamac.notices.core.dto.NoticeDto;
import org.siemac.metamac.notices.web.client.NoticesWeb;
import org.siemac.metamac.notices.web.client.model.ds.NoticeDS;
import org.siemac.metamac.notices.web.client.view.handlers.AnnouncementCreationUiHandlers;
import org.siemac.metamac.notices.web.shared.GetStatisticalOperationsResult;
import org.siemac.metamac.notices.web.shared.criteria.PaginationWebCriteria;
import org.siemac.metamac.web.common.client.constants.CommonWebConstants;
import org.siemac.metamac.web.common.client.widgets.form.MainFormLayout;
import org.siemac.metamac.web.common.client.widgets.form.fields.CustomDateItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.CustomTextItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.RichTextEditorItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.external.SearchMultiExternalItemSimpleItem;
import org.siemac.metamac.web.common.shared.criteria.MetamacWebCriteria;

import com.smartgwt.client.widgets.events.HasClickHandlers;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class AnnouncementCreationLayout extends VLayout {

    private MainFormLayout                 mainFormLayout;
    private DynamicForm                    form;
    private ToolStripButton                sendButton;

    private AnnouncementCreationUiHandlers uiHandlers;

    public AnnouncementCreationLayout() {
        setMargin(10);
        setMembersMargin(10);
        createMainFormLayout();
    }

    private void createMainFormLayout() {
        mainFormLayout = new MainFormLayout();
        mainFormLayout.setEditionMode();
        mainFormLayout.getSave().setVisible(false);
        mainFormLayout.getCancelToolStripButton().setVisible(false);

        sendButton = new ToolStripButton(getConstants().actionCreate());
        mainFormLayout.getToolStrip().addButton(sendButton);

        createForm();
        mainFormLayout.addEditionCanvas(form);
        addMember(mainFormLayout);
    }

    private void createForm() {
        form = new DynamicForm();

        CustomTextItem subjectItem = new CustomTextItem(NoticeDS.SUBJECT, getConstants().noticeSubject());
        subjectItem.setRequired(true);

        RichTextEditorItem messageItem = new RichTextEditorItem(NoticeDS.MESSAGE, getConstants().noticeMessage());
        messageItem.setRequired(true);
        messageItem.setTitleStyle("formTitle");

        CustomDateItem expirationDateItem = new CustomDateItem(NoticeDS.EXPIRATION_DATE, getConstants().noticeExpirationDate());

        SearchMultiExternalItemSimpleItem operationsItem = new SearchMultiExternalItemSimpleItem(NoticeDS.STATISTICAL_OPERATION, NoticesWeb.getConstants().noticeStatisticalOperations(),
                CommonWebConstants.FORM_LIST_MAX_RESULTS) {

            @Override
            protected void retrieveResources(int firstResult, int maxResults, MetamacWebCriteria webCriteria) {
                getUiHandlers().retrieveStatisticalOperations(new PaginationWebCriteria(webCriteria.getCriteria(), firstResult, maxResults));
            }
        };

        SearchRolesItem rolesItem = new SearchRolesItem(NoticeDS.ROLE, getConstants().noticeRoles());
        SearchAppsItem appsItem = new SearchAppsItem(NoticeDS.APPLICATION, getConstants().noticeApplications());

        form.setFields(subjectItem, messageItem, expirationDateItem, operationsItem, rolesItem, appsItem);
    }

    public void setStatisticalOperations(GetStatisticalOperationsResult result) {
        ((SearchMultiExternalItemSimpleItem) form.getItem(NoticeDS.STATISTICAL_OPERATION)).setResources(result.getOperations(), result.getFirstResult(), result.getTotalResults());
    }

    public HasClickHandlers getSendButton() {
        return sendButton;
    }

    public NoticeDto getNotice() {
        return new NoticeDto(); // TODO METAMAC-1984
    }

    public void setUiHandlers(AnnouncementCreationUiHandlers uiHandlers) {
        this.uiHandlers = uiHandlers;
    }

    public AnnouncementCreationUiHandlers getUiHandlers() {
        return uiHandlers;
    }

    public void clearValues() {
        form.clearValues();
        ((SearchMultiExternalItemSimpleItem) form.getItem(NoticeDS.STATISTICAL_OPERATION)).clearRelatedResourceList();
    }
}
