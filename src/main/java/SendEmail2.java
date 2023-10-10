import java.io.File;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class SendEmail2{
    public static void main(String [] args){
        String to = "740506765@qq.com";									// 收件人电子邮
        String from = "8208211408@csu.edu.cn";									// 发件人电子邮箱
        String host = "smtp.csu.edu.cn"; 								// 指定发送邮件的主机为 smtp.qq.com，QQ 邮件服务器
        Properties properties = System.getProperties();				// 获取系统属性
        properties.setProperty("mail.smtp.host", host);				// 设置邮件服务器
        properties.put("mail.smtp.auth", "true");					// 开启认证
        Session session = Session.getDefaultInstance(properties,new Authenticator(){
            public PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication("8208211408@csu.edu.cn", "1941006006Qq");	 	//发件人邮件用户名、授权码
            }
        });																			// 获取默认session对象，通过认证
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        try{
            MimeMessage message = new MimeMessage(session);							// 创建默认的 MimeMessage 对象
            message.setFrom(new InternetAddress(from));								// Set From: 头部头字段
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));		// Set To: 头部头字段
            message.setSubject("测试附件标题");								// Set Subject: 头部头字段
            messageBodyPart.setText("测试附件");
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            messageBodyPart = new MimeBodyPart();
            DataSource source = new FileDataSource(new File("/Users/shilinli/Desktop/Projects/javaProjects/EMeiYou/src/main/java/me/indexss/LinliShi_CV_en_v2.pdf"));
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName("CV.pdf");
            multipart.addBodyPart(messageBodyPart);
            message.setContent(multipart);
            // 设置消息体
            Transport.send(message);														// 发送消息
        }catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}
