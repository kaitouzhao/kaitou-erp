/*
 * Created by JFormDesigner on Mon May 11 15:48:37 CST 2015
 */

package kaitou.ppp.app.ui.dialog;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import static kaitou.ppp.app.SpringContextManager.getSystemSettingsService;
import static kaitou.ppp.app.ui.UIUtil.chooseExportFile;
import static kaitou.ppp.app.ui.UIUtil.handleEx;

/**
 * 报告错误提示框
 *
 * @author kaitou
 */
public class ReportErrorHint extends JDialog {

    private Frame parantFrame;
    private static final String reportTo = "zhaoliwei@cofco.com";

    public ReportErrorHint(Frame owner) {
        super(owner);
        parantFrame = owner;
        initComponents();
        setVisible(true);
    }

    public ReportErrorHint(Dialog owner) {
        super(owner);
        initComponents();
    }

    private void okButtonActionPerformed(ActionEvent e) {
        try {
            File targetFile = chooseExportFile("压缩文件", "zip");
            if (targetFile != null) {
                getSystemSettingsService().backPPP(targetFile.getPath());
                new OperationHint(parantFrame, "备份成功");
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                StringSelection text = new StringSelection(reportTo);
                clipboard.setContents(text, null);
                new OperationHint(parantFrame, "技术支持邮箱地址已复制到剪切板上");
            }
            setVisible(false);
        } catch (Exception ex) {
            handleEx(ex, (JFrame) parantFrame);
        }
    }

    private void cancelButtonActionPerformed(ActionEvent e) {
        setVisible(false);
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
        buttonBar = new JPanel();
        okButton = new JButton();
        cancelButton = new JButton();

        //======== this ========
        setTitle("\u62a5\u544a\u9519\u8bef");
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== dialogPane ========
        {
            dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
            dialogPane.setLayout(null);

            //======== contentPanel ========
            {
                contentPanel.setLayout(null);

                //---- label1 ----
                label1.setText("\u6280\u672f\u652f\u6301\u90ae\u7bb1\uff1azhaoliwei@cofco.com");
                contentPanel.add(label1);
                label1.setBounds(5, 5, 340, 45);

                //---- label2 ----
                label2.setText("\u5728\u4f7f\u7528\u7cfb\u7edf\u4e2d\u9047\u5230\u9519\u8bef\uff0c\u8bf7\u70b9\u51fbOK\uff0c\u9009\u62e9\u8def\u5f84\u540e\uff0c");
                contentPanel.add(label2);
                label2.setBounds(new Rectangle(new Point(5, 50), label2.getPreferredSize()));

                //---- label3 ----
                label3.setText("\u7cfb\u7edf\u4f1a\u81ea\u52a8\u5907\u4efd\u91cd\u8981\u6570\u636e\u3002\u8bf7\u5c06\u5907\u4efd\u6587\u4ef6\u53d1\u7ed9\u6280\u672f\u652f\u6301\u4eba\u5458\u3002");
                contentPanel.add(label3);
                label3.setBounds(new Rectangle(new Point(5, 80), label3.getPreferredSize()));

                //---- label4 ----
                label4.setText("\u7cfb\u7edf\u7684\u5b8c\u5584\u671f\u5f85\u4f60\u7684\u53c2\u4e0e\uff0c\u8c22\u8c22\uff01");
                contentPanel.add(label4);
                label4.setBounds(new Rectangle(new Point(5, 140), label4.getPreferredSize()));

                //---- label5 ----
                label5.setText("\u8bf7\u5728\u53d1\u9001\u7684\u90ae\u4ef6\u4e2d\u5c3d\u91cf\u8be6\u7ec6\u5730\u63cf\u8ff0\u4f60\u7684\u95ee\u9898\u4e0e\u64cd\u4f5c\u6b65\u9aa4\u3002");
                contentPanel.add(label5);
                label5.setBounds(new Rectangle(new Point(5, 110), label5.getPreferredSize()));

                { // compute preferred size
                    Dimension preferredSize = new Dimension();
                    for (int i = 0; i < contentPanel.getComponentCount(); i++) {
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
            dialogPane.add(contentPanel);
            contentPanel.setBounds(12, 12, 360, 203);

            //======== buttonBar ========
            {
                buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
                buttonBar.setLayout(new GridBagLayout());
                ((GridBagLayout) buttonBar.getLayout()).columnWidths = new int[]{0, 85, 80};
                ((GridBagLayout) buttonBar.getLayout()).columnWeights = new double[]{1.0, 0.0, 0.0};

                //---- okButton ----
                okButton.setText("OK");
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
                cancelButton.setText("Cancel");
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
            dialogPane.add(buttonBar);
            buttonBar.setBounds(12, 215, 360, buttonBar.getPreferredSize().height);

            { // compute preferred size
                Dimension preferredSize = new Dimension();
                for (int i = 0; i < dialogPane.getComponentCount(); i++) {
                    Rectangle bounds = dialogPane.getComponent(i).getBounds();
                    preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                    preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                }
                Insets insets = dialogPane.getInsets();
                preferredSize.width += insets.right;
                preferredSize.height += insets.bottom;
                dialogPane.setMinimumSize(preferredSize);
                dialogPane.setPreferredSize(preferredSize);
            }
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
    private JPanel buttonBar;
    private JButton okButton;
    private JButton cancelButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
