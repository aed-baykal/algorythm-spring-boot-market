package ru.gb.springbootmarket.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.gb.springbootmarket.enums.EmailType;
import ru.gb.springbootmarket.model.MessageTextElement;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service
public class EmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);
    private final JavaMailSender mailSender;
    private final MessageService messageService;
    private final UserService userService;

    public EmailService(JavaMailSender mailSender, MessageService messageService, UserService userService) {
        this.mailSender = mailSender;
        this.messageService = messageService;
        this.userService = userService;
    }

    @Async
    public void sendMail(EmailType emailType, Map<String, Object> params, Collection<String> receivers) {
        switch (emailType) {
            case USER_REGISTRATION:
                receivers.forEach(receiver -> sendVerificationLink(receiver, params));
                break;
            case USER_ORDER_CREATED:
                receivers.forEach(receiver -> sendOrderDetailsToUser(receiver, params));
                break;
            case MANAGER_ORDER_CREATED:
                receivers.forEach(receiver -> sendOrderDetailsToManager(receiver, params));
                break;
        }
    }

    private void sendOrderDetailsToManager(String to, Map<String, Object> params) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setTo(to);
            helper.setFrom("geek-market@gb.ru");
            helper.setSubject("Поступил новый заказ");
            helper.setText(textBuilder(to, params, "Поступил новый заказ"), false);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            LOGGER.error("failed to send mail ", e);
        }
    }

    private String textBuilder(String to, Map<String, Object> params, String key) {
        StringBuilder messageText = new StringBuilder();
        List<MessageTextElement> textElements = messageService.getMessageByTitle("Приветствие");
        textElements.forEach(messageTextElement -> {
            messageText.append(messageTextElement.getText());
            if (messageTextElement.getText().contains("день, "))
                if (userService.findByEmail(to) == null) messageText.append(to);
                else messageText.append(userService.findByEmail(to).getLogin());
        });
        if (key.equals("Подтвердите ваш email")) messageText.append("<br />");
        else messageText.append("\n");
        textElements = messageService.getMessageByTitle(key);
        textElements.forEach(messageTextElement -> {
            messageText.append(messageTextElement.getText());
            if (messageTextElement.getText().contains("№"))
                messageText.append(params.get("orderId"));
            if (messageTextElement.getText().contains("на сумму "))
                messageText.append(params.get("price"));
            if (messageTextElement.getText().contains("token="))
                messageText.append(params.get("token").toString());
            if (messageTextElement.getText().contains("ссылке."))
                messageText.append("<br />");
        });
        if (key.equals("Подтвердите ваш email")) messageText.append("<br />");
        else messageText.append("\n");
        textElements = messageService.getMessageByTitle("Подпись");
        textElements.forEach(messageTextElement -> messageText.append(messageTextElement.getText()));
        return messageText.toString();
    }

    private void sendOrderDetailsToUser(String to, Map<String, Object> params) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setTo(to);
            helper.setFrom("geek-market@gb.ru");
            helper.setSubject("Заказ успешно сформирован");
            helper.setText(textBuilder(to, params, "Заказ успешно сформирован"), false);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            LOGGER.error("failed to send mail ", e);
        }
    }

    private void sendVerificationLink(String to, Map<String, Object> params) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setTo(to);
            helper.setFrom("geek-market@gb.ru");
            helper.setSubject("Подтвердите ваш email");
            helper.setText(textBuilder(to, params, "Подтвердите ваш email"), true);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            LOGGER.error("failed to send mail ", e);
        }
    }

}
