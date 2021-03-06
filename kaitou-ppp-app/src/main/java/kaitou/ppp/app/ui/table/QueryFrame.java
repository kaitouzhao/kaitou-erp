/*
 * Created by JFormDesigner on Thu Feb 05 15:42:34 CST 2015
 */

package kaitou.ppp.app.ui.table;

import com.womai.bsp.tool.utils.CollectionUtil;
import kaitou.ppp.app.ui.dialog.BaseSaveDialog;
import kaitou.ppp.app.ui.dialog.ConfirmHint;
import kaitou.ppp.app.ui.dialog.OperationHint;
import kaitou.ppp.app.ui.table.queryobject.IQueryObject;
import kaitou.ppp.domain.BaseDomain;
import kaitou.ppp.domain.BaseDomain4InDoubt;
import org.apache.commons.lang.StringUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.*;
import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static kaitou.ppp.app.ui.UIUtil.chooseExportFile;
import static kaitou.ppp.app.ui.UIUtil.handleEx;
import static kaitou.ppp.app.ui.table.OPManager.*;
import static kaitou.ppp.common.utils.ReflectionUtil.getFieldValue;
import static kaitou.ppp.common.utils.ReflectionUtil.setFieldValue;

/**
 * 查询界面
 *
 * @author 赵立伟
 */
public class QueryFrame<T extends BaseDomain> extends JFrame {
    /**
     * 操作列标题
     */
    protected static final String OP_COLUMN_TITLE = "操作";
    /**
     * 选择列号
     */
    protected static final int SELECT_COLUMN_INDEX = 0;
    /**
     * 数据开始列号
     */
    protected static final int DATA_COLUMN_START_INDEX = 1;
    /**
     * 存疑筛选名
     */
    protected static final String CHOOSE_IN_DOUBT_LABEL = "存疑";
    /**
     * 当前frame
     */
    protected QueryFrame self = this;
    /**
     * 查询区域输入框动态集合
     */
    protected List<JTextField> queryTextFields = new ArrayList<JTextField>();
    /**
     * 数据源
     */
    protected List<T> datas;
    /**
     * 显示的数据
     */
    protected List<T> shownDatas = new ArrayList<T>();
    /**
     * 当前页码
     */
    protected int currentPageIndex = 1;
    /**
     * 每页显示条数
     */
    protected int countPerPage = 25;
    /**
     * 总页数
     */
    protected int pageCount;
    /**
     * 总记录数
     */
    protected int recordCount;
    /**
     * 查询对象
     */
    protected IQueryObject<T> queryObject;
    /**
     * 操作列号
     */
    protected int opColumnIndex = -1;
    /**
     * 筛选存疑
     */
    protected JCheckBox selectInDoubt;

    /**
     * 构造器
     * <ul>
     * <li>参数赋值</li>
     * <li>初始化表格参数</li>
     * <li>初始化界面</li>
     * </ul>
     *
     * @param datas       数据源
     * @param queryObject 查询对象接口。提供查询所需参数
     */
    @SuppressWarnings(value = "unchecked")
    public QueryFrame(List<T> datas, IQueryObject<T> queryObject) {
        this.queryObject = queryObject;
        String[] tableTitles = this.queryObject.tableTitles();
        if (OP_COLUMN_TITLE.equals(tableTitles[tableTitles.length - 1])) {
            opColumnIndex = tableTitles.length - 1;
        }
        this.datas = datas;
        shownDatas.addAll(datas);
        initComponents();
        initTableData();
        select();
        initQueryArea();
        setTitle(queryObject.title());
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }

    public QueryFrame(IQueryObject<T> queryObject) {
        this.queryObject = queryObject;
        String[] tableTitles = this.queryObject.tableTitles();
        if (OP_COLUMN_TITLE.equals(tableTitles[tableTitles.length - 1])) {
            opColumnIndex = tableTitles.length - 1;
        }
        this.datas = query(this.queryObject.domainType());
        shownDatas.addAll(datas);
        initComponents();
        initTableData();
        select();
        initQueryArea();
        setTitle(queryObject.title());
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }

    /**
     * 初始化表格参数
     */
    @SuppressWarnings(value = "unchecked")
    protected void initTableData() {
        recordCount = shownDatas.size();
        if (recordCount % countPerPage == 0) {
            pageCount = recordCount / countPerPage;
        } else {
            pageCount = (recordCount / countPerPage) + 1;
        }
        recordCountShow.setText("总计：" + recordCount);
        selectPage.removeAllItems();
        for (int i = 0; i < pageCount; i++) {
            selectPage.insertItemAt(i + 1, i);
        }
    }

    /**
     * 下一页
     */
    @SuppressWarnings(value = "unchecked")
    protected void nextPage() {
        if (currentPageIndex < pageCount) {
            currentPageIndex++;
        }
        select();
    }

    /**
     * 上一页
     */
    protected void previousPage() {
        if (currentPageIndex > 1) {
            currentPageIndex--;
        }
        select();
    }

    /**
     * 选择
     */
    @SuppressWarnings(value = "unchecked")
    protected void select() {
        List<T> currentList = new ArrayList<T>();
        for (int i = (currentPageIndex - 1) * countPerPage; i < currentPageIndex * countPerPage && i < recordCount; i++) {
            currentList.add(shownDatas.get(i));
        }
        view(currentList);
    }

    /**
     * 显示
     *
     * @param list 当前显示列表
     */
    protected void view(List<? extends BaseDomain> list) {
        String[] fieldNames = queryObject.fieldNames();
        int fieldNamesSize = fieldNames.length + 1;
        Object[][] objects = new Object[(list.size())][fieldNamesSize];
        for (int i = 0; i < objects.length; i++) {
            List<Object> oneRowList = CollectionUtil.newList(list.get(i).export2Array(fieldNames));
            oneRowList.add(SELECT_COLUMN_INDEX, new Boolean(false));
            objects[i] = CollectionUtil.toArray(oneRowList, Object.class);
        }
        String[] tableTitles = queryObject.tableTitles();
        dataTable.setModel(new QueryTable(objects,
                tableTitles));
        if (!queryObject.autoResizeMode()) {
            dataTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        }
        for (int index = DATA_COLUMN_START_INDEX; index < fieldNamesSize; index++) {
            dataTable.getColumnModel().getColumn(index).setCellRenderer(new QueryTableColorRenderer());
        }
        if (opColumnIndex < 0) {
            return;
        }
        dataTable.getColumnModel().getColumn(opColumnIndex).setCellEditor(new QueryTableBtnCellEditor());
        dataTable.getColumnModel().getColumn(opColumnIndex).setCellRenderer(new QueryTableBtnCellEditor());
    }

    /**
     * 获取已选中的行号列表
     *
     * @return 行号列表
     */
    protected List<Integer> getSelectedRows() {
        List<Integer> selectedRows = new ArrayList<Integer>();
        for (int row = 0; row < dataTable.getRowCount(); row++) {
            if (dataTable.getValueAt(row, SELECT_COLUMN_INDEX) == Boolean.TRUE) {
                selectedRows.add(row);
            }
        }
        return selectedRows;
    }

    protected void firstPageBtnActionPerformed(ActionEvent e) {
        try {
            currentPageIndex = 1;
            select();
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    protected void previousPageBtnActionPerformed(ActionEvent e) {
        try {
            previousPage();
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    protected void nextPageBtnActionPerformed(ActionEvent e) {
        try {
            nextPage();
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    protected void thisWindowClosed(WindowEvent e) {
        setVisible(false);
        // TODO 待优化
//        datas = null;
//        shownDatas = null;
//        dataTable = null;
//        dispose();
    }

    @SuppressWarnings(value = "unchecked")
    protected void queryBtnActionPerformed(ActionEvent e) {
        queryByCondition();
    }

    /**
     * 根据查询条件筛选
     */
    protected void queryByCondition() {
        try {
            boolean pickInDoubt = selectInDoubt != null ? selectInDoubt.isSelected() : false;
            shownDatas.clear();
            if (!CollectionUtil.isEmpty(datas) && !CollectionUtil.isEmpty(queryTextFields)) {
                List<Integer> excludes = new ArrayList<Integer>();
                JTextField textField;
                String textFieldValue;
                String[] queryFieldNames = queryObject.queryFieldNames();
                for (int i = 0; i < datas.size(); i++) {
                    T data = datas.get(i);
                    if (pickInDoubt && data instanceof BaseDomain4InDoubt) {
                        if (!((BaseDomain4InDoubt) data).isInDoubt()) {
                            excludes.add(i);
                            continue;
                        }
                    }
                    for (int j = 0; j < queryTextFields.size(); j++) {
                        textField = queryTextFields.get(j);
                        textFieldValue = StringUtils.isEmpty(textField.getText()) ? "" : textField.getText().trim();
                        if (StringUtils.isEmpty(textFieldValue)) {
                            continue;
                        }
                        Object fieldValue = getFieldValue(queryFieldNames[j], data);
                        if (fieldValue == null || fieldValue.toString().trim().equals("")) {
                            excludes.add(i);
                            break;
                        }
                        String fieldValueStr = fieldValue.toString();
                        if (!fieldValueStr.toLowerCase().contains(textFieldValue.toLowerCase().trim())) {
                            excludes.add(i);
                            break;
                        }
                    }
                }
                for (int i = 0; i < datas.size(); i++) {
                    if (excludes.contains(i)) {
                        continue;
                    }
                    shownDatas.add(datas.get(i));
                }
            } else {
                shownDatas.addAll(datas);
            }
            initTableData();
            currentPageIndex = 1;
            select();
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    protected void selectPageActionPerformed(ActionEvent e) {
        // DO NOTHING
    }

    protected void selectPageItemStateChanged(ItemEvent e) {
        try {
            currentPageIndex = Integer.valueOf(e.getItem().toString());
            select();
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    @SuppressWarnings(value = "unchecked")
    protected void resetBtnActionPerformed(ActionEvent e) {
        try {
            for (JTextField queryTextField : queryTextFields) {
                queryTextField.setText("");
            }
            if (selectInDoubt != null) {
                selectInDoubt.setSelected(false);
            }
            shownDatas.addAll(datas);
            queryByCondition();
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    protected void dataTableMouseClicked(MouseEvent e) {
        if (e.getButton() != 3) {
            return;
        }
        T t = shownDatas.get(getShownDataIndex(dataTable.getSelectedRow()));
        Object[] datas = t.export2Array(queryObject.fieldNames());
        Object data = datas[dataTable.getSelectedColumn() - DATA_COLUMN_START_INDEX];
        String copyData = String.valueOf(data == null ? "" : data);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection text = new StringSelection(copyData);
        clipboard.setContents(text, null);
        new OperationHint(this, "已复制到剪切板上");
    }

    protected void deleteBtnActionPerformed(ActionEvent e) {
        try {
            List<Integer> selectedRows = getSelectedRows();
            if (selectedRows.size() <= 0) {
                new OperationHint(this, "请先选择要删除的记录");
                return;
            }
            ConfirmHint hint = new ConfirmHint(this, "是否确认删除这些记录？");
            if (!hint.isOk()) {
                return;
            }
            final Object[] deleted = new Object[selectedRows.size()];
            List<Integer> deletedIndexes = new ArrayList<Integer>();
            for (int i = 0; i < selectedRows.size(); i++) {
                int index = getShownDataIndex(selectedRows.get(i));
                deleted[i] = shownDatas.get(index);
                deletedIndexes.add(index);
            }
            for (Integer deletedIndex : deletedIndexes) {
                datas.remove(shownDatas.get(deletedIndex));
            }
            doRunWithWaiting(self, new OpRunnable() {
                @Override
                public void run() {
                    delete(queryObject.domainType(), deleted);
                }
            }, "删除成功！");
            queryByCondition();
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    protected void saveBtnActionPerformed(ActionEvent e) {
        try {
            BaseSaveDialog<T> saveDialog = wannaSave(queryObject, self);
            if (!saveDialog.isOk() || saveDialog.getDomain() == null) {
                return;
            }
            datas.add(saveDialog.getDomain());
            new OperationHint(self, "添加成功");
            queryByCondition();
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    /**
     * 自定义table
     *
     * @see javax.swing.table.DefaultTableModel
     */
    protected class QueryTable extends DefaultTableModel {
        public QueryTable(Object[][] data, Object[] columnNames) {
            super(data, columnNames);
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            Integer[] editableColumnIndexes = queryObject.editableColumnIndex();
            if (CollectionUtil.isNotEmpty(editableColumnIndexes)) {
                for (Integer editableColumnIndex : editableColumnIndexes) {
                    if (column == editableColumnIndex) {
                        return true;
                    }
                }
            }
            int editableColumnStartIndex = queryObject.editableColumnStartIndex();
            return (editableColumnStartIndex >= DATA_COLUMN_START_INDEX && column >= editableColumnStartIndex) || column == opColumnIndex || column == SELECT_COLUMN_INDEX;
        }

        @Override
        @SuppressWarnings("unchecked")
        public void fireTableCellUpdated(int row, int column) {
            if (column == SELECT_COLUMN_INDEX) {
                return;
            }
            super.fireTableCellUpdated(row, column);
            ConfirmHint hint = new ConfirmHint(self, "是否确定更新这行记录？");
            if (!hint.isOk()) {
                select();
                return;
            }
            Object edited = shownDatas.get(getShownDataIndex(row));
            final Object dataObj = datas.get(datas.indexOf(edited));
            String fieldName = String.valueOf(Array.get(queryObject.fieldNames(), column - 1));
            String fieldValue = String.valueOf(dataTable.getValueAt(row, column));
            setFieldValue(fieldName, edited, fieldValue);
            setFieldValue(fieldName, dataObj, fieldValue);
            doRunWithWaiting(self, new OpRunnable() {
                @Override
                public void run() {
                    saveOrUpdate(queryObject.domainType(), CollectionUtil.toArray(CollectionUtil.newList(dataObj), queryObject.domainClass()));
                }
            }, "更新成功");
            select();
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            if (getColumnName(columnIndex).equals("选择")) {
                return Boolean.class;
            }
            return super.getColumnClass(columnIndex);
        }
    }

    /**
     * 自定义table行色渲染
     */
    protected class QueryTableColorRenderer extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            T t = shownDatas.get(getShownDataIndex(row));
            if (t instanceof BaseDomain4InDoubt) {
                if (((BaseDomain4InDoubt) t).isInDoubt()) {
                    component.setBackground(Color.yellow);
                } else {
                    component.setBackground(t.tableRowColor());
                }
            }
            return component;
        }
    }

    /**
     * 自定义table按钮列编辑器
     */
    protected class QueryTableBtnCellEditor extends AbstractCellEditor implements TableCellEditor, TableCellRenderer, ActionListener {

        private JButton btn = null;

        private QueryTableBtnCellEditor() {
            btn = new JButton("此处有操作");
            btn.addActionListener(this);
        }

        @Override
        @SuppressWarnings("unchecked")
        public void actionPerformed(ActionEvent e) {
            int[] opIndexes = dataTable.getSelectedRows();
            if (opIndexes.length <= 0) {
                return;
            }
            ConfirmHint hint = new ConfirmHint(self, "确定变更吗？");
            if (!hint.isOk()) {
                return;
            }
            Object shownObj = shownDatas.get(getShownDataIndex(opIndexes[0]));
            final Object dataObj = datas.get(datas.indexOf(shownObj));
            if (!queryObject.tableBtnEvent(self, (T) shownObj)) {
                return;
            }
            String btnText = btn.getText();
            String changeValue;
            String[] opNames = queryObject.opNames();
            if (btnText.equals(opNames[0])) {
                changeValue = opNames[0];
            } else {
                changeValue = opNames[1];
            }
            String opFieldName = queryObject.opFieldName();
            setFieldValue(opFieldName, shownObj, changeValue);
            setFieldValue(opFieldName, dataObj, changeValue);
            doRunWithWaiting(self, new OpRunnable() {
                @Override
                public void run() {
                    saveOrUpdate(queryObject.domainType(), CollectionUtil.toArray(CollectionUtil.newList(dataObj), queryObject.domainClass()));
                }
            }, "操作成功");
            select();
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            initBtn(row);
            return btn;
        }

        /**
         * 初始化按钮
         *
         * @param row 行数
         */
        private void initBtn(int row) {
            Object initFieldValue = getFieldValue(queryObject.opFieldName(), shownDatas.get(getShownDataIndex(row)));
            String[] opNames = queryObject.opNames();
            if (initFieldValue == null || opNames[1].equals(initFieldValue)) {
                btn.setText(opNames[0]);
                return;
            }
            btn.setText(opNames[1]);
        }

        @Override
        public Object getCellEditorValue() {
            return null;
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            initBtn(row);
            return btn;
        }
    }

    /**
     * 获取行号对应的显示数据位置
     *
     * @param tableRow 行号
     * @return 显示数据位置
     */
    protected int getShownDataIndex(int tableRow) {
        return tableRow + (currentPageIndex - 1) * countPerPage;
    }

    protected void exportBtnActionPerformed(ActionEvent e) {
        try {
            File targetFile = chooseExportFile("excel文件", "xlsx");
            if (targetFile == null) return;
            List<Integer> selectedRows = getSelectedRows();
            List exportList = new ArrayList();
            if (selectedRows.size() <= 0) {
                exportList.addAll(shownDatas);
            } else {
                for (int i = 0; i < selectedRows.size(); i++) {
                    int index = getShownDataIndex(selectedRows.get(i));
                    exportList.add(shownDatas.get(index));
                }
            }
            export(queryObject.domainType(), exportList, targetFile);
            new OperationHint(this, "导出成功");
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    protected void sendBtnActionPerformed(ActionEvent e) {
        try {
            List<Integer> selectedRows = getSelectedRows();
            if (selectedRows.size() <= 0) {
                new OperationHint(this, "请先选择要发送的记录");
                return;
            }
            List<T> sendList = new ArrayList<T>();
            for (int i = 0; i < selectedRows.size(); i++) {
                int index = getShownDataIndex(selectedRows.get(i));
                sendList.add(shownDatas.get(index));
            }
            saveOrUpdate(queryObject.domainType(), CollectionUtil.toArray(sendList, queryObject.domainClass()));
            new OperationHint(this, "发送成功");
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    protected void resultAreaMouseWheelMoved(MouseWheelEvent e) {
        if (e.getWheelRotation() < 0) {
            previousPage();
        } else {
            nextPage();
        }
    }

    protected void allSelectedBtnActionPerformed(ActionEvent e) {
        changeCheckboxStatus(Boolean.TRUE);
    }

    /**
     * 改变当前页全部复选框选择状态
     *
     * @param isSelected 是否选中
     */
    protected void changeCheckboxStatus(Boolean isSelected) {
        for (int row = 0; row < dataTable.getRowCount(); row++) {
            dataTable.setValueAt(isSelected, row, SELECT_COLUMN_INDEX);
        }
        dataTable.repaint();
    }

    protected void noSelectedBtnActionPerformed(ActionEvent e) {
        changeCheckboxStatus(Boolean.FALSE);
    }

    protected void downloadImportModelBtnActionPerformed(ActionEvent e) {
        doRunWithExHandler(self, new OpRunnable() {
            @Override
            public void run() {
                File targetFile = chooseExportFile("excel文件", "xlsx");
                if (targetFile == null) return;
                exportImportModel(queryObject.domainType(), targetFile);
                new OperationHint(self, "导出成功");
            }
        });
    }

    /**
     * 初始化界面
     */
    protected void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        resultArea = new JScrollPane();
        dataTable = new JTable();
        firstPageBtn = new JButton();
        previousPageBtn = new JButton();
        nextPageBtn = new JButton();
        recordCountShow = new JLabel();
        jump2Page = new JLabel();
        selectPage = new JComboBox();
        queryArea = new JPanel();
        queryBtn = new JButton();
        resetBtn = new JButton();
        deleteBtn = new JButton();
        saveBtn = new JButton();
        exportBtn = new JButton();
        sendBtn = new JButton();
        allSelectedBtn = new JButton();
        noSelectedBtn = new JButton();
        editableHint = new JLabel();
        downloadImportModelBtn = new JButton();

        //======== this ========
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                thisWindowClosed(e);
            }
        });
        Container contentPane = getContentPane();
        contentPane.setLayout(null);

        //======== resultArea ========
        {
            resultArea.setAutoscrolls(true);
            resultArea.addMouseWheelListener(new MouseWheelListener() {
                @Override
                public void mouseWheelMoved(MouseWheelEvent e) {
                    resultAreaMouseWheelMoved(e);
                }
            });

            //---- dataTable ----
            dataTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
            dataTable.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    dataTableMouseClicked(e);
                }
            });
            resultArea.setViewportView(dataTable);
        }
        contentPane.add(resultArea);
        resultArea.setBounds(0, 175, 1366, 455);

        //---- firstPageBtn ----
        firstPageBtn.setText("\u9996\u9875");
        firstPageBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                firstPageBtnActionPerformed(e);
            }
        });
        contentPane.add(firstPageBtn);
        firstPageBtn.setBounds(new Rectangle(new Point(305, 635), firstPageBtn.getPreferredSize()));

        //---- previousPageBtn ----
        previousPageBtn.setText("\u4e0a\u4e00\u9875");
        previousPageBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                previousPageBtnActionPerformed(e);
            }
        });
        contentPane.add(previousPageBtn);
        previousPageBtn.setBounds(new Rectangle(new Point(375, 635), previousPageBtn.getPreferredSize()));

        //---- nextPageBtn ----
        nextPageBtn.setText("\u4e0b\u4e00\u9875");
        nextPageBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nextPageBtnActionPerformed(e);
            }
        });
        contentPane.add(nextPageBtn);
        nextPageBtn.setBounds(new Rectangle(new Point(455, 635), nextPageBtn.getPreferredSize()));

        //---- recordCountShow ----
        recordCountShow.setText("\u6761\u6570\uff1a");
        contentPane.add(recordCountShow);
        recordCountShow.setBounds(5, 640, 120, recordCountShow.getPreferredSize().height);

        //---- jump2Page ----
        jump2Page.setText("\u8df3\u8f6c\u5230\uff1a");
        contentPane.add(jump2Page);
        jump2Page.setBounds(new Rectangle(new Point(155, 640), jump2Page.getPreferredSize()));

        //---- selectPage ----
        selectPage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectPageActionPerformed(e);
            }
        });
        selectPage.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                selectPageItemStateChanged(e);
            }
        });
        contentPane.add(selectPage);
        selectPage.setBounds(205, 635, 90, selectPage.getPreferredSize().height);

        //======== queryArea ========
        {
            queryArea.setAutoscrolls(true);
            queryArea.setLayout(new FlowLayout(FlowLayout.LEFT));
        }
        contentPane.add(queryArea);
        queryArea.setBounds(0, 5, 1366, 95);

        //---- queryBtn ----
        queryBtn.setText("\u68c0\u7d22");
        queryBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                queryBtnActionPerformed(e);
            }
        });
        contentPane.add(queryBtn);
        queryBtn.setBounds(new Rectangle(new Point(70, 110), queryBtn.getPreferredSize()));

        //---- resetBtn ----
        resetBtn.setText("\u91cd\u7f6e");
        resetBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetBtnActionPerformed(e);
            }
        });
        contentPane.add(resetBtn);
        resetBtn.setBounds(new Rectangle(new Point(5, 110), resetBtn.getPreferredSize()));

        //---- deleteBtn ----
        deleteBtn.setText("\u5220\u9664\u9009\u4e2d\u7684\u8bb0\u5f55");
        deleteBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteBtnActionPerformed(e);
            }
        });
        contentPane.add(deleteBtn);
        deleteBtn.setBounds(new Rectangle(new Point(765, 635), deleteBtn.getPreferredSize()));

        //---- saveBtn ----
        saveBtn.setText("\u6dfb\u52a0");
        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveBtnActionPerformed(e);
            }
        });
        contentPane.add(saveBtn);
        saveBtn.setBounds(new Rectangle(new Point(255, 110), saveBtn.getPreferredSize()));

        //---- exportBtn ----
        exportBtn.setText("\u5bfc\u51fa");
        exportBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportBtnActionPerformed(e);
            }
        });
        contentPane.add(exportBtn);
        exportBtn.setBounds(new Rectangle(new Point(190, 110), exportBtn.getPreferredSize()));

        //---- sendBtn ----
        sendBtn.setText("\u53d1\u9001\u6570\u636e\u7ed9\u4ed6\u4eba");
        sendBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendBtnActionPerformed(e);
            }
        });
        contentPane.add(sendBtn);
        sendBtn.setBounds(new Rectangle(new Point(635, 635), sendBtn.getPreferredSize()));

        //---- allSelectedBtn ----
        allSelectedBtn.setText("\u5168\u9009");
        allSelectedBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                allSelectedBtnActionPerformed(e);
            }
        });
        contentPane.add(allSelectedBtn);
        allSelectedBtn.setBounds(new Rectangle(new Point(5, 145), allSelectedBtn.getPreferredSize()));

        //---- noSelectedBtn ----
        noSelectedBtn.setText("\u53cd\u9009");
        noSelectedBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                noSelectedBtnActionPerformed(e);
            }
        });
        contentPane.add(noSelectedBtn);
        noSelectedBtn.setBounds(new Rectangle(new Point(70, 145), noSelectedBtn.getPreferredSize()));

        //---- editableHint ----
        editableHint.setText("*\u662f\u53ef\u4ee5\u4fee\u6539\u7684\u5217");
        editableHint.setForeground(Color.red);
        contentPane.add(editableHint);
        editableHint.setBounds(new Rectangle(new Point(195, 150), editableHint.getPreferredSize()));

        //---- downloadImportModelBtn ----
        downloadImportModelBtn.setText("\u5bfc\u5165\u6a21\u677f\u5411\u5bfc");
        downloadImportModelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                downloadImportModelBtnActionPerformed(e);
            }
        });
        contentPane.add(downloadImportModelBtn);
        downloadImportModelBtn.setBounds(new Rectangle(new Point(335, 110), downloadImportModelBtn.getPreferredSize()));

        contentPane.setPreferredSize(new Dimension(920, 695));
        setSize(920, 695);
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents

    }

    /**
     * 初始化查询区域
     */
    protected void initQueryArea() {
        String[] queryFieldNames = queryObject.queryFieldNames();
        if (CollectionUtil.isEmpty(queryFieldNames)) {
            return;
        }
        JLabel queryLabel;
        JTextField queryTextField;
        String queryLabelTitle = null;
        String[] fieldNames = queryObject.fieldNames();
        for (String queryFieldName : queryFieldNames) {
            for (int j = 0; j < fieldNames.length; j++) {
                if (queryFieldName.equals(fieldNames[j])) {
                    queryLabelTitle = queryObject.tableTitles()[DATA_COLUMN_START_INDEX + j];
                    break;
                }
            }
            if (StringUtils.isEmpty(queryLabelTitle)) {
                continue;
            }
            queryLabel = new JLabel(queryLabelTitle.replace("*", ""));
            queryArea.add(queryLabel);
            queryTextField = new JTextField(10);
            queryTextField.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {

                }

                @Override
                public void keyPressed(KeyEvent e) {
                    queryByCondition();
                }

                @Override
                public void keyReleased(KeyEvent e) {

                }
            });
            queryArea.add(queryTextField);
            queryTextFields.add(queryTextField);
        }
        if (queryObject.domainClass().getSuperclass() == BaseDomain4InDoubt.class) {
            selectInDoubt = new JCheckBox(CHOOSE_IN_DOUBT_LABEL);
            selectInDoubt.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    selectInDoubt.setSelected(e.getStateChange() == ItemEvent.SELECTED);
                    queryByCondition();
                }
            });
            queryArea.add(selectInDoubt);
        }
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    protected JScrollPane resultArea;
    protected JTable dataTable;
    protected JButton firstPageBtn;
    protected JButton previousPageBtn;
    protected JButton nextPageBtn;
    protected JLabel recordCountShow;
    protected JLabel jump2Page;
    protected JComboBox selectPage;
    protected JPanel queryArea;
    protected JButton queryBtn;
    protected JButton resetBtn;
    protected JButton deleteBtn;
    protected JButton saveBtn;
    protected JButton exportBtn;
    protected JButton sendBtn;
    protected JButton allSelectedBtn;
    protected JButton noSelectedBtn;
    protected JLabel editableHint;
    protected JButton downloadImportModelBtn;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
