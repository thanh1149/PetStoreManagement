package com.petstoremanagement.Service;

import com.petstoremanagement.Constant.EmailConstant;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailService {

    public void sendResetToken(String toEmail, String token) {
        // Email configuration
        String fromEmail = EmailConstant.EMAIL_CONSTANT; // Replace with your email
        String password = EmailConstant.PASSWORD_EMAIL; // Replace with your email password

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Password Reset Token");
            message.setText("Your password reset token is: " + token + "\nThis token will expire in 15 minutes.");

            Transport.send(message);

            System.out.println("Reset token sent successfully to " + toEmail);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
