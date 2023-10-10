/*
 * Created by JFormDesigner on Tue Jun 06 10:03:48 CST 2023
 */

package me.indexss;

import com.formdev.flatlaf.intellijthemes.FlatDraculaIJTheme;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * @author shilinli
 */
public class SendFrame extends JFrame {
    public static String myAddr = null;
    public static String smtpAddr = null;
    public static String fjAddr = "";
    public static String fjName = null;

    public SendFrame(String addr, String smtpAdd){
        FlatDraculaIJTheme.setup();
        myAddr = addr;
        smtpAddr = smtpAdd;
        this.setTitle("Addr: " + myAddr + "    SMTP: " + smtpAdd);
//        label5.setText("Addr: " + addr + "    SMTP: " + smtpAdd);
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        initComponents();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }
    public SendFrame() {
        initComponents();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    private void button1(ActionEvent e) {
        // TODO add your code here
        String to = textField1.getText();
        String from = myAddr;
        String host = smtpAddr;
        Properties properties = System.getProperties();                // 获取系统属性
        properties.setProperty("mail.smtp.host", host);                // 设置邮件服务器
        properties.put("mail.smtp.auth", "true");                    // 开启认证
        Session session = Session.getDefaultInstance(properties,new Authenticator(){
            public PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(myAddr, LoginFrame.pwd);         //发件人邮件用户名、授权码
            }
        });                                                                            // 获取默认session对象，通过认证

        try{
            MimeMessage message = new MimeMessage(session);                            // 创建默认的 MimeMessage 对象
            message.setFrom(new InternetAddress(from));                                // Set From: 头部头字段
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));        // Set To: 头部头字段
            message.setSubject(textField2.getText());                                // Set Subject: 头部头字段
            message.setText(textArea1.getText());
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(textArea1.getText());
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            messageBodyPart = new MimeBodyPart();
            if(!fjAddr.equals("")) {
                DataSource source = new FileDataSource(new File(fjAddr));
                messageBodyPart.setDataHandler(new DataHandler(source));
                messageBodyPart.setFileName(fjName);
                multipart.addBodyPart(messageBodyPart);
            }
            message.setContent(multipart);
            // 设置消息体
            Transport.send(message);                                                        // 发送消息
        }catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }

    private void button2(ActionEvent e) {
        // TODO add your code here
        JFileChooser fileChooser = new JFileChooser();

        // 设置对话框标题
        fileChooser.setDialogTitle("选择文件");

        // 显示文件选择对话框
        int result = fileChooser.showOpenDialog(null);

        // 判断用户是否选择了文件
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            // 获取文件的路径和文件名
            String filePath = selectedFile.getAbsolutePath();
            String fileName = selectedFile.getName();
            fjAddr = filePath;
            fjName = fileName;

            // 打印文件的路径和文件名
            System.out.println("文件路径：" + filePath);
            System.out.println("文件名：" + fileName);
            label6.setText("📎" + fileName);
        }
    }

    private void button3(ActionEvent e) {
        // TODO add your code here
        JFileChooser fileChooser = new JFileChooser();

        // 设置对话框标题
        fileChooser.setDialogTitle("选择txt文件");

        // 设置文件过滤器，只显示 txt 文件
        FileNameExtensionFilter filter = new FileNameExtensionFilter("文本文件 (*.txt)", "txt");
        fileChooser.setFileFilter(filter);

        // 显示文件选择对话框
        int result = fileChooser.showOpenDialog(null);

        // 判断用户是否选择了文件
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            // 读取文件内容
            StringBuilder sb = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                    sb.append("\n");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            // 设置 testArea1 的文本内容
            this.textArea1.setText(sb.toString());
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        label1 = new JLabel();
        label2 = new JLabel();
        textField1 = new JTextField();
        label3 = new JLabel();
        textField2 = new JTextField();
        scrollPane1 = new JScrollPane();
        textArea1 = new JTextArea();
        label4 = new JLabel();
        textField3 = new JTextField();
        button1 = new JButton();
        button2 = new JButton();
        button3 = new JButton();
        label5 = new JLabel();
        label6 = new JLabel();

        //======== this ========
        var contentPane = getContentPane();
        contentPane.setLayout(null);


        //---- label1 ----
        label1.setText("\u53d1\u4ef6\u7aef");
        label1.setFont(new Font(".AppleSystemUIFont", Font.BOLD, 20));
        contentPane.add(label1);
        label1.setBounds(25, 13, 85, 35);

        //---- label2 ----
        label2.setText("\u6536\u4ef6\u4eba\u5730\u5740\uff1a");
        label2.setFont(new Font(".AppleSystemUIFont", Font.PLAIN, 18));
        contentPane.add(label2);
        label2.setBounds(120, 15, 110, 30);
        contentPane.add(textField1);
        textField1.setBounds(225, 13, 620, textField1.getPreferredSize().height);

        //---- label3 ----
        label3.setText("\u4e3b\u9898\uff1a");
        label3.setFont(new Font(".AppleSystemUIFont", Font.PLAIN, 18));
        contentPane.add(label3);
        label3.setBounds(new Rectangle(new Point(174, 55), label3.getPreferredSize()));
        contentPane.add(textField2);
        textField2.setBounds(225, 52, 620, textField2.getPreferredSize().height);

        //======== scrollPane1 ========
        {
            scrollPane1.setViewportView(textArea1);
        }
        contentPane.add(scrollPane1);
        scrollPane1.setBounds(125, 130, 715, 425);

        //---- label4 ----
        label4.setText("\u6284\u9001\uff1a");
        label4.setFont(new Font(".AppleSystemUIFont", Font.PLAIN, 18));
        contentPane.add(label4);
        label4.setBounds(new Rectangle(new Point(174, 92), label4.getPreferredSize()));
        contentPane.add(textField3);
        textField3.setBounds(225, 91, 620, textField3.getPreferredSize().height);

        //---- button1 ----
        button1.setText("\u53d1\u9001");
        button1.setFont(new Font(".AppleSystemUIFont", Font.BOLD, 16));
        button1.addActionListener(e -> button1(e));
        contentPane.add(button1);
        button1.setBounds(10, 515, 105, 40);

        //---- button2 ----
        button2.setText("\u9644\u4ef6");
        button2.setFont(new Font(".AppleSystemUIFont", Font.BOLD, 16));
        button2.addActionListener(e -> button2(e));
        contentPane.add(button2);
        button2.setBounds(10, 462, 105, 40);

        //---- button3 ----
        button3.setText("\u4ece\u6587\u4ef6\u8f7d\u5165");
        button3.setFont(new Font(".AppleSystemUIFont", Font.BOLD, 14));
        button3.addActionListener(e -> button3(e));
        contentPane.add(button3);
        button3.setBounds(10, 410, 105, 40);
        contentPane.add(label5);
        label5.setBounds(450, 545, 390, 25);

        //---- label6 ----
        label6.setText("\ud83d\udcce\u672a\u6302\u8f7d\u9644\u4ef6\uff01");
        contentPane.add(label6);
        label6.setBounds(15, 295, 100, 55);

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
        setSize(865, 605);
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JLabel label1;
    private JLabel label2;
    private JTextField textField1;
    private JLabel label3;
    private JTextField textField2;
    private JScrollPane scrollPane1;
    private JTextArea textArea1;
    private JLabel label4;
    private JTextField textField3;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JLabel label5;
    private JLabel label6;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
