package org.siemac.metamac.notices.web.client.widgets;

import static org.siemac.metamac.notices.web.client.NoticesWeb.getConstants;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.notices.core.dto.MessageDto;
import org.siemac.metamac.notices.core.dto.NoticeDto;
import org.siemac.metamac.notices.core.notice.enume.domain.NoticeType;
import org.siemac.metamac.notices.web.client.model.ds.NoticeDS;
import org.siemac.metamac.notices.web.client.utils.AccessControlValues;
import org.siemac.metamac.notices.web.client.utils.CommonUtils;
import org.siemac.metamac.notices.web.client.utils.RecordUtils;
import org.siemac.metamac.web.common.client.utils.DateUtils;
import org.siemac.metamac.web.common.client.utils.ExternalItemUtils;
import org.siemac.metamac.web.common.client.utils.FormItemUtils;
import org.siemac.metamac.web.common.client.widgets.form.MainFormLayout;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewTextItem;

import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

public class NoticeLayout extends VLayout {

    private MainFormLayout   mainFormLayout;
    private DynamicForm      form;
    private Canvas           messageHtmlCanvas;
    private NoticesToolStrip noticeToolStrip;
   
    public NoticeLayout() {
        setMargin(10);
        setMembersMargin(10);
        createMainFormLayout();
        setVisible(false);
    }

    public void setNotice(NoticeDto notice) {
        clearValues();

        mainFormLayout.setTitleLabelContents(notice.getSubject());

        form.setValue(NoticeDS.URN, notice.getUrn());
        form.setValue(NoticeDS.SENDING_APPLICATION, AccessControlValues.getAppTitle(notice.getSendingApplication()));
        form.setValue(NoticeDS.SENDING_USER, notice.getSendingUser());
        form.setValue(NoticeDS.EXPIRATION_DATE, DateUtils.getFormattedDate(notice.getExpirationDate()));
        form.setValue(NoticeDS.TYPE, CommonUtils.getNoticeTypeName(notice.getType()));
        form.setValue(NoticeDS.TYPE_ENUM, notice.getType().name());
        form.setValue(NoticeDS.CREATION_DATE, DateUtils.getFormattedDateTime(notice.getCreationDate()));
        setMessage(notice);

        form.markForRedraw();
        
        updateToolStripButtonsVisibility(notice);
        show();
    }

    public String getNoticeUrn() {
        return form.getValueAsString(NoticeDS.URN);
    }

    private void setMessage(NoticeDto notice) {
        MessageDto messageDto = notice.getMessages().isEmpty() ? null : notice.getMessages().get(0);
        if (messageDto != null) {
            String message = buildMessage(messageDto);
            messageHtmlCanvas.setContents(message);
        }
    }

    private void createMainFormLayout() {
        mainFormLayout = new MainFormLayout(false, false);
        createForm();
        mainFormLayout.addViewCanvas(form);
        
        
        extendToolStrip(mainFormLayout.getToolStrip());

        messageHtmlCanvas = new Canvas();
        messageHtmlCanvas.setOverflow(Overflow.VISIBLE);
        messageHtmlCanvas.setAutoHeight();
        messageHtmlCanvas.setPadding(2);
        messageHtmlCanvas.setCanSelectText(true);
        mainFormLayout.addViewCanvas(messageHtmlCanvas);

        addMember(mainFormLayout);
    }

    private void extendToolStrip(ToolStrip toolStrip) {        
        noticeToolStrip = new NoticesToolStrip();
        toolStrip.addChild(noticeToolStrip);
    }

    private void createForm() {
        form = new DynamicForm();
        ViewTextItem urnItem = new ViewTextItem(NoticeDS.URN, getConstants().noticeURN());

        ViewTextItem sendingApplicationItem = new ViewTextItem(NoticeDS.SENDING_APPLICATION, getConstants().noticeSendingApplication());
        sendingApplicationItem.setShowIfCondition(getNotificationItemFormItemIfFunction());

        ViewTextItem sendingUserItem = new ViewTextItem(NoticeDS.SENDING_USER, getConstants().noticeSendingUser());
        sendingUserItem.setShowIfCondition(getAnnouncementItemFormItemIfFunction());

        ViewTextItem expirationDateItem = new ViewTextItem(NoticeDS.EXPIRATION_DATE, getConstants().noticeExpirationDate());
        expirationDateItem.setShowIfCondition(getAnnouncementItemFormItemIfFunction());

        ViewTextItem typeItem = new ViewTextItem(NoticeDS.TYPE, getConstants().noticeType());

        ViewTextItem typeEnumItem = new ViewTextItem(NoticeDS.TYPE_ENUM, getConstants().noticeType());
        typeEnumItem.setShowIfCondition(FormItemUtils.getFalseFormItemIfFunction());

        ViewTextItem creationDate = new ViewTextItem(NoticeDS.CREATION_DATE, getConstants().noticeCreationDate());

        form.setFields(sendingApplicationItem, sendingUserItem, expirationDateItem, typeItem, typeEnumItem, creationDate, urnItem);
    }

    private void clearValues() {
        form.clearValues();
        messageHtmlCanvas.setContents(StringUtils.EMPTY);
    }

    private FormItemIfFunction getNotificationItemFormItemIfFunction() {
        return new FormItemIfFunction() {

            @Override
            public boolean execute(FormItem item, Object value, DynamicForm form) {
                NoticeType type = CommonUtils.getNoticeType(form.getValueAsString(NoticeDS.TYPE_ENUM));
                return NoticeType.NOTIFICATION.equals(type);
            }
        };
    }

    private FormItemIfFunction getAnnouncementItemFormItemIfFunction() {
        return new FormItemIfFunction() {

            @Override
            public boolean execute(FormItem item, Object value, DynamicForm form) {
                NoticeType type = CommonUtils.getNoticeType(form.getValueAsString(NoticeDS.TYPE_ENUM));
                return NoticeType.ANNOUNCEMENT.equals(type);
            }
        };
    }

    private String buildMessage(MessageDto messageDto) {
        StringBuilder result = new StringBuilder();
        result.append("<p>").append(messageDto.getText()).append("</p>");
        result.append("<p>").append("<ul>");
        for (ExternalItemDto externalItemDto : messageDto.getResources()) {
            result.append("<li>");
            result.append("<a href=\"").append(externalItemDto.getManagementAppUrl()).append("\">");
            result.append(ExternalItemUtils.getExternalItemName(externalItemDto));
            result.append("</a>");
            result.append("</li>");
        }
        result.append("</u>").append("</p>");
        return result.toString();
    }
    
    private void updateToolStripButtonsVisibility(NoticeDto notice) {
        getNoticeToolStrip().hideButtons();
        boolean noticeRead = RecordUtils.getNoticeRecord(notice).getReceiverAcknowledge();
        if (noticeRead) {
            getNoticeToolStrip().showMarkAsUnreadButton();
        } else {
            getNoticeToolStrip().showMarkAsReadButton();
        }
    }
    
    public NoticesToolStrip getNoticeToolStrip() {
        return noticeToolStrip;
    }

}
