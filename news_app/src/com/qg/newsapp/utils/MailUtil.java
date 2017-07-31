package com.qg.newsapp.utils;

import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * 发送邮件工具类
 */
public class MailUtil {

    /**
     * 发送邮件
     *
     * @param recipient   收件人
     * @param subject     邮件标题
     * @param content     邮件内容
     * @param contentType 内容格式类型
     * @throws Exception
     */
    public void sendMail(final String recipient, final String subject,
                         final String content, final String contentType) throws Exception {
        try {
            Properties props = new Properties();
            MailSSLSocketFactory sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
            props.put("mail.smtp.ssl.enable", "true");
            props.put("mail.smtp.ssl.socketFactory", sf);
            props.put("username", "1594998836@qq.com");
            props.put("password", "ymocfsshdnqzhhjj");
            props.put("mail.transport.protocol", "smtp");
            props.put("mail.smtp.host", "smtp.qq.com");
            props.put("mail.smtp.port", "465");

            Session mailSession = Session.getDefaultInstance(props);

            Message msg = new MimeMessage(mailSession);
            msg.setFrom(new InternetAddress("1594998836@qq.com"));
            msg.addRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(recipient));
            msg.setSubject(subject);
            msg.setContent(content, contentType);
            msg.saveChanges();

            Transport transport = mailSession.getTransport("smtp");
            transport.connect(props.getProperty("mail.smtp.host"),
                    props.getProperty("username"),
                    props.getProperty("password"));
            transport.sendMessage(msg, msg.getAllRecipients());
            transport.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
