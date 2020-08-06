package zda.task.webchat.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import zda.task.webchat.service.UserService;

import java.time.LocalDateTime;

/**
 * ChatMessage Data Transfer Object (used on the client side)
 *
 * @author Zhevnov D.A.
 * @ created 2020-08-08
 */
@Component
@Scope("prototype")
public class ChatMessageDto {

    @Autowired
    ChatMessage chatMessage;

    @Autowired
    private UserService userService;

    private Long id;

    private String username;

    private MessageType type;

    private String message;

    @Value("#{T(java.time.LocalDateTime).now()}")
    private LocalDateTime date;

    public ChatMessageDto() {
    }

    public ChatMessageDto(MessageType type, String message, String username) {
        this.username = username;
        this.type = type;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}