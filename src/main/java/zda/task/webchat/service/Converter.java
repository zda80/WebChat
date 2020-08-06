package zda.task.webchat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Service;
import zda.task.webchat.model.ChatMessage;
import zda.task.webchat.model.ChatMessageDto;

/**
 * Converter from ChatMessage to ChatMessageDto ans back
 *
 * @author Zhevnov D.A.
 * @ created 2020-08-08
 */
@Service
public class Converter {

    @Autowired
    private UserService userService;

    @Lookup
    public ChatMessage getChatmessage() {
        return null;
    }

    @Lookup
    public ChatMessageDto getChatmessageDto() {
        return null;
    }

    public ChatMessage toChatMessage(ChatMessageDto chatMessageDto){
        ChatMessage chatMessage = getChatmessage();

        chatMessage.setUser(userService.findUserByUsername(chatMessageDto.getUsername()));
        chatMessage.setType(chatMessageDto.getType());
        chatMessage.setMessage(chatMessageDto.getMessage());

        return chatMessage;
    }

    public ChatMessageDto toChatMessageDto(ChatMessage chatMessage){
        ChatMessageDto chatMessageDto = getChatmessageDto();

        chatMessageDto.setDate(chatMessage.getDate());
        chatMessageDto.setUsername(chatMessage.getUser().getUsername());
        chatMessageDto.setType(chatMessage.getType());
        chatMessageDto.setMessage(chatMessage.getMessage());

        return chatMessageDto;
    }
}