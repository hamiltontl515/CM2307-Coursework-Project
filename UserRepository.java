import java.util.*;

public class UserRepository{
    private HashMap<String, User> UsersID = new HashMap<>();
    private int IDIndex;

    public UserRepository(){
        this.IDIndex = 0;
    }

    public void setRepositoryIndex(int index){
        IDIndex = index;
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
    public User userIDInRepo(String userID){
        return UsersID.get(userID);
    }

    //iterates through repo checking for the presence of a specific email, all users must have different emails
    public User userEmailInRepo(String userEmail){
        for(User user: UsersID.values()){
            if(user.getEmail().equals(userEmail)){
                return user;
            }
        }
        return null;
    }

    public User getUser(String UserID){
        return UsersID.get(UserID);
    }

    public void userCount(){
        System.out.println(UsersID.size());
    }
}