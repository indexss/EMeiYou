package me.indexss;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

public class EmailClient {
    private static final String POP3_SERVER = "pop3.csu.edu.cn";
    private static final String EMAIL = "8208211408@csu.edu.cn";
    private static final String PASSWORD = "1941006006Qq";
    private static final String ATTACHMENT_DIR = "/Users/shilinli/Desktop/fj";

    public static void main(String[] args) throws MessagingException, IOException {
        EmailClient client = new EmailClient();
        client.displaySubjects();

        int index = 1; // 从用户接收的索引号
        client.displayEmailDetails(index);
        client.downloadAttachments(index);
    }

    private void displaySubjects() throws MessagingException {
        Folder inbox = getInboxFolder();
        inbox.open(Folder.READ_ONLY);

        int messageCount = inbox.getMessageCount();
        for (int i = 1; i <= messageCount; i++) {
            Message message = inbox.getMessage(i);
            System.out.printf("%d. %s\n", i, message.getSubject());
        }

        inbox.close(false);
        inbox.getStore().close();
    }

    private void displayEmailDetails(int index) throws MessagingException, IOException {
        Folder inbox = getInboxFolder();
        inbox.open(Folder.READ_ONLY);

        int messageCount = inbox.getMessageCount();
        if (index < 1 || index > messageCount) {
            System.out.println("索引号范围不合法");
            return;
        }

        Message message = inbox.getMessage(index);
        System.out.println("主题: " + message.getSubject());
        System.out.println("发件人: " + message.getFrom()[0]);
        System.out.println("发件时间: " + message.getSentDate());

        Object content = message.getContent();
        if (content instanceof String) {
            System.out.println("邮件内容: " + content);
        } else if (content instanceof MimeMultipart) {
            MimeMultipart multipart = (MimeMultipart) content;
            for (int i = 0; i < multipart.getCount(); i++) {
                MimeBodyPart part = (MimeBodyPart) multipart.getBodyPart(i);
                if (part.isMimeType("text/plain")) {
                    System.out.println("邮件内容: " + part.getContent());
                }
            }
        }

        System.out.println("是否有附件: " + hasAttachments(message));

        inbox.close(false);
        inbox.getStore().close();
    }

    private void downloadAttachments(int index) throws MessagingException, IOException {
        Folder inbox = getInboxFolder();
        inbox.open(Folder.READ_ONLY);

        int messageCount = inbox.getMessageCount();
        if (index < 1 || index > messageCount) {
            System.out.println("索引号范围不合法");
            return;
        }

        Message message = inbox.getMessage(index);
        if (hasAttachments(message)) {
            Object content = message.getContent();
            if (content instanceof MimeMultipart) {
                MimeMultipart multipart = (MimeMultipart) content;
                for (int i = 0; i < multipart.getCount(); i++) {
                    MimeBodyPart part = (MimeBodyPart) multipart.getBodyPart(i);
                    if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
                        String fileName = part.getFileName();
                        File file = new File(ATTACHMENT_DIR, fileName);
                        part.saveFile(file);
                    }
                }
            }
        }

        inbox.close(false);
        inbox.getStore().close();
    }

    private boolean hasAttachments(Message message) throws MessagingException, IOException {
        Object content = message.getContent();
        if (content instanceof MimeMultipart) {
            MimeMultipart multipart = (MimeMultipart) content;
            for (int i = 0; i < multipart.getCount(); i++) {
                MimeBodyPart part = (MimeBodyPart) multipart.getBodyPart(i);
                if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
                    return true;
                }
            }
        }
        return false;
    }

    private Folder getInboxFolder() throws MessagingException {
        Properties properties = new Properties();
        properties.setProperty("mail.store.protocol", "pop3");
        properties.setProperty("mail.pop3.host", POP3_SERVER);
        properties.setProperty("mail.pop3.port", "995");
        properties.setProperty("mail.pop3.ssl.enable", "true");

        Session session = Session.getDefaultInstance(properties);
        Store store = session.getStore("pop3");
        store.connect(EMAIL, PASSWORD);

        return store.getFolder("INBOX");
    }
}
