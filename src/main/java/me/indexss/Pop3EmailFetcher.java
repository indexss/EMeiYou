package me.indexss;

import org.jsoup.Jsoup;

import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import java.util.Properties;
import java.io.*;
import java.util.Scanner;

public class Pop3EmailFetcher {
    public static void main(String[] args) {
        String host = "pop3.csu.edu.cn";
        String username = "8208211408@csu.edu.cn";
        String password = "1941006006Qq";
        String attachmentDownloadPath = "/Users/shilinli/Desktop/fj/"; // 附件下载路径

        // 设置连接属性
        Properties properties = new Properties();
        properties.put("mail.store.protocol", "pop3");
        properties.put("mail.pop3.host", host);
        properties.put("mail.pop3.port", "110");

        try {
            // 创建会话对象
            Session session = Session.getDefaultInstance(properties);

            // 连接到邮件服务器
            Store store = session.getStore("pop3");
            store.connect(host, username, password);

            // 打开收件箱
            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);

            // 获取邮件消息
            Message[] messages = inbox.getMessages();

            // 打印邮件列表
            System.out.println("邮件列表:");
            for (int i = 0; i < messages.length; i++) {
                Message message = messages[i];
                System.out.println((i + 1) + ". " + message.getSubject());
            }

            // 选择要打开的邮件
            Scanner scanner = new Scanner(System.in);
            int selectedMessageIndex;
            Message selectedMessage;
            do {
                System.out.print("请输入要打开的邮件编号 (输入 0 退出): ");
                selectedMessageIndex = scanner.nextInt();
                if (selectedMessageIndex == 0) {
                    break;
                }

                int messageIndex = selectedMessageIndex - 1;
                if (messageIndex >= 0 && messageIndex < messages.length) {
                    selectedMessage = messages[messageIndex];
                    printMessage(selectedMessage);

                    // 检查附件并下载
                    Multipart multipart = (Multipart) selectedMessage.getContent();
                    int attachmentCount = countAttachments(multipart);
                    if (attachmentCount > 0) {
                        System.out.print("是否下载附件? (y/n): ");
                        String downloadChoice = scanner.next();
                        if (downloadChoice.equalsIgnoreCase("y")) {
                            downloadAttachments(multipart, attachmentDownloadPath);
                        }
                    }
                } else {
                    System.out.println("无效的邮件编号。");
                }
            } while (true);

            // 关闭连接
            inbox.close(false);
            store.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void printMessage(Message message) throws Exception {
        String subject = message.getSubject();
        String sender = message.getFrom()[0].toString();
        String date = message.getSentDate().toString();
        String content = getTextFromMessage(message);

        System.out.println("主题: " + subject);
        System.out.println("发件人: " + sender);
        System.out.println("发送日期: " + date);
        System.out.println("内容: " + content);
    }

    private static String getTextFromMessage(Message message) throws Exception {
        String result = "";
        if (message.isMimeType("text/plain")) {
            result = message.getContent().toString();
        } else if (message.isMimeType("multipart/*")) {
            MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
            result = getTextFromMimeMultipart(mimeMultipart);
        } else if (message.isMimeType("text/html")) {
            Object content = message.getContent();
            if (content instanceof String) {
                result = (String) content;
            } else if (content instanceof MimeMultipart) {
                MimeMultipart mimeMultipart = (MimeMultipart) content;
                result = getTextFromMimeMultipart(mimeMultipart);
            }
        }
        return result;
    }

    private static String getTextFromMimeMultipart(MimeMultipart mimeMultipart) throws Exception {
        int count = mimeMultipart.getCount();
        if (count == 0)
            throw new MessagingException("Multipart with no body parts detected.");

        boolean multipartAlternative = new ContentType(mimeMultipart.getContentType()).match("multipart/alternative");
        if (multipartAlternative)
            return getTextFromBodyPart(mimeMultipart.getBodyPart(count - 1));

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < count; i++) {
            BodyPart bodyPart = mimeMultipart.getBodyPart(i);
            result.append(getTextFromBodyPart(bodyPart));
        }
        return result.toString();
    }

    private static String getTextFromBodyPart(BodyPart bodyPart) throws Exception {
        if (bodyPart.isMimeType("text/plain")) {
            return bodyPart.getContent().toString();
        } else if (bodyPart.isMimeType("text/html")) {
            String html = (String) bodyPart.getContent();
            return Jsoup.parse(html).text();
        } else if (bodyPart.getContent() instanceof MimeMultipart) {
            return getTextFromMimeMultipart((MimeMultipart) bodyPart.getContent());
        }
        return "";
    }

    private static int countAttachments(Multipart multipart) throws Exception {
        int count = 0;
        for (int i = 0; i < multipart.getCount(); i++) {
            BodyPart bodyPart = multipart.getBodyPart(i);
            if (Part.ATTACHMENT.equalsIgnoreCase(bodyPart.getDisposition())) {
                count++;
            }
        }
        return count;
    }

    private static void downloadAttachments(Multipart multipart, String downloadPath) throws Exception {
        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < multipart.getCount(); i++) {
            BodyPart bodyPart = multipart.getBodyPart(i);
            if (Part.ATTACHMENT.equalsIgnoreCase(bodyPart.getDisposition())) {
                String fileName = bodyPart.getFileName();
                System.out.print("是否下载附件 \"" + fileName + "\"? (y/n): ");
                String downloadChoice = scanner.next();
                if (downloadChoice.equalsIgnoreCase("y")) {
                    String filePath = downloadPath + fileName;
                    saveAttachment(bodyPart, filePath);
                    System.out.println("附件已下载: " + filePath);
                }
            }
        }
    }

    private static void saveAttachment(BodyPart bodyPart, String filePath) throws Exception {
        InputStream inputStream = bodyPart.getInputStream();
        OutputStream outputStream = new FileOutputStream(new File(filePath));

        byte[] buffer = new byte[8192];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }

        outputStream.close();
        inputStream.close();
    }
}
