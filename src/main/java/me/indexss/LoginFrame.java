/*
 * Created by JFormDesigner on Tue Jun 06 10:19:36 CST 2023
 */

package me.indexss;

import com.formdev.flatlaf.intellijthemes.FlatDraculaIJTheme;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * @author shilinli
 */
public class LoginFrame extends JFrame {
    public static String mailAddr = null;
    public static String pwd = null;
    public static String smtpAddr = null;
    public static String pop3Addr = null;

    public static void main(String[] args) {
        FlatDraculaIJTheme.setup();
        LoginFrame loginFrame = new LoginFrame();
//        loginFrame.setVisible(true);
    }

    public LoginFrame() {
        initComponents();
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void button1(ActionEvent e) {
        // TODO add your code here
        mailAddr = textField1.getText();
        pwd = String.valueOf(passwordField1.getPassword());
        smtpAddr = textField2.getText();
        pop3Addr = textField3.getText();
        System.out.println(mailAddr);
        System.out.println(pwd);
        System.out.println(smtpAddr);
        System.out.println(pop3Addr);

    }

    private void button2(ActionEvent e) throws Exception {
        // TODO add your code here
//        new InBoxService(mailAddr, pwd, pop3Addr);
        new MailFrame(mailAddr, pwd, pop3Addr);
    }

    private void button3(ActionEvent e) {
        // TODO add your code here
        new SendFrame(mailAddr, smtpAddr);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        label1 = new JLabel();
        label2 = new JLabel();
        label3 = new JLabel();
        label4 = new JLabel();
        textField1 = new JTextField();
        passwordField1 = new JPasswordField();
        textField2 = new JTextField();
        textField3 = new JTextField();
        button1 = new JButton();
        label5 = new JLabel();
        button2 = new JButton();
        button3 = new JButton();

        //======== this ========
        var contentPane = getContentPane();
        contentPane.setLayout(null);

        //---- label1 ----
        label1.setText("\u90ae\u7bb1\u5730\u5740\uff1a");
        label1.setFont(new Font(".AppleSystemUIFont", Font.PLAIN, 16));
        contentPane.add(label1);
        label1.setBounds(new Rectangle(new Point(50, 80), label1.getPreferredSize()));

        //---- label2 ----
        label2.setText("\u5bc6\u7801\uff1a");
        label2.setFont(new Font(".AppleSystemUIFont", Font.PLAIN, 16));
        contentPane.add(label2);
        label2.setBounds(new Rectangle(new Point(82, 118), label2.getPreferredSize()));

        //---- label3 ----
        label3.setText("SMTP\u670d\u52a1\u5668\uff1a");
        label3.setFont(new Font(".AppleSystemUIFont", Font.PLAIN, 16));
        contentPane.add(label3);
        label3.setBounds(new Rectangle(new Point(23, 153), label3.getPreferredSize()));

        //---- label4 ----
        label4.setText("POP3\u670d\u52a1\u5668\uff1a");
        label4.setFont(new Font(".AppleSystemUIFont", Font.PLAIN, 16));
        contentPane.add(label4);
        label4.setBounds(new Rectangle(new Point(24, 189), label4.getPreferredSize()));
        contentPane.add(textField1);
        textField1.setBounds(140, 70, 290, 37);
        contentPane.add(passwordField1);
        passwordField1.setBounds(140, 109, 290, 37);
        contentPane.add(textField2);
        textField2.setBounds(140, 145, 290, 37);
        contentPane.add(textField3);
        textField3.setBounds(140, 181, 290, 37);

        //---- button1 ----
        button1.setText("\u786e\u8ba4\u914d\u7f6e");
        button1.setFont(new Font(".AppleSystemUIFont", Font.BOLD, 16));
        button1.addActionListener(e -> button1(e));
        contentPane.add(button1);
        button1.setBounds(45, 235, 105, 35);

        //---- label5 ----
        label5.setText("\ud83c\udf1f\u767b\u9646\u914d\u7f6e");
        label5.setFont(new Font(".AppleSystemUIFont", Font.PLAIN, 24));
        contentPane.add(label5);
        label5.setBounds(190, 15, 315, 40);

        //---- button2 ----
        button2.setText("\u6253\u5f00\u6536\u4ef6");
        button2.setFont(new Font(".AppleSystemUIFont", Font.BOLD, 14));
        button2.addActionListener(e -> {try {
button2(e);} catch (Exception ex) {
    throw new RuntimeException(ex);
}});
        contentPane.add(button2);
        button2.setBounds(190, 235, 105, 35);

        //---- button3 ----
        button3.setText("\u6253\u5f00\u5199\u4fe1");
        button3.setFont(new Font(".AppleSystemUIFont", Font.BOLD, 14));
        button3.addActionListener(e -> button3(e));
        contentPane.add(button3);
        button3.setBounds(335, 235, 105, 35);

        {
            // compute preferred size
            Dimension preferredSize = new Dimension();
            for(int i = 0; i < contentPane.getComponentCount(); i++) {
                Rectangle bounds = contentPane.getComponent(i).getBounds();
                preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
            }
            Insets insets = contentPane.getInsets();
            preferredSize.width += insets.right;
            preferredSize.height += insets.bottom;
            contentPane.setMinimumSize(preferredSize);
            contentPane.setPreferredSize(preferredSize);
        }
        setSize(485, 315);
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JLabel label4;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JTextField textField2;
    private JTextField textField3;
    private JButton button1;
    private JLabel label5;
    private JButton button2;
    private JButton button3;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
