package am.itspace.bigbag.serviceImpl;

import am.itspace.bigbag.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Component
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender emailSender;

    @Async
    public void sendWithAttachment(String to, String subject, String text, String filePath) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom("projecttestin1997@gmail.com");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text);
        File file = new File(filePath);
        FileSystemResource fileResource = new FileSystemResource(file);
        helper.addAttachment(file.getName(), fileResource);
        emailSender.send(message);

    }

    @Async
    public void sendHtml(String to, String subject, String htmlContent) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true,"UTF-8");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlContent, true);
        emailSender.send(message);
    }
}




