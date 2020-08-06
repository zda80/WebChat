package zda.task.webchat;

import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import zda.task.webchat.config.RoleConfig;
import zda.task.webchat.model.*;
import zda.task.webchat.service.Converter;
import zda.task.webchat.service.UserService;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

/**
 * Test for Converter
 *
 * @author Zhevnov D.A.
 * @ created 2020-08-08
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class ConverterTests {

	@Autowired
	public Converter converter;

	@MockBean
	private UserService userService;

	@MockBean
	private RoleConfig roleConfig;

	public ChatMessage chatMessage;

	public ChatMessageDto chatMessageDto;

	public User user;

	@Before
	public void setUp() {
		user = new User("User1", "password", "password");
		chatMessage= new ChatMessage(MessageType.CHAT, "Привет!", user);
		chatMessageDto = new ChatMessageDto(MessageType.CHAT, "Привет!", "User1");
		given(userService.isRoleExists(any())).willReturn(true);
		given(userService.findUserByUsername("User1")).willReturn(user);
		given(userService.findUserByUsername("User1")).willReturn(user);
	}

	@Test
	public void givenChatMessage_whenConvertChatMessage_thenGetChatMessageDto() {
		ChatMessageDto newChatMessageDto = converter.toChatMessageDto(chatMessage);
		assertEquals(newChatMessageDto.getUsername(), chatMessageDto.getUsername());
		assertEquals(newChatMessageDto.getType(), chatMessageDto.getType());
		assertEquals(newChatMessageDto.getMessage(),chatMessageDto.getMessage());
	}

	@Test
	public void givenChatMessageDto_whenConvertChatMessageDto_thenGetChatMessage() {
		ChatMessage newChatMessage = converter.toChatMessage(chatMessageDto);
		assertEquals(newChatMessage.getUser().getUsername(),chatMessage.getUser().getUsername());
		assertEquals(newChatMessage.getType(), chatMessage.getType());
		assertEquals(newChatMessage.getMessage(), chatMessage.getMessage());
	}
}