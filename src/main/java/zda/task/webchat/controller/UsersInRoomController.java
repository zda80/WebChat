package zda.task.webchat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import zda.task.webchat.model.User;
import zda.task.webchat.model.UsersInRoom;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Room User List Controller
 *
 * @author Zhevnov D.A.
 * @ created 2020-08-08
 */
@RestController
@RequestMapping("/app")
public class UsersInRoomController {

    @Autowired
    private UsersInRoom usersInRoom;

    @RequestMapping(path = "/messages.usersInRoom", method = RequestMethod.POST)
    List<String> getAllUsersInRoom() {
        return usersInRoom.getUsers().stream().map(User::getUsername).collect(Collectors.toList());
    }
}