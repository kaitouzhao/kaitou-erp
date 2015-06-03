/*
 * Created by JFormDesigner on Thu Feb 05 15:42:34 CST 2015
 */

package kaitou.ppp.app.ui.table;

import com.womai.bsp.tool.utils.CollectionUtil;
import kaitou.ppp.app.ui.dialog.ConfirmHint;
import kaitou.ppp.app.ui.dialog.OperationHint;
import kaitou.ppp.app.ui.dialog.SaveDialog;
import kaitou.ppp.dao.support.Condition;
import kaitou.ppp.dao.support.Pager;
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
public class QueryFrameNew<T extends BaseDomain> extends JFrame {
    /**
     * 操作列标题
     */
    private static final String OP_COLUMN_TITLE = "操作";
    /**
     * 选择列号
     */
    private static final int SELECT_COLUMN_INDEX = 0;
    /**
     * 数据开始列号
     */
    private static final int DATA_COLUMN_START_INDEX = 1;
    /**
     * 存疑筛选名
     */
    private static final String CHOOSE_IN_DOUBT_LABEL = "存疑";
    /**
     * 标识数据存疑的属性名
     */
    private static final String IN_DOUBT_FIELD_NAME = "inDoubt";
    /**
     * 当前frame
     */
    private QueryFrameNew self = this;
    /**
     * 查询区域输入框动态集合
     */
    private List<JTextField> queryTextFields = new ArrayList<JTextField>();
    /**
     * 分页对象
     */
    private Pager<T> pager;
    /**
     * 显示数据源
     */
    private List<T> shownDatas;
    /**
     * 当前页码
     */
    private int currentPageIndex = 1;
    /**
     * 总页数
     */
    private int pageCount;
    /**
     * 总记录数
     */
    private int recordCount;
    /**
     * 查询对象
     */
    private IQueryObject<T> queryObject;
    /**
     * 操作列号
     */
    private int opColumnIndex = -1;
    /**
     * 筛选存疑
     */
    private JCheckBox selectInDoubt;

    /**
     * 构造函数
     *
     * @param queryObject 查询对象
     */
    public QueryFrameNew(IQueryObject<T> queryObject) {
        this.queryObject = queryObject;
        String[] tableTitles = this.queryObject.tableTitles();
        if (OP_COLUMN_TITLE.equals(tableTitles[tableTitles.length - 1])) {
            opColumnIndex = tableTitles.length - 1;
        }
        pager = OPManager.queryPager(queryObject.domainType(), currentPageIndex, null);
        initComponents();
        reQuery();
        initQueryArea();
        setTitle(queryObject.title());
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }

    /**
     * 初始化表格参数
     */
    @SuppressWarnings(value = "unchecked")
    private void initTableData() {
        recordCount = pager.getTotalSize();
        pageCount = pager.getTotalPage();
        recordCountShow.setText("总计：" + recordCount);
        selectPage.removeAllItems();
        for (int i = 0; i < pageCount; i++) {
            selectPage.insertItemAt(i + 1, i);
        }
        currentPageIndex = 1;
    }

    /**
     * 下一页
     */
    @SuppressWarnings(value = "unchecked")
    private void nextPage() {
        if (currentPageIndex < pageCount) {
            currentPageIndex++;
        }
        selectPage.setSelectedIndex(currentPageIndex - 1);
        queryByCondition();
    }

    /**
     * 上一页
     */
    private void previousPage() {
        if (currentPageIndex > 1) {
            currentPageIndex--;
        }
        selectPage.setSelectedIndex(currentPageIndex - 1);
        queryByCondition();
    }

    /**
     * 显示
     *
     * @param list 当前显示列表
     */
    private void view(List<? extends BaseDomain> list) {
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
    private List<Integer> getSelectedRows() {
        List<Integer> selectedRows = new ArrayList<Integer>();
        for (int row = 0; row < dataTable.getRowCount(); row++) {
            if (dataTable.getValueAt(row, SELECT_COLUMN_INDEX) == Boolean.TRUE) {
                selectedRows.add(row);
            }
        }
        return selectedRows;
    }

    private void firstPageBtnActionPerformed(ActionEvent e) {
        try {
            reQuery();
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void previousPageBtnActionPerformed(ActionEvent e) {
        try {
            previousPage();
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void nextPageBtnActionPerformed(ActionEvent e) {
        try {
            nextPage();
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void thisWindowClosed(WindowEvent e) {
        setVisible(false);
        // TODO 待优化
//        datas = null;
//        shownDatas = null;
//        dataTable = null;
//        dispose();
    }

    @SuppressWarnings(value = "unchecked")
    private void queryBtnActionPerformed(ActionEvent e) {
        reQuery();
    }

    /**
     * 根据查询条件筛选
     */
    private void queryByCondition() {
        try {
            boolean pickInDoubt = selectInDoubt != null ? selectInDoubt.isSelected() : false;
            List<Condition> conditions = new ArrayList<Condition>();
            if (pickInDoubt) {
                conditions.add(new Condition(IN_DOUBT_FIELD_NAME, pickInDoubt));
            }
            if (CollectionUtil.isNotEmpty(queryTextFields)) {
                JTextField textField;
                String textFieldValue;
                String[] queryFieldNames = queryObject.queryFieldNames();
                for (int j = 0; j < queryTextFields.size(); j++) {
                    textField = queryTextFields.get(j);
                    textFieldValue = StringUtils.isEmpty(textField.getText()) ? "" : textField.getText().trim();
                    if (StringUtils.isEmpty(textFieldValue)) {
                        continue;
                    }
                    conditions.add(new Condition(queryFieldNames[j], textFieldValue.trim()));
                }
                pager = OPManager.queryPager(queryObject.domainType(), currentPageIndex, conditions);
            } else {
                pager = OPManager.queryPager(queryObject.domainType(), currentPageIndex, conditions);
            }
            shownDatas = pager.getResult();
            view(shownDatas);
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void selectPageActionPerformed(ActionEvent e) {
        // DO NOTHING
    }

    private void selectPageItemStateChanged(ItemEvent e) {
        try {
            currentPageIndex = Integer.valueOf(e.getItem().toString());
            queryByCondition();
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    @SuppressWarnings(value = "unchecked")
    private void resetBtnActionPerformed(ActionEvent e) {
        try {
            for (JTextField queryTextField : queryTextFields) {
                queryTextField.setText("");
            }
            if (selectInDoubt != null) {
                selectInDoubt.setSelected(false);
            }
            reQuery();
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void dataTableMouseClicked(MouseEvent e) {
        if (e.getButton() != 3 || CollectionUtil.isEmpty(shownDatas)) {
            return;
        }
        T t = shownDatas.get(dataTable.getSelectedRow());
        Object[] datas = t.export2Array(queryObject.fieldNames());
        Object data = datas[dataTable.getSelectedColumn() - DATA_COLUMN_START_INDEX];
        String copyData = String.valueOf(data == null ? "" : data);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection text = new StringSelection(copyData);
        clipboard.setContents(text, null);
        new OperationHint(this, "已复制到剪切板上");
    }

    private void deleteBtnActionPerformed(ActionEvent e) {
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
            Object[] deleted = new Object[selectedRows.size()];
            for (int i = 0; i < selectedRows.size(); i++) {
                deleted[i] = shownDatas.get(selectedRows.get(i));
            }
            delete(queryObject.domainType(), deleted);
            new OperationHint(this, "删除成功！");
            reQuery();
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void reQuery() {
        queryByCondition();
        initTableData();
    }

    private void saveBtnActionPerformed(ActionEvent e) {
        try {
            SaveDialog<T> saveDialog = new SaveDialog<T>(this, queryObject);
            if (!saveDialog.isOk() || saveDialog.getDomain() == null) {
                return;
            }
            new OperationHint(self, "添加成功");
            reQuery();
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    /**
     * 自定义table
     *
     * @see javax.swing.table.DefaultTableModel
     */
    private class QueryTable extends DefaultTableModel {
        private QueryTable(Object[][] data, Object[] columnNames) {
            super(data, columnNames);
        }

        @Override
        public boolean isCellEditable(int row, int column) {
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
                queryByCondition();
                return;
            }
            Object edited = shownDatas.get(row);
            String fieldName = String.valueOf(Array.get(queryObject.fieldNames(), column - 1));
            String fieldValue = String.valueOf(dataTable.getValueAt(row, column));
            setFieldValue(fieldName, edited, fieldValue);
            saveOrUpdate(queryObject.domainType(), CollectionUtil.toArray(CollectionUtil.newList(edited), queryObject.domainClass()));
            new OperationHint(self, "更新成功");
            queryByCondition();
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
    private class QueryTableColorRenderer extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            T t = shownDatas.get(row);
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
    private class QueryTableBtnCellEditor extends AbstractCellEditor implements TableCellEditor, TableCellRenderer, ActionListener {

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
            Object shownObj = shownDatas.get(opIndexes[0]);
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
            saveOrUpdate(queryObject.domainType(), CollectionUtil.toArray(CollectionUtil.newList(shownObj), queryObject.domainClass()));
            new OperationHint(self, "操作成功");
            queryByCondition();
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
            Object initFieldValue = getFieldValue(queryObject.opFieldName(), shownDatas.get(row));
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

    private void queryAreaKeyPressed(KeyEvent e) {
        // TODO add your code here
    }

    private void exportBtnActionPerformed(ActionEvent e) {
        try {
            File targetFile = chooseExportFile("excel文件", "xlsx");
            if (targetFile == null) return;
            List<Integer> selectedRows = getSelectedRows();
            List exportList = new ArrayList();
            if (selectedRows.size() <= 0) {
                exportList.addAll(shownDatas);
            } else {
                for (int i = 0; i < selectedRows.size(); i++) {
                    exportList.add(shownDatas.get(selectedRows.get(i)));
                }
            }
            export(queryObject.domainType(), exportList, targetFile);
            new OperationHint(this, "导出成功");
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void sendBtnActionPerformed(ActionEvent e) {
        try {
            List<Integer> selectedRows = getSelectedRows();
            if (selectedRows.size() <= 0) {
                new OperationHint(this, "请先选择要发送的记录");
                return;
            }
            List<T> sendList = new ArrayList<T>();
            for (int i = 0; i < selectedRows.size(); i++) {
                sendList.add(shownDatas.get(selectedRows.get(i)));
            }
            saveOrUpdate(queryObject.domainType(), CollectionUtil.toArray(sendList, queryObject.domainClass()));
            new OperationHint(this, "发送成功");
        } catch (Exception ex) {
            handleEx(ex, this);
        }
    }

    private void resultAreaMouseWheelMoved(MouseWheelEvent e) {
        if (e.getWheelRotation() < 0) {
            previousPage();
        } else {
            nextPage();
        }
    }

    private void allSelectedBtnActionPerformed(ActionEvent e) {
        changeCheckboxStatus(Boolean.TRUE);
    }

    /**
     * 改变当前页全部复选框选择状态
     *
     * @param isSelected 是否选中
     */
    private void changeCheckboxStatus(Boolean isSelected) {
        for (int row = 0; row < dataTable.getRowCount(); row++) {
            dataTable.setValueAt(isSelected, row, SELECT_COLUMN_INDEX);
        }
        dataTable.repaint();
    }

    private void noSelectedBtnActionPerformed(ActionEvent e) {
        changeCheckboxStatus(Boolean.FALSE);
    }

    /**
     * 初始化界面
     */
    private void initComponents() {
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
        recordCountShow.setText("\u603b\u8ba1\uff1a");
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
            queryArea.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    queryAreaKeyPressed(e);
                }
            });
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

        contentPane.setPreferredSize(new Dimension(920, 695));
        setSize(920, 695);
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents

    }

    /**
     * 初始化查询区域
     */
    private void initQueryArea() {
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
            queryLabel = new JLabel(queryLabelTitle);
            queryArea.add(queryLabel);
            queryTextField = new JTextField(10);
            queryTextField.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {

                }

                @Override
                public void keyPressed(KeyEvent e) {
                    reQuery();
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
                    reQuery();
                }
            });
            queryArea.add(selectInDoubt);
        }
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JScrollPane resultArea;
    private JTable dataTable;
    private JButton firstPageBtn;
    private JButton previousPageBtn;
    private JButton nextPageBtn;
    private JLabel recordCountShow;
    private JLabel jump2Page;
    private JComboBox selectPage;
    private JPanel queryArea;
    private JButton queryBtn;
    private JButton resetBtn;
    private JButton deleteBtn;
    private JButton saveBtn;
    private JButton exportBtn;
    private JButton sendBtn;
    private JButton allSelectedBtn;
    private JButton noSelectedBtn;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
