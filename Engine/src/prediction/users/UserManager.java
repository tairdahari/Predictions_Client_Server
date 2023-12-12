package prediction.users;

import prediction.clientRequest.RequestsManager;

import java.util.HashMap;
import java.util.Map;

public class UserManager {
    private final Map<String, User> usersMap = new HashMap<>();


    public UserManager() {
    }

    public synchronized void addUser(String username) {
        usersMap.put(username, new User(username));
    }

    public synchronized void removeUser(String username) {
        usersMap.remove(username);
    }

    public synchronized User getUser(String username) {
        return this.usersMap.get(username);
    }

    public synchronized Map<String, User> getUsers() {
        return usersMap;
    }

    public boolean isUserExists(String username) {
        return usersMap.containsKey(username);
    }

}
