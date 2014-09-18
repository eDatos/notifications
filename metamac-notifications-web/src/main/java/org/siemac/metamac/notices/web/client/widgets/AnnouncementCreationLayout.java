package org.siemac.metamac.notices.web.client.widgets;

import static org.siemac.metamac.notices.web.client.NoticesWeb.getConstants;

import org.siemac.metamac.notices.core.dto.NoticeDto;
import org.siemac.metamac.notices.web.client.model.ds.NoticeDS;
import org.siemac.metamac.web.common.client.widgets.form.MainFormLayout;
import org.siemac.metamac.web.common.client.widgets.form.fields.CustomDateItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.CustomTextItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.RichTextEditorItem;

import com.smartgwt.client.widgets.events.HasClickHandlers;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class AnnouncementCreationLayout extends VLayout {

    private MainFormLayout  mainFormLayout;
    private DynamicForm     form;
    private ToolStripButton sendButton;

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

        sendButton = new ToolStripButton(getConstants().actionSend());
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

        form.setFields(subjectItem, messageItem, expirationDateItem);
    }

    public HasClickHandlers getSendButton() {
        return sendButton;
    }

    public NoticeDto getNotice() {
        return new NoticeDto(); // TODO METAMAC-1984
    }
}
