
public class UserManager{
    private UserRepository userRepo = new UserRepository();

    public UserManager(UserRepository userRepo){
        this.userRepo = userRepo;
    }

    //user attempts login, if it's in repo return true else return false
    public Boolean login(String username, String password){
        if(userRepo.userIDInRepo(username)){
            User loginUser = userRepo.getUser(username);

            if(loginUser.getPassword().equals(password)){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    //allows a user to sign up to the service, checking that the email suppplied isn't already used.
    public void signUp(String usertype, String name, String email, String password){
        if(!userRepo.userEmailInRepo(email)){
            switch(usertype.toLowerCase()){
                case "homeowner":
                    addNewHomeOwner(name, email, password);
                case "student":
                    addNewStudent(name, email, password);
                case "admin":
                    addNewAdmin(name, email, password);
            }
        }
    }

    //create a homeowner
    public void addNewHomeOwner(String name, String email, String password){
        User newHomeOwner = UserFactory.createUser("homeowner", userRepo.generateUserID(), name, email, password);
        userRepo.AddUser(newHomeOwner);
    }
    //create a student
    public void addNewStudent(String name, String email, String password){
        User newStudent = UserFactory.createUser("student", userRepo.generateUserID(), name, name, password);
        userRepo.AddUser(newStudent);
    }
    //create an admin
    public void addNewAdmin(String name, String email, String password){
        User newAdmin = UserFactory.createUser("admin", userRepo.generateUserID(), name, email, password);
        userRepo.AddUser(newAdmin);
    }
    //delete a user
    public void deleteUserByID(String userID){
        if(userRepo.userIDInRepo(userID)){
            userRepo.deleteUser(userID);
        }
    }

}