/*
 * Created by JFormDesigner on Thu Jun 04 14:21:39 CST 2015
 */

package kaitou.ppp.app.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 测试窗体
 *
 * @author kaitou
 */
public class TestFrame extends JFrame {

    private JFrame self;
    /**
     * 等待提示
     */
    private JProgressBar waitingHint;

    public static void main(String[] args) throws InterruptedException {
        TestFrame frame = new TestFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
        JProgressBar waitingHint1 = new JProgressBar(SwingConstants.HORIZONTAL);
        waitingHint1.setIndeterminate(true);
        frame.add(waitingHint1);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        frame.remove(waitingHint1);
    }

    public TestFrame() {
        initComponents();
        self = this;
        waitingHint = new JProgressBar(SwingConstants.HORIZONTAL);
        waitingHint.setIndeterminate(true);
    }

    private void testBtnActionPerformed(ActionEvent e) {
        callWaiting();
    }

    private void callWaiting() {
        self.add(waitingHint);
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        self.remove(waitingHint);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        testBtn = new JButton();

        //======== this ========
        setTitle("demo");
        Container contentPane = getContentPane();
        contentPane.setLayout(new FlowLayout());

        //---- testBtn ----
        testBtn.setText("\u6d4b\u8bd5");
        testBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                testBtnActionPerformed(e);
            }
        });
        contentPane.add(testBtn);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JButton testBtn;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
