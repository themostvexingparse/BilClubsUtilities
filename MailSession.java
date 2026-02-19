import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailSession {
    private Credentials credentials;
    private Properties properties;
    private Session session;

    public MailSession(Credentials credentials) {
        this.credentials = credentials;
        properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        session = Session.getInstance(properties, (new javax.mail.Authenticator() {
            @Override
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication(credentials.getUsername(), credentials.getPassword());
            }
        }));
    }

    public void send(MailMessage msg) {
         try {
            Address[] reciepents = msg.getRecipients();
            Message message = new MimeMessage(session);
            String type = msg.isHTML() ? "text/html; charset=utf-8" : "text/plain";
            message.setFrom(new InternetAddress(credentials.getUsername()));
            message.setRecipients(Message.RecipientType.TO, reciepents);
            message.setSubject(msg.getSubject());
            message.setContent(msg.getContent(), type);

            System.out.println("Sending email...");
            Transport.send(message);
            System.out.println("Email successfully sent!");

        } catch (MessagingException e) {
            System.err.println("Error sending email. Check your credentials and connection.");
            e.printStackTrace();
        }
    }

}
