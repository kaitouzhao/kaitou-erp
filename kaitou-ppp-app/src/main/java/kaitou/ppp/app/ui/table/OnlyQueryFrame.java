/*
 * Created by JFormDesigner on Fri Jul 03 15:16:31 CST 2015
 */

package kaitou.ppp.app.ui.table;

import com.womai.bsp.tool.utils.CollectionUtil;
import kaitou.ppp.app.ui.table.queryobject.IQueryObject;
import kaitou.ppp.domain.BaseDomain;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

/**
 * 只可查询的界面
 *
 * @author kaitou
 */
public class OnlyQueryFrame<T extends BaseDomain> extends QueryFrame<T> {
    public OnlyQueryFrame(IQueryObject<T> queryObject) {
        super(queryObject);
    }

    @Override
    protected void view(List<? extends BaseDomain> list) {
        String[] fieldNames = queryObject.fieldNames();
        int fieldNamesSize = fieldNames.length;
        Object[][] objects = new Object[(list.size())][fieldNamesSize];
        for (int i = 0; i < objects.length; i++) {
            List<Object> oneRowList = CollectionUtil.newList(list.get(i).export2Array(fieldNames));
            objects[i] = CollectionUtil.toArray(oneRowList, Object.class);
        }
        String[] tableTitles = queryObject.tableTitles();
        dataTable.setModel(new QueryTable(objects,
                tableTitles));
        if (!queryObject.autoResizeMode()) {
            dataTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        }
    }

    @Override
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

        contentPane.setPreferredSize(new Dimension(920, 695));
        setSize(920, 695);
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }
}
