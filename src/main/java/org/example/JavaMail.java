package org.example;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class JavaMail{

    public static void main(String[] args) throws MessagingException {
        String to = "lmauricio@uniara.edu.br";
        String subject = "Cadastro finalizado!";
        String content = "Bem-vindo(a)";

        sendEmail(to, subject, content);
    }

    public static void sendEmail(String to, String subject, String content) throws MessagingException {
        String from = "leonardo.mauricio404@gmail.com";
        String password = "gucwpnrbnkxtdiax";

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(subject);
        message.setText(content);

        Transport.send(message);
    }
}
