package com.maoqis;

import com.maoqis.utils.Log4jUtil;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
@Path("email")
public class SendEmail {
    public static void main(String [] args)
    {
        String title = "This is the Subject Line!";
        String mgs = "This is actual message";
        sendMailToMe(title,mgs);
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getMethod(){
        return sendMailToMe("test","test");
    }
    public static String sendMailToMe(String title,String mgs) {

        Log4jUtil.info("sendMailToMe "+title);
        // 收件人电子邮箱
        String to = "495714448@qq.com";

        // 发件人电子邮箱
        final String xxx = "158522061";
        String from = xxx + "@qq.com";

        // 指定发送邮件的主机为 smtp.qq.com
        String host = "smtp.qq.com";  //QQ 邮件服务器

        // 获取系统属性
        Properties properties = System.getProperties();

        // 设置邮件服务器
        properties.setProperty("mail.smtp.host", host);

        properties.put("mail.smtp.auth", "true");
        // 获取默认session对象
        Session session = Session.getDefaultInstance(properties,new Authenticator(){
            public PasswordAuthentication getPasswordAuthentication()
            {
                return new PasswordAuthentication(xxx + "@qq.com", "wmhtdsoswyyebhjf"); //发件人邮件用户名、授权码
            }
        });

        try{
            // 创建默认的 MimeMessage 对象
            MimeMessage message = new MimeMessage(session);

            // Set From: 头部头字段
            message.setFrom(new InternetAddress(from));

            // Set To: 头部头字段
            message.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(to));

            // Set Subject: 头部头字段

            message.setSubject(title);

            // 设置消息体

            message.setText(mgs);

            // 发送消息
            Transport.send(message);
            Log4jUtil.info("Sent message successfully....");

        }catch (MessagingException mex) {
            mex.printStackTrace();
            return mex.toString();
        }
        return "ok";
    }
}
