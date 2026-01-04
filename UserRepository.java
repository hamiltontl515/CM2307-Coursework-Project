import java.util.*;

public class UserRepository{
    private HashMap<String, User> UsersID = new HashMap<>();
    private int IDIndex;

    public UserRepository(){
        this.IDIndex = 0;
    }

    public String generateUserID(){
        String paddedNum = String.format("%3d", IDIndex);
        IDIndex ++;
        return "U".concat(paddedNum);

    }

    public void AddUser(User NewUser){
        UsersID.put(NewUser.getUserID(), NewUser);
    }

    public void deleteUser(String userID){
        UsersID.remove(userID);
    }
    public Boolean userIDInRepo(String userID){
        return UsersID.containsKey(userID);
    }

    //iterates through repo checking for the presence of a specific email, all users must have different emails
    public Boolean userEmailInRepo(String userEmail){
        Boolean isIn = false;
        for(User user: UsersID.values()){
            if(user.getEmail() != null){
                isIn = true;
            }
        }
        return isIn;
    }

    public User getUser(String UserID){
        return UsersID.get(UserID);
    }
}