package zda.task.webchat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zda.task.webchat.model.ChatMessage;

import java.util.List;

/**
 * ChatMessageRepository interface
 *
 * @author Zhevnov D.A.
 * @ created 2020-08-08
 */
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findTop1000ByOrderByIdDesc();
}
