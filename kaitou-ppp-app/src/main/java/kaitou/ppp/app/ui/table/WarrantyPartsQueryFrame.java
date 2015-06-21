/*
 * Created by JFormDesigner on Sat Jun 20 14:46:10 CST 2015
 */

package kaitou.ppp.app.ui.table;

import kaitou.ppp.app.ui.table.queryobject.IQueryObject;
import kaitou.ppp.domain.warranty.WarrantyParts;
import kaitou.ppp.service.ServiceInvokeManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.rmi.RemoteException;
import java.util.List;

import static kaitou.ppp.app.SpringContextManager.getWarrantyService;
import static kaitou.ppp.common.utils.NumberUtil.money2Str;

/**
 * 保修及零件索赔查询界面
 *
 * @author kaitou
 */
public class WarrantyPartsQueryFrame extends PageQueryFrame<WarrantyParts> {

    public WarrantyPartsQueryFrame(IQueryObject<WarrantyParts> queryObject) {
        super(queryObject);
        reCompute();
    }

    @Override
    protected void queryBtnActionPerformed(ActionEvent e) {
        deliveryNumberTotal.setText("正在计算数量总计...");
        totalAfterTaxTotal.setText("正在计算金额总计......");
        super.queryBtnActionPerformed(e);
        reCompute();
    }

    /**
     * 重新计算
     * <ul>
     * <li>数量</li>
     * <li>金额</li>
     * </ul>
     */
    private void reCompute() {
        ServiceInvokeManager.asynchronousRun(new ServiceInvokeManager.InvokeRunnable() {
            @Override
            public void run() throws RemoteException {
                List<WarrantyParts> warrantyPartsList = getWarrantyService().queryWarrantyParts(getConditions());
                int totalOfDeliveryNumber = 0;
                double totalOfTotalAfterTax = 0;
                for (WarrantyParts warrantyParts : warrantyPartsList) {
                    try {
                        totalOfDeliveryNumber += Integer.valueOf(warrantyParts.getDeliveryNumber());
                    } catch (NumberFormatException e) {
                    }
                    try {
                        totalOfTotalAfterTax += Double.valueOf(warrantyParts.getTotalAfterTax());
                    } catch (NumberFormatException e) {
                    }
                }
                deliveryNumberTotal.setText("数量总计:" + totalOfDeliveryNumber);
                totalAfterTaxTotal.setText("金额总计:" + money2Str(totalOfTotalAfterTax));
            }
        });
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
        deleteBtn = new JButton();
        saveBtn = new JButton();
        exportBtn = new JButton();
        sendBtn = new JButton();
        allSelectedBtn = new JButton();
        noSelectedBtn = new JButton();
        editableHint = new JLabel();
        downloadImportModelBtn = new JButton();
        deliveryNumberTotal = new JLabel();
        totalAfterTaxTotal = new JLabel();

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

        //---- deliveryNumberTotal ----
        deliveryNumberTotal.setText("\u6b63\u5728\u8ba1\u7b97\u6570\u91cf\u603b\u8ba1...");
        contentPane.add(deliveryNumberTotal);
        deliveryNumberTotal.setBounds(new Rectangle(new Point(445, 150), deliveryNumberTotal.getPreferredSize()));

        //---- totalAfterTaxTotal ----
        totalAfterTaxTotal.setText("\u6b63\u5728\u8ba1\u7b97\u91d1\u989d\u603b\u8ba1......");
        contentPane.add(totalAfterTaxTotal);
        totalAfterTaxTotal.setBounds(615, 150, 260, totalAfterTaxTotal.getPreferredSize().height);

        contentPane.setPreferredSize(new Dimension(920, 695));
        setSize(920, 695);
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents

    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JLabel deliveryNumberTotal;
    private JLabel totalAfterTaxTotal;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
