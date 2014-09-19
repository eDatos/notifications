package org.siemac.metamac.notices.web.client.widgets;

import static org.siemac.metamac.notices.web.client.NoticesWeb.getConstants;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.notices.web.shared.dto.AccessControlValueDto;
import org.siemac.metamac.web.common.client.MetamacWebCommon;
import org.siemac.metamac.web.common.client.widgets.BaseSearchWindow;
import org.siemac.metamac.web.common.client.widgets.CustomListGridField;
import org.siemac.metamac.web.common.client.widgets.form.fields.DragAndDropItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.external.ExternalItemListItem;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemIconClickEvent;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public abstract class SearchAccessControlValueItem extends ExternalItemListItem {

    protected static final int                FORM_ITEM_CUSTOM_WIDTH = 1000;

    protected SearchAccessControlValuesWindow searchWindow;

    public SearchAccessControlValueItem(final String name, final String title) {
        super(name, title, true);

        CustomListGridField valueField = new CustomListGridField(AccessControlValueDS.TITLE, getConstants().value());
        listGrid.setFields(valueField);

        getSearchIcon().addFormItemClickHandler(new FormItemClickHandler() {

            @Override
            public void onFormItemClick(FormItemIconClickEvent event) {
                searchWindow = new SearchAccessControlValuesWindow(name, title);
                searchWindow.resetValues();
                searchWindow.setSourceDtos(getSourceDtosWihtoutSelectedTypes());
                searchWindow.setTargetDtos(getSelectedDtos());
                searchWindow.getSaveButton().addClickHandler(new ClickHandler() {

                    @Override
                    public void onClick(ClickEvent event) {
                        List<AccessControlValueDto> selectedTypeDtos = searchWindow.getSelectedDtos();
                        searchWindow.markForDestroy();

                        setAccessControlValues(selectedTypeDtos);
                        SearchAccessControlValueItem.this.validate();
                    }
                });
            }
        });
    }

    private List<AccessControlValueDto> getSourceDtosWihtoutSelectedTypes() {
        List<AccessControlValueDto> sourceTypeDtosWihtoutSelectedTypes = new ArrayList<AccessControlValueDto>();
        List<AccessControlValueDto> selectedTypeDtos = getSelectedDtos();
        for (AccessControlValueDto typeDto : getAccessControlValueDtos()) {
            if (!isTypeInList(typeDto, selectedTypeDtos)) {
                sourceTypeDtosWihtoutSelectedTypes.add(typeDto);
            }
        }
        return sourceTypeDtosWihtoutSelectedTypes;
    }

    private boolean isTypeInList(AccessControlValueDto dto, List<AccessControlValueDto> dtos) {
        for (AccessControlValueDto type : dtos) {
            if (dto.getCode() != null && type.getCode() != null) {
                if (StringUtils.equals(dto.getCode(), type.getCode())) {
                    return true;
                }
            }
        }
        return false;
    }

    public void setAccessControlValues(List<AccessControlValueDto> dtos) {
        AccessControlValueRecord[] records = getAccessControlValueRecords(dtos);
        getListGrid().setData(records);
    }

    public List<AccessControlValueDto> getSelectedDtos() {
        List<AccessControlValueDto> dtos = new ArrayList<AccessControlValueDto>();
        ListGridRecord[] records = getListGrid().getRecords();
        for (ListGridRecord record : records) {
            dtos.add(((AccessControlValueRecord) record).getAccessControlValueDto());
        }
        return dtos;
    }

    private class SearchAccessControlValuesWindow extends BaseSearchWindow {

        protected AccessControlValuesDragAndDropItem dragAndDropItem;
        protected ButtonItem                         saveItem;

        public SearchAccessControlValuesWindow(String name, String title) {
            super(title);
            setWidth(FORM_ITEM_CUSTOM_WIDTH);

            dragAndDropItem = new AccessControlValuesDragAndDropItem(name, "", name);
            dragAndDropItem.setShowTitle(false);

            saveItem = new ButtonItem("save-button", MetamacWebCommon.getConstants().actionSave());
            saveItem.setAlign(Alignment.CENTER);

            getForm().setFields(dragAndDropItem, saveItem);
        }

        public void setSourceDtos(List<AccessControlValueDto> dtos) {
            dragAndDropItem.setSourceDtos(dtos);
        }

        public void setTargetDtos(List<AccessControlValueDto> dtos) {
            dragAndDropItem.setTargetDtos(dtos);
        }

        public void resetValues() {
            dragAndDropItem.resetValues();
        }

        public ButtonItem getSaveButton() {
            return saveItem;
        }

        public List<AccessControlValueDto> getSelectedDtos() {
            return dragAndDropItem.getSelectedDtos();
        }
    }

    private class AccessControlValuesDragAndDropItem extends DragAndDropItem {

        public AccessControlValuesDragAndDropItem(String name, String title, String dragDropType) {
            super(name, title);

            CustomListGridField titleField = new CustomListGridField(AccessControlValueDS.TITLE, getConstants().value());

            sourceList.setHeight(250);
            sourceList.setWidth(FORM_ITEM_CUSTOM_WIDTH / 2);
            sourceList.setDataSource(new AccessControlValueDS());
            sourceList.setUseAllDataSourceFields(false);
            sourceList.setFields(titleField);

            targetList.setHeight(250);
            targetList.setWidth(FORM_ITEM_CUSTOM_WIDTH / 2);
            targetList.setDataSource(new AccessControlValueDS());
            targetList.setUseAllDataSourceFields(false);
            targetList.setFields(titleField);
        }

        public void setSourceDtos(List<AccessControlValueDto> dtos) {
            AccessControlValueRecord[] records = getAccessControlValueRecords(dtos);
            sourceList.setData(records);
        }

        public void setTargetDtos(List<AccessControlValueDto> dtos) {
            AccessControlValueRecord[] records = getAccessControlValueRecords(dtos);
            targetList.setData(records);
        }

        public List<AccessControlValueDto> getSelectedDtos() {
            List<AccessControlValueDto> selectedItems = new ArrayList<AccessControlValueDto>();
            ListGridRecord[] records = targetList.getRecords();
            for (int i = 0; i < records.length; i++) {
                AccessControlValueRecord record = (AccessControlValueRecord) records[i];
                selectedItems.add(record.getAccessControlValueDto());
            }
            return selectedItems;
        }

        public void resetValues() {
            clearValue();
        }
    }

    private AccessControlValueRecord[] getAccessControlValueRecords(List<AccessControlValueDto> values) {
        AccessControlValueRecord[] records = new AccessControlValueRecord[values.size()];
        for (int i = 0; i < values.size(); i++) {
            AccessControlValueRecord record = new AccessControlValueRecord();
            record.setCode(values.get(i).getCode());
            record.setTitle(values.get(i).getTitle());
            record.setDescription(values.get(i).getDescription());
            record.setAccessControlValueDto(values.get(i));
            records[i] = record;
        }
        return records;
    }

    private class AccessControlValueDS extends DataSource {

        public static final String CODE        = "acl-code";
        public static final String TITLE       = "acl-title";
        public static final String DESCRIPTION = "acl-description";
        public static final String DTO         = "acl-dto";
    }

    private class AccessControlValueRecord extends ListGridRecord {

        public void setCode(String value) {
            setAttribute(AccessControlValueDS.CODE, value);
        }

        public void setTitle(String value) {
            setAttribute(AccessControlValueDS.TITLE, value);
        }

        public void setDescription(String value) {
            setAttribute(AccessControlValueDS.DESCRIPTION, value);
        }

        public void setAccessControlValueDto(AccessControlValueDto value) {
            setAttribute(AccessControlValueDS.DTO, value);
        }

        public AccessControlValueDto getAccessControlValueDto() {
            return (AccessControlValueDto) getAttributeAsObject(AccessControlValueDS.DTO);
        }
    }

    public abstract List<AccessControlValueDto> getAccessControlValueDtos();
}
