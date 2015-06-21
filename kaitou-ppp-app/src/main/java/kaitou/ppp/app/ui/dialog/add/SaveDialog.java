/*
 * Created by JFormDesigner on Tue Feb 10 11:47:12 CST 2015
 */

package kaitou.ppp.app.ui.dialog.add;

import com.womai.bsp.tool.utils.CollectionUtil;
import kaitou.ppp.app.ui.dialog.BaseSaveDialog;
import kaitou.ppp.app.ui.table.queryobject.IQueryObject;
import kaitou.ppp.domain.BaseDomain;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 保存实体对话框
 *
 * @author 赵立伟
 */
public class SaveDialog<T extends BaseDomain> extends BaseSaveDialog {
    /**
     * 创建对话框
     *
     * @param owner       父窗体
     * @param queryObject 查询对象
     */
    public SaveDialog(JFrame owner, IQueryObject<T> queryObject) {
        super(owner);
        parentFrame = owner;
        this.queryObject = queryObject;
        initComponents();
        initInputArea();
        setVisible(true);
    }

    /**
     * 初始化输入区域
     */
    private void initInputArea() {
        String[] titles = queryObject.saveTitles();
        if (CollectionUtil.isEmpty(titles) || CollectionUtil.isEmpty(queryObject.saveFieldNames())) {
            throw new RuntimeException("缺少输入选项");
        }
        JLabel label;
        JTextField textField;
        for (String field : titles) {
            label = new JLabel(field);
            textField = new JTextField(10);
            contentPanel.add(label);
            contentPanel.add(textField);
            textFields.add(textField);
        }
    }

    private void okButtonActionPerformed(ActionEvent e) {
        ok();
    }

    private void cancelButtonActionPerformed(ActionEvent e) {
        cancel();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        buttonBar = new JPanel();
        okButton = new JButton();
        cancelButton = new JButton();

        //======== this ========
        setTitle("\u8bf7\u6dfb\u52a0");
        setModal(true);
        Container contentPane = getContentPane();
        contentPane.setLayout(null);

        //======== dialogPane ========
        {
            dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
            dialogPane.setLayout(new BorderLayout());

            //======== contentPanel ========
            {
                contentPanel.setLayout(new FlowLayout());
            }
            dialogPane.add(contentPanel, BorderLayout.CENTER);

            //======== buttonBar ========
            {
                buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
                buttonBar.setLayout(new GridBagLayout());
                ((GridBagLayout) buttonBar.getLayout()).columnWidths = new int[]{0, 85, 80};
                ((GridBagLayout) buttonBar.getLayout()).columnWeights = new double[]{1.0, 0.0, 0.0};

                //---- okButton ----
                okButton.setText("\u4fdd\u5b58");
                okButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        okButtonActionPerformed(e);
                    }
                });
                buttonBar.add(okButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 0, 5), 0, 0));

                //---- cancelButton ----
                cancelButton.setText("\u53d6\u6d88");
                cancelButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        cancelButtonActionPerformed(e);
                    }
                });
                buttonBar.add(cancelButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(0, 0, 0, 0), 0, 0));
            }
            dialogPane.add(buttonBar, BorderLayout.SOUTH);
        }
        contentPane.add(dialogPane);
        dialogPane.setBounds(0, 0, 699, 504);

        contentPane.setPreferredSize(new Dimension(715, 540));
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel dialogPane;
    private JPanel contentPanel;
    private JPanel buttonBar;
    private JButton okButton;
    private JButton cancelButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
