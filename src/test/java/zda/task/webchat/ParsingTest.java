package zda.task.webchat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.junit4.SpringRunner;
import zda.task.webchat.model.*;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

/**
 * Test for Parsing from JSON
 *
 * @author Zhevnov D.A.
 * @ created 2020-08-08
 */
@JsonTest
@RunWith(SpringRunner.class)
public class ParsingTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JacksonTester<ChatMessageDto> json;

    @Test
    public void givenChatMessageDto_whenStringifyChatMessageDto_thenGetJSON() throws JsonProcessingException {
        ChatMessageDto chatMessageDto = new ChatMessageDto(MessageType.CHAT, "Привет!", "User1");
        chatMessageDto.setDate(LocalDateTime.parse("2020-04-08T23:09:17"));
        chatMessageDto.setId(1L);
        String expectedResult = "{\"id\":1,\"username\":\"User1\",\"type\":\"CHAT\",\"message\":\"Привет!\",\"date\":\"2020-04-08T23:09:17\"}";

        assertEquals(expectedResult, objectMapper.writeValueAsString(chatMessageDto));
    }

    @Test
    public void givenJSON_whenParsingJSON_thenGetChatMessageDto() throws IOException {
        ChatMessageDto expectedMessageDto = new ChatMessageDto(MessageType.CHAT, "Привет!", "User1");
        expectedMessageDto.setDate(LocalDateTime.parse("2020-04-08T23:09:17"));
        expectedMessageDto.setId(1L);
        String JSONString = "{\"id\":1,\"username\":\"User1\",\"type\":\"CHAT\",\"message\":\"Привет!\",\"date\":\"2020-04-08T23:09:17\"}";

        ChatMessageDto resultMessage = objectMapper.readValue(JSONString,  ChatMessageDto.class);

        assertEquals(resultMessage.getMessage(), expectedMessageDto.getMessage());
        assertEquals(resultMessage.getUsername(), expectedMessageDto.getUsername());
        assertEquals(resultMessage.getType(), expectedMessageDto.getType());
        assertEquals(resultMessage.getDate(), expectedMessageDto.getDate());
        assertEquals(resultMessage.getId(), expectedMessageDto.getId());
    }
}
