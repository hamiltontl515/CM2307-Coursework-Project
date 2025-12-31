import java.util.*;

public class UserRepository{
    private HashMap<String, User> UsersID = new HashMap<>();

    public void AddUser(User NewUser){
        UsersID.put(NewUser.getUserID(), NewUser);
    }

    public User getUser(String UserID){
        return UsersID.get(UserID);
    }
}