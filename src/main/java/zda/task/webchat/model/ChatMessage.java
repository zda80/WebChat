package zda.task.webchat.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * ChatMessage Entity (used on the server)
 *
 * @author Zhevnov D.A.
 * @ created 2020-08-08
 */
@Component
@Scope("prototype")
@Entity
@Table(name = "messages")
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "message_type")
    private MessageType type;

    @Column(name = "message")
    private String message;

    @Column
    @Value("#{T(java.time.LocalDateTime).now()}")
    private LocalDateTime date;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public ChatMessage() {
    }

    public ChatMessage(MessageType type, String message, User user) {
        this.type = type;
        this.message = message;
        this.user = user;
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
    public Long getId() {
        return id;
    }

    public void setId(Long id) { }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}