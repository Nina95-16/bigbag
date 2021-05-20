package am.itspace.bigbag.service;


import javax.mail.MessagingException;

public interface EmailService {

    void sendWithAttachment(String to, String subject, String text, String filePath) throws MessagingException;

    void sendHtml(String to, String subject, String htmlContent) throws MessagingException;
}
