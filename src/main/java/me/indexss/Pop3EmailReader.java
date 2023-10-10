package me.indexss;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class Pop3EmailReader {
    private static Message[] messages = null;

    public static void main(String[] args) {
        Pop3EmailReader pop3EmailReader = new Pop3EmailReader();
        pop3EmailReader.fetchTitle();
        pop3EmailReader.printDetail(59); // 示例：打印索引为1的邮件的详细信息
    }

    public void fetchTitle(){
        String host = "pop3.csu.edu.cn";
        String username = "8208211408@csu.edu.cn";
        String password = "1941006006Qq";

        // 设置连接属性
        Properties properties = new Properties();
        properties.setProperty("mail.pop3.host", host);
        properties.setProperty("mail.pop3.port", "995");
        properties.setProperty("mail.pop3.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.setProperty("mail.pop3.socketFactory.fallback", "false");

        Session session = Session.getDefaultInstance(properties);

        try {
            // 创建连接
            Store store = session.getStore("pop3");
            store.connect(host, username, password);

            // 打开邮箱
            Folder folder = store.getFolder("INBOX");
            folder.open(Folder.READ_ONLY);

            // 获取邮件数量
            int messageCount = folder.getMessageCount();

            // 遍历邮件
            messages = folder.getMessages();
            System.out.println("收件箱中共有 " + messageCount + " 封邮件:");

            // 将邮件主题填入列表并打印
            for (int i = 0; i < messageCount; i++) {
                Message message = messages[i];
                String subject = message.getSubject();
                System.out.println("[" + (i+1) + "] " + subject);
            }

            // 关闭连接
            folder.close(false);
            store.close();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void printDetail(int index) {
        if (messages == null || index < 1 || index > messages.length) {
            System.out.println("无效的邮件索引");
            return;
        }

        try {
            Message message = messages[index - 1];
            System.out.println("邮件主题: " + message.getSubject());
            System.out.println("发件人: " + message.getFrom()[0]);
            System.out.println("发件时间: " + message.getSentDate());

            Object content = message.getContent();
            if (content instanceof Multipart) {
                Multipart multipart = (Multipart) content;
                for (int j = 0; j < multipart.getCount(); j++) {
                    BodyPart bodyPart = multipart.getBodyPart(j);
                    String fileName = bodyPart.getFileName();

                    // if the part is an attachment, display the filename
                    if (fileName != null) {
                        System.out.println("Attachment: " + fileName);
                    } else {
                        // if the part is not an attachment, display the content
                        Object body = bodyPart.getContent();
                        if (body instanceof Multipart){
                            for (int k = 0; k < ((Multipart) body).getCount(); k++) {
                                BodyPart bodyPartTest = ((Multipart) body).getBodyPart(k);
                                if (bodyPartTest.isMimeType("text/plain")) {
                                    System.out.println("Content: " + bodyPartTest.getContent().toString());
                                }
                            }
                        }
                    }

                }
            } else {
                // if the content is not a multipart message, display the content
                String body = content.toString();
                System.out.println("Content: " + body);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
