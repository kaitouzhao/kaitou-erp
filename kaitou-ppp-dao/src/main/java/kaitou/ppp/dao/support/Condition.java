package kaitou.ppp.dao.support;

/**
 * 单个查询条件对象.
 * User: 赵立伟
 * Date: 2015/6/1
 * Time: 15:47
 */
public class Condition {
    /**
     * 查询属性名
     */
    private String fieldName;
    /**
     * 查询属性值
     */
    private Object fieldValue;

    public Condition(String fieldName, Object fieldValue) {
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    @Override
    public String toString() {
        return "Condition{" +
                "fieldName='" + fieldName + '\'' +
                ", fieldValue=" + fieldValue +
                '}';
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Object getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(Object fieldValue) {
        this.fieldValue = fieldValue;
    }
}
