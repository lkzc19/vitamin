package org.example.mail;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Properties;

@NoArgsConstructor
@Data
public class MailSender {

    private String host;
    private String port = "465";
    private String username;
    private String password;

    private Session session;

    private static final String MIMETYPE_TEXT_PLAIN_UTF_8 = "text/plain; charset=utf-8";

    @Builder
    public MailSender(String host, String port, String username, String password) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        session = createSession(this.username, this.password);
    }

    private Properties prepareProperties() {
        Properties props = new Properties();
        props.put("mail.smtp.host", this.host);
        props.put("mail.smtp.port", this.port);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.enable", "true");
        return props;
    }

    private Session createSession(String username, String password) {
        Properties props = prepareProperties();

        return Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }

    public void send(String to, String subject, String content) throws MessagingException {
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(this.username));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
        message.setSubject(subject, StandardCharsets.UTF_8.name());
        message.setContent(content, MIMETYPE_TEXT_PLAIN_UTF_8);

        Transport.send(message);
    }
}
