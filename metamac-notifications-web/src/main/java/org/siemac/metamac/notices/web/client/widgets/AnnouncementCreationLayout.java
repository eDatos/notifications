package org.siemac.metamac.notices.web.client.widgets;

import static org.siemac.metamac.notices.web.client.NoticesWeb.getConstants;

import java.util.List;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.notices.core.dto.MessageDto;
import org.siemac.metamac.notices.core.dto.NoticeDto;
import org.siemac.metamac.notices.core.dto.ReceiverDto;
import org.siemac.metamac.notices.web.client.NoticesWeb;
import org.siemac.metamac.notices.web.client.enums.ReceiverType;
import org.siemac.metamac.notices.web.client.model.ds.NoticeDS;
import org.siemac.metamac.notices.web.client.utils.CommonUtils;
import org.siemac.metamac.notices.web.client.view.handlers.AnnouncementCreationUiHandlers;
import org.siemac.metamac.notices.web.shared.GetStatisticalOperationsResult;
import org.siemac.metamac.notices.web.shared.dto.AccessControlValueDto;
import org.siemac.metamac.web.common.client.constants.CommonWebConstants;
import org.siemac.metamac.web.common.client.utils.ExternalItemUtils;
import org.siemac.metamac.web.common.client.widgets.form.CustomDynamicForm;
import org.siemac.metamac.web.common.client.widgets.form.GroupDynamicForm;
import org.siemac.metamac.web.common.client.widgets.form.MainFormLayout;
import org.siemac.metamac.web.common.client.widgets.form.fields.CustomDateItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.CustomSelectItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.CustomTextItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.MultiTextItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.RichTextEditorItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.external.SearchMultiExternalItemSimpleItem;
import org.siemac.metamac.web.common.shared.criteria.MetamacWebCriteria;
import org.siemac.metamac.web.common.shared.criteria.PaginationWebCriteria;

import com.smartgwt.client.widgets.events.HasClickHandlers;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.validator.CustomValidator;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class AnnouncementCreationLayout extends VLayout {

    private MainFormLayout                 mainFormLayout;
    private GroupDynamicForm               receiversForm;
    private CustomDynamicForm              form;
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

        createReceiversForm();
        createForm();
        mainFormLayout.addEditionCanvas(receiversForm);
        mainFormLayout.addEditionCanvas(form);
        addMember(mainFormLayout);
    }

    private void createReceiversForm() {
        receiversForm = new GroupDynamicForm(getConstants().noticeReceivers());

        CustomSelectItem receiverTypeItem = new CustomSelectItem(NoticeDS.RECEIVER_TYPE, getConstants().receiverType());
        receiverTypeItem.setRequired(true);
        receiverTypeItem.setValueMap(CommonUtils.getReceiverTypeLinkedHashMap());
        receiverTypeItem.addChangedHandler(new ChangedHandler() {

            @Override
            public void onChanged(ChangedEvent event) {
                receiversForm.markForRedraw();
            }
        });

        MultiTextItem usersItem = new MultiTextItem(NoticeDS.USERNAMES, getConstants().noticeUsernames(), new CustomValidator() {

            @Override
            protected boolean condition(Object value) {
                // Set required with a custom validator
                return value != null && !StringUtils.isBlank((String) value);
            }
        });
        usersItem.setTitleStyle("staticFormItemTitle");
        usersItem.setShowIfCondition(getReceiverTypeUsernamesFormItemIfFunction());
        usersItem.setStartRow(true);

        SearchMultiExternalItemSimpleItem operationsItem = new SearchMultiExternalItemSimpleItem(NoticeDS.STATISTICAL_OPERATION, NoticesWeb.getConstants().noticeStatisticalOperations(),
                CommonWebConstants.FORM_LIST_MAX_RESULTS) {

            @Override
            protected void retrieveResources(int firstResult, int maxResults, MetamacWebCriteria webCriteria) {
                getUiHandlers().retrieveStatisticalOperations(new PaginationWebCriteria(webCriteria.getCriteria(), firstResult, maxResults));
            }
        };
        operationsItem.setShowIfCondition(getReceiverTypeConditionsFormItemIfFunction());
        operationsItem.setStartRow(true);

        SearchRolesItem rolesItem = new SearchRolesItem(NoticeDS.ROLE, getConstants().noticeRoles());
        rolesItem.setShowIfCondition(getReceiverTypeConditionsFormItemIfFunction());

        SearchAppsItem appsItem = new SearchAppsItem(NoticeDS.APPLICATION, getConstants().noticeApplications());
        appsItem.setShowIfCondition(getReceiverTypeConditionsFormItemIfFunction());

        receiversForm.setFields(receiverTypeItem, usersItem, operationsItem, rolesItem, appsItem);
    }

    private void createForm() {
        form = new CustomDynamicForm();

        CustomTextItem subjectItem = new CustomTextItem(NoticeDS.SUBJECT, getConstants().noticeSubject());
        subjectItem.setRequired(true);

        RichTextEditorItem messageItem = new RichTextEditorItem(NoticeDS.MESSAGE, getConstants().noticeMessage());
        messageItem.setRequired(true);
        messageItem.setTitleStyle("formTitle");

        CustomDateItem expirationDateItem = new CustomDateItem(NoticeDS.EXPIRATION_DATE, getConstants().noticeExpirationDate());

        form.setFields(subjectItem, messageItem, expirationDateItem);
    }

    public void setStatisticalOperations(GetStatisticalOperationsResult result) {
        ((SearchMultiExternalItemSimpleItem) receiversForm.getItem(NoticeDS.STATISTICAL_OPERATION)).setResources(result.getOperations(), result.getFirstResult(), result.getTotalResults());
    }

    public HasClickHandlers getSendButton() {
        return sendButton;
    }

    public NoticeDto getNotice() {
        NoticeDto noticeDto = new NoticeDto();
        updateUsernames(noticeDto);
        updateStatisticalOperations(noticeDto);
        updateRoles(noticeDto);
        updateApplications(noticeDto);

        // TODO METAMAC-1984 set sending user?
        // TODO METAMAC-1984 set sending app?

        noticeDto.setSubject(form.getValueAsString(NoticeDS.SUBJECT));
        noticeDto.addMessage(buildMessage(form.getValueAsString(NoticeDS.MESSAGE)));
        noticeDto.setExpirationDate(((CustomDateItem) form.getItem(NoticeDS.EXPIRATION_DATE)).getValueAsDate());

        return noticeDto;
    }

    public boolean validate() {
        return receiversForm.validate(false) && form.validate(false);
    }

    public void setUiHandlers(AnnouncementCreationUiHandlers uiHandlers) {
        this.uiHandlers = uiHandlers;
    }

    public AnnouncementCreationUiHandlers getUiHandlers() {
        return uiHandlers;
    }

    public void clearValues() {
        receiversForm.clearValues();
        ((MultiTextItem) receiversForm.getItem(NoticeDS.USERNAMES)).resetFormLayout();
        ((SearchMultiExternalItemSimpleItem) receiversForm.getItem(NoticeDS.STATISTICAL_OPERATION)).clearRelatedResourceList();
        ((SearchRolesItem) receiversForm.getItem(NoticeDS.ROLE)).clearRelatedResourceList();
        ((SearchAppsItem) receiversForm.getItem(NoticeDS.APPLICATION)).clearRelatedResourceList();
        form.clearValues();
    }

    private FormItemIfFunction getReceiverTypeUsernamesFormItemIfFunction() {
        return new FormItemIfFunction() {

            @Override
            public boolean execute(FormItem item, Object value, DynamicForm form) {
                ReceiverType type = CommonUtils.getReceiverType(form.getValueAsString(NoticeDS.RECEIVER_TYPE));
                return ReceiverType.USERS.equals(type);
            }
        };
    }

    private FormItemIfFunction getReceiverTypeConditionsFormItemIfFunction() {
        return new FormItemIfFunction() {

            @Override
            public boolean execute(FormItem item, Object value, DynamicForm form) {
                ReceiverType type = CommonUtils.getReceiverType(form.getValueAsString(NoticeDS.RECEIVER_TYPE));
                return ReceiverType.CONDITIONS.equals(type);
            }
        };
    }

    //
    // NOTICE VALUES UPDATE
    //

    private void updateUsernames(NoticeDto noticeDto) {
        if (receiversForm.getItem(NoticeDS.USERNAMES).isVisible()) {
            List<String> usernames = ((MultiTextItem) receiversForm.getItem(NoticeDS.USERNAMES)).getValues();
            for (String username : usernames) {
                noticeDto.addReceiver(buildReceiver(username));
            }
        }
    }

    private void updateStatisticalOperations(NoticeDto noticeDto) {
        if (receiversForm.getItem(NoticeDS.STATISTICAL_OPERATION).isVisible()) {
            List<ExternalItemDto> statisticalOperations = ((SearchMultiExternalItemSimpleItem) receiversForm.getItem(NoticeDS.STATISTICAL_OPERATION)).getExternalItemDtos();
            noticeDto.setStatisticalOperationCodes(ExternalItemUtils.getExternalItemCodes(statisticalOperations));
        }
    }

    private void updateRoles(NoticeDto noticeDto) {
        if (receiversForm.getItem(NoticeDS.ROLE).isVisible()) {
            List<AccessControlValueDto> values = ((SearchRolesItem) receiversForm.getItem(NoticeDS.ROLE)).getSelectedDtos();
            noticeDto.setRoles(CommonUtils.getAccessControlValueCodes(values));
        }
    }

    private void updateApplications(NoticeDto noticeDto) {
        if (receiversForm.getItem(NoticeDS.APPLICATION).isVisible()) {
            List<AccessControlValueDto> values = ((SearchAppsItem) receiversForm.getItem(NoticeDS.APPLICATION)).getSelectedDtos();
            noticeDto.setApplications(CommonUtils.getAccessControlValueCodes(values));
        }
    }

    private MessageDto buildMessage(String text) {
        MessageDto messageDto = new MessageDto();
        messageDto.setText(text);
        return messageDto;
    }

    private ReceiverDto buildReceiver(String username) {
        ReceiverDto receiverDto = new ReceiverDto();
        receiverDto.setUsername(username);
        receiverDto.setAcknowledge(false);
        return receiverDto;
    }
}
