package org.siemac.metamac.notices.web.client.widgets;

import static org.siemac.metamac.notices.web.client.NoticesWeb.getConstants;

import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.notices.web.client.model.ds.NoticeDS;
import org.siemac.metamac.notices.web.client.utils.CommonUtils;
import org.siemac.metamac.notices.web.client.view.handlers.NoticesUiHandlers;
import org.siemac.metamac.notices.web.shared.criteria.NoticeWebCriteria;
import org.siemac.metamac.web.common.client.MetamacWebCommon;
import org.siemac.metamac.web.common.client.widgets.BaseAdvancedSearchSectionStack;
import org.siemac.metamac.web.common.client.widgets.form.GroupDynamicForm;
import org.siemac.metamac.web.common.client.widgets.form.fields.BooleanSelectItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.CustomButtonItem;

import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;

public class NoticesSearchSectionStack extends BaseAdvancedSearchSectionStack {

    private NoticesUiHandlers uiHandlers;

    @Override
    protected void createAdvancedSearchForm() {
        advancedSearchForm = new GroupDynamicForm(StringUtils.EMPTY);
        advancedSearchForm.setPadding(5);
        advancedSearchForm.setMargin(5);
        advancedSearchForm.setVisible(false);

        SelectItem sendingApplication = new SelectItem(NoticeDS.SENDING_APPLICATION, getConstants().noticeSendingApplication());
        sendingApplication.setValueMap(CommonUtils.getAppLinkedHashMap());
        TextItem sendingUser = new TextItem(NoticeDS.SENDING_USER, getConstants().noticeSendingUser());
        SelectItem type = new SelectItem(NoticeDS.TYPE, getConstants().noticeType());
        type.setValueMap(CommonUtils.getNoticeTypeLinkedHashMap());
        BooleanSelectItem acknowledge = new BooleanSelectItem(NoticeDS.RECEIVER_ACKNOWLEDGE, getConstants().noticeReceiverAcknowledge());
        acknowledge.setWidth(50);

        CustomButtonItem searchItem = new CustomButtonItem(ADVANCED_SEARCH_ITEM_NAME, MetamacWebCommon.getConstants().search());
        searchItem.setColSpan(4);
        searchItem.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                retrieveResources();
            }
        });

        FormItem[] advancedSearchFormItems = new FormItem[]{sendingApplication, sendingUser, type, acknowledge, searchItem};
        setFormItemsInAdvancedSearchForm(advancedSearchFormItems);
    }

    @Override
    protected void retrieveResources() {
        getUiHandlers().retrieveNotices(getNoticeWebCriteria());
    }

    public NoticeWebCriteria getNoticeWebCriteria() {
        NoticeWebCriteria noticeWebCriteria = new NoticeWebCriteria();
        noticeWebCriteria.setCriteria(searchForm.getValueAsString(SEARCH_ITEM_NAME));
        noticeWebCriteria.setSendingApplication(advancedSearchForm.getValueAsString(NoticeDS.SENDING_APPLICATION));
        noticeWebCriteria.setSendingUser(advancedSearchForm.getValueAsString(NoticeDS.SENDING_USER));
        noticeWebCriteria.setType(CommonUtils.getNoticeType(advancedSearchForm.getValueAsString(NoticeDS.TYPE)));
        noticeWebCriteria.setAcknowledge(((BooleanSelectItem) advancedSearchForm.getItem(NoticeDS.RECEIVER_ACKNOWLEDGE)).getBooleanValue());
        return noticeWebCriteria;
    }

    public NoticesUiHandlers getUiHandlers() {
        return uiHandlers;
    }

    public void setUiHandlers(NoticesUiHandlers uiHandlers) {
        this.uiHandlers = uiHandlers;
    }
}
