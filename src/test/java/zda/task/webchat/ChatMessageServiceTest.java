package zda.task.webchat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import zda.task.webchat.model.ChatMessage;
import zda.task.webchat.model.MessageType;
import zda.task.webchat.model.Role;
import zda.task.webchat.model.User;
import zda.task.webchat.service.ChatMessageService;
import zda.task.webchat.service.UserService;

import javax.transaction.Transactional;
import java.util.Collections;

import static org.junit.Assert.*;

/**
 * Test for ChatMessageService
 *
 * @author Zhevnov D.A.
 * @ created 2020-08-08
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional
public class ChatMessageServiceTest {

    @Autowired
    ChatMessageService chatMessageService;

    @Autowired
    UserService userService;

    User user;

    @Before
    public void setUp() {
        user = new User("User", "password", "password");
        user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
        userService.saveUser(user);
    }

    @Test
    public void givenChatMessage_whenSaveChatMessage_thenChatMessageSaveInDAO() {
        ChatMessage chatMessage = new ChatMessage(MessageType.CHAT, "Привет!", user);
        ChatMessage saveChatMessage = chatMessageService.saveMessage(chatMessage);
        assertEquals(saveChatMessage, chatMessage);
        assertTrue(chatMessageService.findAll().contains(chatMessage));
    }

    @Test
    public void givenChatMessages_whenFindAllChatMessages_thenGetAllChatMessages() {
        ChatMessage chatMessage;
        for (int i = 0; i < 1500; i++) {
            chatMessage = new ChatMessage(MessageType.CHAT, i + "Привет!", user);
            chatMessageService.saveMessage(chatMessage);
        }
        assertTrue(chatMessageService.findAll().size() == 1500);
    }

    @Test
    public void givenChatMessages_whenFind100ChatMessages_thenGet100ChatMessages() {
        ChatMessage chatMessage;
        for (int i = 0; i < 1500; i++) {
            chatMessage = new ChatMessage(MessageType.CHAT, i + "Привет!", user);
            chatMessageService.saveMessage(chatMessage);
        }
     assertTrue(chatMessageService.findLast1000By().size() == 1000);
    }
}