package kaitou.ppp.app.ui.table.queryobject;

import kaitou.ppp.common.log.BaseLogManager;
import kaitou.ppp.domain.BaseDomain;
import org.apache.commons.lang.StringUtils;

import javax.swing.*;
import java.lang.reflect.ParameterizedType;

import static com.womai.bsp.tool.utils.PropertyUtil.getValue;

/**
 * 查询实体父类.
 * User: 赵立伟
 * Date: 2015/2/28
 * Time: 14:27
 */
public abstract class BaseQueryObject<T> implements IQueryObject {

    private static final String SCHEME_PROPERTIES = "scheme.properties";

    @Override
    public String title() {
        return getValue(SCHEME_PROPERTIES, domainType() + "_TITLE");
    }

    @Override
    public String[] tableTitles() {
        return getValue(SCHEME_PROPERTIES, domainType() + "_TABLE_TITLES").split(",");
    }

    @Override
    public String[] fieldNames() {
        return getValue(SCHEME_PROPERTIES, domainType() + "_FIELD_NAMES").split(",");
    }

    @Override
    public String[] queryFieldNames() {
        return getValue(SCHEME_PROPERTIES, domainType() + "_QUERY_FIELDS").split(",");
    }

    @Override
    public String domainType() {
        return domainClass().getSimpleName();
    }

    @Override
    public String[] opNames() {
        return getValue(SCHEME_PROPERTIES, domainType() + "_OP_NAMES").split(",");
    }

    @Override
    public String opFieldName() {
        return getValue(SCHEME_PROPERTIES, domainType() + "_OP_FIELD_NAME");
    }

    @Override
    public int editableColumnStartIndex() {
        return Integer.valueOf(getValue(SCHEME_PROPERTIES, domainType() + "_EDITABLE_COLUMN_START_INDEX"));
    }

    @Override
    public Integer[] editableColumnIndex() {
        String editableColumnIndex = getValue(SCHEME_PROPERTIES, domainType() + "_EDITABLE_COLUMN_INDEX");
        if (StringUtils.isEmpty(editableColumnIndex)) {
            return new Integer[0];
        }
        String[] editableColumnIndexes = editableColumnIndex.split(",");
        Integer[] result = new Integer[editableColumnIndexes.length];
        for (int i = 0; i < editableColumnIndexes.length; i++) {
            try {
                result[i] = Integer.valueOf(editableColumnIndexes[i]);
            } catch (NumberFormatException e) {
                BaseLogManager.logSystemEx(e);
            }
        }
        return result;
    }

    @Override
    public String[] saveTitles() {
        return getValue(SCHEME_PROPERTIES, domainType() + "_SAVE_TITLES").split(",");
    }

    @Override
    public String[] saveFieldNames() {
        return getValue(SCHEME_PROPERTIES, domainType() + "_SAVE_FIELD_NAMES").split(",");
    }

    @Override
    public boolean autoResizeMode() {
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Class domainClass() {
        return (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public boolean tableBtnEvent(JFrame frame, BaseDomain domain) {
        return true;
    }
}
