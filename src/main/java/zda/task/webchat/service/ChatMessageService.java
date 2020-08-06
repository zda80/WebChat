package zda.task.webchat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zda.task.webchat.model.ChatMessage;
import zda.task.webchat.repository.ChatMessageRepository;

import java.util.Collections;
import java.util.List;

/**
 * Service for saving and searching ChatMessages
 *
 * @author Zhevnov D.A.
 * @ created 2020-08-08
 */
@Service
public class ChatMessageService {

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    public ChatMessage saveMessage(ChatMessage chatMessage) {
        return chatMessageRepository.save(chatMessage);
    }

    public List<ChatMessage> findAll() {
        return chatMessageRepository.findAll();
    }

    public List<ChatMessage> findLast1000By() {
        List<ChatMessage> usersList = chatMessageRepository.findTop1000ByOrderByIdDesc();
        Collections.reverse(usersList);
        return usersList;
    }
}