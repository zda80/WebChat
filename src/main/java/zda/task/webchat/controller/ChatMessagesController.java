package zda.task.webchat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import zda.task.webchat.model.ChatMessage;
import zda.task.webchat.model.ChatMessageDto;
import zda.task.webchat.model.UsersInRoom;
import zda.task.webchat.service.ChatMessageService;
import zda.task.webchat.service.Converter;

/**
 * Chat message controller
 *
 * @author Zhevnov D.A.
 * @ created 2020-08-08
 */
@Controller
public class ChatMessagesController {

    @Autowired
    private ChatMessageService chatMessageService;

    @Autowired
    private UsersInRoom usersInRoom;

    @Autowired
    private Converter converter;

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/chatRoom")
    public ChatMessageDto sendMessage(@Payload ChatMessageDto chatMessageDto) {

        ChatMessage chatMessage = converter.toChatMessage(chatMessageDto);
        chatMessageService.saveMessage(chatMessage);
        chatMessageDto.setDate(chatMessage.getDate());

        return chatMessageDto;
    }

    @MessageMapping("/chat.newUser")
    @SendTo("/topic/chatRoom")
    public ChatMessageDto newUser(@Payload ChatMessageDto chatMessageDto, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", chatMessageDto.getUsername());

        ChatMessage chatMessage = converter.toChatMessage(chatMessageDto);
        chatMessageService.saveMessage(chatMessage);
        usersInRoom.add(chatMessage.getUser());
        chatMessageDto.setDate(chatMessage.getDate());

        return chatMessageDto;
    }
}