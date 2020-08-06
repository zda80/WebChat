package zda.task.webchat.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import zda.task.webchat.model.*;
import zda.task.webchat.service.ChatMessageService;
import zda.task.webchat.service.Converter;
import zda.task.webchat.service.UserService;

/**
 * WebSocket listener
 *
 * @author Zhevnov D.A.
 * @ created 2020-08-08
 */
@Controller
public class WebSocketEventController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketEventController.class);

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @Autowired
    private UsersInRoom usersInRoom;

    @Autowired
    private UserService userService;

    @Autowired
    private ChatMessageService chatMessageService;

    @Autowired
    private SimpUserRegistry simpUserRegistry;

    @Autowired
    private Converter converter;

    @Lookup
    public ChatMessage getChatmessage() {
        return null;
    }

    public int getNumberOfSessions() {
        return simpUserRegistry.getUserCount();
    }

    @EventListener
    public void webSocketConnectListener(SessionConnectedEvent event) {
        LOGGER.info("New User connected, number of sessions: " + getNumberOfSessions());
    }

    @EventListener
    public void webSocketDisconnectListener(SessionDisconnectEvent event) {

        String username = (String) StompHeaderAccessor.wrap(event.getMessage()).getSessionAttributes().get("username");

        if (username != null) {
            LOGGER.info("User disconnected: " + username);
            User disconnectedUser = userService.findUserByUsername(username);

            ChatMessage chatMessage = getChatmessage();
            chatMessage.setType(MessageType.QUIT);
            chatMessage.setUser(disconnectedUser);
            chatMessageService.saveMessage(chatMessage);

            usersInRoom.remove(disconnectedUser);

            ChatMessageDto chatMessageDto = converter.toChatMessageDto(chatMessage);
            messagingTemplate.convertAndSend("/topic/chatRoom", chatMessageDto);
        }
    }
}