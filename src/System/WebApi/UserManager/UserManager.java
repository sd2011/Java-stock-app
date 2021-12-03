package System.WebApi.UserManager;
import System.SystemProxy;


import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class UserManager {

        private final Set<String> usersSet;
        private final SystemProxy prox;

    public UserManager(SystemProxy prox) {
        usersSet = new HashSet<>();
        this.prox = prox;
    }

    public boolean isUserExists(String userNameFromParmeter) {
            return usersSet.contains(userNameFromParmeter);
    }

    public synchronized void addUser(String userName, String userType) throws Throwable {
        usersSet.add(userName);
        boolean userTypeInBool = userType.equals("trader");
        this.prox.invoke("load" , userName, userTypeInBool);
    }

    public Set<String> getUsers() {
        return Collections.unmodifiableSet(usersSet);
    }

    public boolean getUserType(String userName) throws Throwable {
        return this.prox.invoke("getUserType" , userName);
    }
}
