package zda.task.webchat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import zda.task.webchat.model.ChatMessageDto;
import zda.task.webchat.service.ChatMessageService;
import zda.task.webchat.service.Converter;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Chat Login Controller
 *
 * @author Zhevnov D.A.
 * @ created 2020-08-08
 */
@RestController
@RequestMapping("/app")
public class OldMessagesController {

    @Autowired
    private ChatMessageService chatMessageService;

    @Autowired
    private Converter converter;

    @RequestMapping(path = "/messages.oldMessages", method = RequestMethod.POST)
    List<ChatMessageDto> getAllMessage() {
        return chatMessageService.findLast1000By()
                .stream()
                .map(converter::toChatMessageDto)
                .collect(Collectors.toList());
    }
}