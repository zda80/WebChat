package zda.task.webchat.model;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Users in Room class
 *
 * @author Zhevnov D.A.
 * @ created 2020-08-08
 */
@Component
public class UsersInRoom {

    private List<User> users = new ArrayList<>();

    public UsersInRoom() {
        this.users = users;
    }

    public boolean add(User user) {
        return users.add(user);
    }

    public boolean remove(User user) {
        for (User e : users)
            if (user.getUsername().equals(e.getUsername())) return users.remove(e);
        return false;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}