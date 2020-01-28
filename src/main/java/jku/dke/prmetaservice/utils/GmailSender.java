package jku.dke.prmetaservice.utils;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class GmailSender {

    private String from;
    private String pass;

    public GmailSender(String from, String pass) {
        this.from = from;
        this.pass = pass;
    }

    public void sendMail(String recipient, String subject, String mail_text) {

        String host = "smtp.gmail.com";
        Properties properties = System.getProperties();

        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.user", this.from);
        properties.put("mail.smtp.password", this.pass);

        Session session = Session.getDefaultInstance(properties);

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(this.from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject(subject);
            message.setText(mail_text);
            Transport transport = session.getTransport("smtp");
            transport.connect(host, this.from, this.pass);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}
