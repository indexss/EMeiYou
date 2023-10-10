package me.indexss;

import java.awt.*;
import java.awt.event.*;
import java.io.FileWriter;
import java.io.IOException;
import javax.mail.MessagingException;
import javax.swing.*;
import javax.swing.event.*;

import static me.indexss.LoginFrame.*;

/**
 * @author shilinli
 */
public class MailFrame extends JFrame {
    public static String account0 = null;
    public static String pwd0 = null;
    public static String server0 = null;
    public static void main(String[] args) throws Exception {
//        new MailFrame(mailAddr, pwd, pop3Addr);
        new MailFrame("8208211408@csu.edu.cn","1941006006Qq", "pop3.csu.edu.cn");
    }
    public MailFrame(String account2, String pwd2, String server2) throws Exception {
        account0 = account2;
        pwd0 = pwd2;
        server0 = server2;
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
        initComponents();
    }
    private void list1ValueChanged(ListSelectionEvent e) {
        // TODO add your code here
    }

    private void button3(ActionEvent e) throws MessagingException {
        // TODO add your code here
        InBoxService inBoxService = new InBoxService(account0, pwd0, server0, "110");
        inBoxService.loadList();
        this.list1.setListData(inBoxService.getTitles());
    }

    private void button4(ActionEvent e) throws Exception {
        // TODO add your code here
        int selectedIndex = this.list1.getSelectedIndex();
        InBoxService inBoxService = new InBoxService(account0, server0, server0, "110");
        inBoxService.getContent(selectedIndex);
        this.textField1.setText(InBoxService.from1);
        this.textField2.setText(InBoxService.subject1);
        this.textField3.setText(InBoxService.date1);
        this.textArea1.setText("邮件为富文本内容，无法在客户端查看！");
        this.textArea1.setText(InBoxService.content1);
        this.label2.setText("📎无附件");
        this.label2.setText("📎" + InBoxService.filename1);
    }

    private void button1(ActionEvent e) throws Exception {
        // TODO add your code here
        InBoxService inBoxService = new InBoxService(mailAddr, pwd, pop3Addr);
        inBoxService.getAttach(this.list1.getSelectedIndex());
        System.out.println("fuck you");
    }

    private void button5(ActionEvent e) {
        // TODO add your code here
        String text = this.textArea1.getText(); // 获取textarea1的文本内容
        try {
            FileWriter writer = new FileWriter("/Users/shilinli/Desktop/Projects/javaProjects/EMeiYou/src/main/java/me/indexss/temp.html");
            writer.write(text); // 将文本内容写入temp.html文件
            writer.close();
            try {
                Runtime.getRuntime().exec("open /Users/shilinli/Desktop/Projects/javaProjects/EMeiYou/src/main/java/me/indexss/temp.html");
            } catch (IOException ex) {
//                    throw new RuntimeException(ex);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void button2(ActionEvent e) throws Exception {
        // TODO add your code here
        InBoxService.deleteMail(this.list1.getSelectedIndex());
        button3.doClick();
    }

    private void initComponents() throws Exception{
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        shoujianLb = new JLabel();
        scrollPane1 = new JScrollPane();
        list1 = new JList();
        label1 = new JLabel();
        scrollPane2 = new JScrollPane();
        textArea1 = new JTextArea();
        button1 = new JButton();
        button3 = new JButton();
        button4 = new JButton();
        label3 = new JLabel();
        textField1 = new JTextField();
        textField2 = new JTextField();
        textField3 = new JTextField();
        label4 = new JLabel();
        label2 = new JLabel();
        button2 = new JButton();
        button5 = new JButton();

        //======== this ========
        setFont(new Font("Lucida Grande", Font.PLAIN, 24));
        var contentPane = getContentPane();
        contentPane.setLayout(null);

        //---- shoujianLb ----
        shoujianLb.setText("\u6536\u4ef6\u5939");
        shoujianLb.setFont(new Font(".AppleSystemUIFont", Font.BOLD, 26));
        contentPane.add(shoujianLb);
        shoujianLb.setBounds(30, 10, 90, 30);

        //======== scrollPane1 ========
        {

            //---- list1 ----
            list1.addListSelectionListener(e -> {
			list1ValueChanged(e);
			list1ValueChanged(e);
		});
            scrollPane1.setViewportView(list1);
        }
        contentPane.add(scrollPane1);
        scrollPane1.setBounds(30, 50, 220, 410);

        //---- label1 ----
        label1.setText("From: ");
        label1.setFont(new Font(".AppleSystemUIFont", Font.PLAIN, 16));
        contentPane.add(label1);
        label1.setBounds(293, 6, 47, 24);

        //======== scrollPane2 ========
        {
            scrollPane2.setViewportView(textArea1);
        }
        contentPane.add(scrollPane2);
        scrollPane2.setBounds(275, 105, 555, 400);

        //---- button1 ----
        button1.setText("\u6536\u9644\u4ef6");
        button1.setFont(new Font(".AppleSystemUIFont", Font.BOLD, 16));
        button1.addActionListener(e -> {try {
button1(e);} catch (Exception ex) {
    throw new RuntimeException(ex);
}});
        contentPane.add(button1);
        button1.setBounds(750, 8, 80, 40);

        //---- button3 ----
        button3.setText("\u5237\u65b0");
        button3.addActionListener(e -> {try {
button3(e);} catch (MessagingException ex) {
    throw new RuntimeException(ex);
}});
        contentPane.add(button3);
        button3.setBounds(30, 470, 70, 40);

        //---- button4 ----
        button4.setText("\u67e5\u770b");
        button4.addActionListener(e -> {try {
button4(e);} catch (Exception ex) {
    throw new RuntimeException(ex);
}});
        contentPane.add(button4);
        button4.setBounds(180, 470, 70, 40);

        //---- label3 ----
        label3.setText("Time: ");
        label3.setFont(new Font(".AppleSystemUIFont", Font.PLAIN, 16));
        contentPane.add(label3);
        label3.setBounds(new Rectangle(new Point(295, 70), label3.getPreferredSize()));
        contentPane.add(textField1);
        textField1.setBounds(340, 5, 395, 30);
        contentPane.add(textField2);
        textField2.setBounds(340, 35, 395, 30);
        contentPane.add(textField3);
        textField3.setBounds(340, 65, 395, 30);

        //---- label4 ----
        label4.setText("Subject:");
        label4.setFont(new Font(".AppleSystemUIFont", Font.PLAIN, 16));
        contentPane.add(label4);
        label4.setBounds(new Rectangle(new Point(276, 38), label4.getPreferredSize()));

        //---- label2 ----
        label2.setText("\ud83d\udcce\u65e0\u9644\u4ef6");
        contentPane.add(label2);
        label2.setBounds(125, 20, 120, 20);

        //---- button2 ----
        button2.setText("\u5220\u9664");
        button2.addActionListener(e -> {try {
button2(e);} catch (Exception ex) {
    throw new RuntimeException(ex);
}});
        contentPane.add(button2);
        button2.setBounds(105, 470, 70, 40);

        //---- button5 ----
        button5.setText("\u67e5\u770bHTML");
        button5.addActionListener(e -> button5(e));
        contentPane.add(button5);
        button5.setBounds(750, 53, 80, 40);

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
        setSize(865, 565);
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JLabel shoujianLb;
    private JScrollPane scrollPane1;
    private JList list1;
    private JLabel label1;
    private JScrollPane scrollPane2;
    private JTextArea textArea1;
    private JButton button1;
    private JButton button3;
    private JButton button4;
    private JLabel label3;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JLabel label4;
    private JLabel label2;
    private JButton button2;
    private JButton button5;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
