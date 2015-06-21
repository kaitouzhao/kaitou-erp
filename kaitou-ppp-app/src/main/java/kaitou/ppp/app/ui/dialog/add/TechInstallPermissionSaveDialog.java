/*
 * Created by JFormDesigner on Tue Jun 09 15:27:28 CST 2015
 */

package kaitou.ppp.app.ui.dialog.add;

import kaitou.ppp.app.ui.dialog.BaseSaveDialog;
import kaitou.ppp.app.ui.table.queryobject.IQueryObject;
import kaitou.ppp.domain.tech.TechInstallPermission;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 装机授权添加界面
 *
 * @author kaitou
 */
public class TechInstallPermissionSaveDialog extends BaseSaveDialog<TechInstallPermission> {

    public TechInstallPermissionSaveDialog(JFrame owner, IQueryObject<TechInstallPermission> queryObject) {
        super(owner);
        parentFrame = owner;
        this.queryObject = queryObject;
        initComponents();
        textFields.add(textField1);
        textFields.add(textField2);
        textFields.add(textField3);
        textFields.add(textField4);
        textFields.add(textField5);
        textFields.add(textField6);
        textFields.add(textField7);
        textFields.add(textField8);
        textFields.add(textField9);
        setVisible(true);
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
        label1 = new JLabel();
        label2 = new JLabel();
        label3 = new JLabel();
        label4 = new JLabel();
        label5 = new JLabel();
        label6 = new JLabel();
        label7 = new JLabel();
        label8 = new JLabel();
        label9 = new JLabel();
        textField1 = new JTextField();
        textField3 = new JTextField();
        textField2 = new JTextField();
        textField4 = new JTextField();
        textField5 = new JTextField();
        textField6 = new JTextField();
        textField7 = new JTextField();
        textField8 = new JTextField();
        textField9 = new JTextField();
        buttonBar = new JPanel();
        okButton = new JButton();
        cancelButton = new JButton();

        //======== this ========
        setTitle("\u6dfb\u52a0\u88c5\u673a\u6743\u9650");
        setModal(true);
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== dialogPane ========
        {
            dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
            dialogPane.setLayout(new BorderLayout());

            //======== contentPanel ========
            {
                contentPanel.setLayout(null);

                //---- label1 ----
                label1.setText("\u533a\u57df");
                contentPanel.add(label1);
                label1.setBounds(20, 20, label1.getPreferredSize().width, 15);

                //---- label2 ----
                label2.setText("\u4ea7\u54c1\u7ebf");
                contentPanel.add(label2);
                label2.setBounds(new Rectangle(new Point(220, 20), label2.getPreferredSize()));

                //---- label3 ----
                label3.setText("\u8ba4\u5b9a\u5e97\u540d\u79f0");
                contentPanel.add(label3);
                label3.setBounds(new Rectangle(new Point(20, 60), label3.getPreferredSize()));

                //---- label4 ----
                label4.setText("\u673a\u578b");
                contentPanel.add(label4);
                label4.setBounds(new Rectangle(new Point(20, 100), label4.getPreferredSize()));

                //---- label5 ----
                label5.setText("\u673a\u8eab\u53f7");
                contentPanel.add(label5);
                label5.setBounds(new Rectangle(new Point(220, 100), label5.getPreferredSize()));

                //---- label6 ----
                label6.setText("MAC\u5730\u5740");
                contentPanel.add(label6);
                label6.setBounds(new Rectangle(new Point(20, 140), label6.getPreferredSize()));

                //---- label7 ----
                label7.setText("\u5f00\u653e\u65f6\u95f4");
                contentPanel.add(label7);
                label7.setBounds(new Rectangle(new Point(215, 140), label7.getPreferredSize()));

                //---- label8 ----
                label8.setText("\u6700\u7ec8\u7528\u6237\u540d\u79f0");
                contentPanel.add(label8);
                label8.setBounds(new Rectangle(new Point(20, 180), label8.getPreferredSize()));

                //---- label9 ----
                label9.setText("\u5907\u6ce8");
                contentPanel.add(label9);
                label9.setBounds(20, 220, 28, label9.getPreferredSize().height);
                contentPanel.add(textField1);
                textField1.setBounds(100, 20, 95, textField1.getPreferredSize().height);
                contentPanel.add(textField3);
                textField3.setBounds(100, 60, 270, 23);
                contentPanel.add(textField2);
                textField2.setBounds(275, 20, 95, 23);
                contentPanel.add(textField4);
                textField4.setBounds(100, 100, 95, 23);
                contentPanel.add(textField5);
                textField5.setBounds(275, 100, 95, 23);
                contentPanel.add(textField6);
                textField6.setBounds(100, 140, 95, 23);
                contentPanel.add(textField7);
                textField7.setBounds(275, 140, 95, 23);
                contentPanel.add(textField8);
                textField8.setBounds(100, 180, 270, 23);
                contentPanel.add(textField9);
                textField9.setBounds(100, 220, 270, 23);

                { // compute preferred size
                    Dimension preferredSize = new Dimension();
                    for(int i = 0; i < contentPanel.getComponentCount(); i++) {
                        Rectangle bounds = contentPanel.getComponent(i).getBounds();
                        preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                        preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                    }
                    Insets insets = contentPanel.getInsets();
                    preferredSize.width += insets.right;
                    preferredSize.height += insets.bottom;
                    contentPanel.setMinimumSize(preferredSize);
                    contentPanel.setPreferredSize(preferredSize);
                }
            }
            dialogPane.add(contentPanel, BorderLayout.CENTER);

            //======== buttonBar ========
            {
                buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
                buttonBar.setLayout(new GridBagLayout());
                ((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {0, 85, 80};
                ((GridBagLayout)buttonBar.getLayout()).columnWeights = new double[] {1.0, 0.0, 0.0};

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
        contentPane.add(dialogPane, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel dialogPane;
    private JPanel contentPanel;
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JLabel label4;
    private JLabel label5;
    private JLabel label6;
    private JLabel label7;
    private JLabel label8;
    private JLabel label9;
    private JTextField textField1;
    private JTextField textField3;
    private JTextField textField2;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;
    private JTextField textField7;
    private JTextField textField8;
    private JTextField textField9;
    private JPanel buttonBar;
    private JButton okButton;
    private JButton cancelButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
