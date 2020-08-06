package zda.task.webchat;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.test.context.junit4.SpringRunner;
import zda.task.webchat.controller.ChatMessagesController;
import zda.task.webchat.model.*;
import zda.task.webchat.service.ChatMessageService;
import java.util.HashMap;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

/**
 * Test for ChatMessageController
 *
 * @author Zhevnov D.A.
 * @ created 2020-08-08
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ChatMessageControllerTest {

    @Autowired
    ChatMessagesController chatMessagesController;

    @MockBean
    ChatMessageService chatMessageService;

    @MockBean
    UsersInRoom usersInRoom;

    @Test
    public void givenChatMessageDtoWithoutDate_whenSendChatMessageDto_thenSendChatMessageDtoWithDate() throws Exception {
        ChatMessageDto chatMessageDto = new ChatMessageDto(MessageType.CHAT, "Привет!", "User1");
        given(chatMessageService.saveMessage(any())).willReturn(new ChatMessage());
        given(usersInRoom.add(any())).willReturn(true);
        Assert.assertNotNull(chatMessagesController.sendMessage(chatMessageDto).getDate());
    }

    @Test
    public void givenNewUSerChatMessageDtoWithoutDate_whenSendNewUserChatMessageDtoWithoutDate_thenSendNewUserChatMessageDtoWithDate() throws Exception {
        ChatMessageDto chatMessageDto = new ChatMessageDto(MessageType.JOIN, null, "User1");
        given(chatMessageService.saveMessage(any())).willReturn(new ChatMessage());
        given(usersInRoom.add(any())).willReturn(true);

        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create();
        headerAccessor.setSessionAttributes(new HashMap<>());
        Assert.assertNotNull(chatMessagesController.newUser(chatMessageDto, headerAccessor).getDate());
    }
}





