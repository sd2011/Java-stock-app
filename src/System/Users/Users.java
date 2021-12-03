package System.Users;

import System.RseUser;
import System.RseUsers;
import System.RseHoldings;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Users {

    Map<String,User> users;


    public Users(){
        users = new HashMap<>();
    }
    public Users(RseUsers usersToLoad){
        users = new HashMap<>();
        List<RseUser> RseList = usersToLoad.getRseUser();
        for (RseUser user: RseList)
        {
            users.put(user.getName(),new User(user));
        }
    }

    public int getUsersSize() {
        return this.users.size();
    }

    public User getUser(String name) {
        return this.users.get(name);
    }

    public User getUser(int i) {
        return this.users.get(this.users.keySet().toArray()[i]);
    }

    public void addUser(User user){
        this.users.put(user.getName(),user);
    }

    public boolean contains(String userName) {
        return this.users.containsKey(userName);
    }

    public void addUserHoldings(String userName , RseHoldings holdings) {
        this.users.get(userName).addHoldings(holdings);
    }
}

