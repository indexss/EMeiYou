package me.indexss;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import java.io.*;
import java.util.Properties;

public class InBoxService {

    public static void main(String[] args) throws Exception {
        InBoxService inBoxService = new InBoxService("8208211408@csu.edu.cn", "1941006006Qq", "pop3.csu.edu.cn", "110");
//        inBoxService.receiveMail();
        inBoxService.loadList();
//        inBoxService.printList();
//        inBoxService.checkAttachment(137);
//        inBoxService.getContent(137);
//        inBoxService.getAttach(137);
    }
    public static String subject1;
    public static String filename1;
    public static String from1;
    public static String date1;
    public static String content1;
    private String account;
    private String password;
    private String POP3Server;
    private String POP3Port;
    private Properties properties;
    public static Store store;
    public static Message[] messages;

    public static String[] getTitles() {
        return titles;
    }


    public static void setTitles(String[] titles) {
        InBoxService.titles = titles;
    }

    public static String[] titles = null;
    private int messageCount;


    public InBoxService(String mailAddr, String pwd, String pop3Addr) throws Exception {
    }

    public InBoxService(String account, String password, String POP3Server, String POP3Port) {
        this.account = account;
        this.password = password;
        this.POP3Server = POP3Server;
        this.POP3Port = POP3Port;
        this.properties = new Properties();

        properties.put("mail.store.protocol", "pop3");
        properties.put("mail.pop3.class", "com.sun.mail.pop3.POP3Store");
        properties.put("mail.imap.class", "com.sun.mail.imap.IMAPStore");
        properties.setProperty("mail.pop3.starttls.enable", "true");
//        properties.put("mail.pop3.host", POP3Server);
        try {
            Session session = Session.getDefaultInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(account, password);
                }
            });

            store = session.getStore("pop3");
            store.connect(POP3Server, account, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Message[] getMessages() {
        return messages;
    }

    public int getMessageCount() {
        return messageCount;
    }

    public Store getStore() {
        return store;
    }

    public void printList() throws MessagingException {
        for(int i = 0; i < messages.length; i++){
            System.out.println("[" + i + "]" + " " + messages[i].getSubject());
        }
    }

    public static void deleteMail(int index) throws MessagingException {
        System.out.println(store);
        Folder inbox = store.getFolder("INBOX");
        inbox.open(Folder.READ_WRITE);

        // 获取指定索引号的邮件
        Message message = inbox.getMessage(index+1);

        // 将邮件标记为已删除
        message.setFlag(Flags.Flag.DELETED, true);

        // 保存更改
//        inbox.expunge();

        // 关闭文件夹
        inbox.close(true);

    }


    public void getAttach(int index) throws Exception {
        Message message = messages[index];
        try {
            Object content = message.getContent();
            if (content instanceof Multipart) {
                Multipart multipart = (Multipart) content;
                for (int j = 0; j < multipart.getCount(); j++) {
                    BodyPart bodyPart = multipart.getBodyPart(j);
                    String disposition = bodyPart.getDisposition();
                    if(disposition != null && (disposition.equalsIgnoreCase(Part.ATTACHMENT) || disposition.equalsIgnoreCase(Part.INLINE))){
                        InputStream is = bodyPart.getInputStream();
                        BufferedInputStream bis = new BufferedInputStream(is);
                        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File("/Users/shilinli/Desktop/fj/" + bodyPart.getFileName())));
                        int index1 = -1;
                        while((index1 = bis.read()) != -1){
                            bos.write(index1);
                            bos.flush();
                        }
                        bos.close();
                        bis.close();
                    }
                    String fileName = bodyPart.getFileName();
                    if (fileName != null) {
                    } else {
                        Object body = bodyPart.getContent();
                        if (body instanceof Multipart) {
                            for (int k = 0; k < ((Multipart) body).getCount(); k++) {
                                BodyPart bodyPartTest = ((Multipart) body).getBodyPart(k);
                                if (bodyPartTest.isMimeType("text/plain")) {
                                }
                            }
                        }
                    }
                }
            } else {}
        }catch (Exception e){}
    }

    public void getContent(int index) throws Exception {
        content1 = "原邮件为富文本，无法在客户端展示！";
        Message message = messages[index];
        subject1 = message.getSubject();
        from1 = InternetAddress.toString(message.getFrom());
        try {
            String fileName = null;
            date1 = message.getSentDate().toString();
            Object content = message.getContent();
            if (content instanceof Multipart) {
                Multipart multipart = (Multipart) content;
                for (int j = 0; j < multipart.getCount(); j++) {
                    BodyPart bodyPart = multipart.getBodyPart(j);
                    String disposition = bodyPart.getDisposition();
                    if(disposition != null && (disposition.equalsIgnoreCase(Part.ATTACHMENT) || disposition.equalsIgnoreCase(Part.INLINE))){
                        fileName = bodyPart.getFileName();
                    }

                    // if the part is an attachment, display the filename
                    if (fileName != null) {
                        filename1 = fileName;

                    } else {
                        // if the part is not an attachment, display the content
                        Object body = bodyPart.getContent();
                        if (body instanceof Multipart) {
                            for (int k = 0; k < ((Multipart) body).getCount(); k++) {
                                BodyPart bodyPartTest = ((Multipart) body).getBodyPart(k);
                                if (bodyPartTest.isMimeType("text/plain")) {
                                    content1 = bodyPartTest.getContent().toString();
                                }
                            }
                        }
                    }
                }
            } else {
                // if the content is not a multipart message, display the content
                String body = content.toString();
                content1 = body;
            }
        }catch (Exception e){}
    }


    public void loadList() throws MessagingException {
        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(account, password);
            }
        });
        store = session.getStore("pop3");
        store.connect(POP3Server, account, password);

        Folder inbox = store.getFolder("INBOX");
        inbox.open(Folder.READ_WRITE);

        messageCount = inbox.getMessageCount();
        System.out.println("邮件数量：" + messageCount);
        messages = inbox.getMessages();
        titles = new String[messages.length];
        for(int i = 0; i < messages.length; i++){
            titles[i] = messages[i].getSubject();
//            System.out.println(titles[i]);
        }
    }
}
