package ru.gb.springbootmarket.service;

import org.springframework.stereotype.Service;
import ru.gb.springbootmarket.model.MessageTextElement;
import ru.gb.springbootmarket.repository.MessageRepository;

import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public List<MessageTextElement> getMessageByTitle(String title){
        return messageRepository.getByTitleContaining(title);
    }
}