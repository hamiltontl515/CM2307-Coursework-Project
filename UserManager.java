
public class UserManager{
    private UserRepository userRepo = new UserRepository();

    public UserManager(UserRepository userRepo){
        this.userRepo = userRepo;
    }

    //user attempts login, if it's in repo and the password is correct return the user object, else exception errors
    public User login(String username, String password){
        User user = userRepo.userEmailInRepo(username);
        if(user == null){
            throw new IllegalArgumentException("user does not exist");
        }
        if(!user.getPassword().equals(password)){
            throw new IllegalArgumentException("password incorrect");
        }
        return user;
    }

    //allows a user to sign up to the service, checking that the email suppplied isn't already used.
    public void signUp(String usertype, String name, String email, String password){
        if(userRepo.userEmailInRepo(email) == null){
            switch(usertype.toLowerCase()){
                case "homeowner":
                    addNewHomeOwner(name, email, password);
                    break;
                case "student":
                    addNewStudent(name, email, password);
                    break;
                case "admin":
                    addNewAdmin(name, email, password);
                    break;
                default:
                    throw new IllegalArgumentException("user type non existant");
            }
        }else{
            throw new IllegalArgumentException("ERROR: email already exists");
        }
    }

    public void emailValidator(String email){
        if(email == null || !email.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")){ //regex that enforces email format, not starting in @, containing an @ and a . for .com, .co.uk etc.
            throw new IllegalArgumentException("ERROR: email formatting incorrect");
        }
    }
    public void nameValidator(String name){
        if(name == null || !name.matches("^[A-Za-z]+ [A-Za-z]+$")){
            throw new IllegalArgumentException("ERROR: name format incorrect");
        }
    }
    public void passwordValidator(String password){
        if(password == null || password.length() < 6){
            throw new IllegalArgumentException("ERROR: password must be over 6 characters");
        }
    }

    //create a homeowner
    public void addNewHomeOwner(String name, String email, String password){
        User newHomeOwner = UserFactory.createUser("homeowner", userRepo.generateUserID(), name, email, password);
        userRepo.AddUser(newHomeOwner);
    }
    //create a student
    public void addNewStudent(String name, String email, String password){
        User newStudent = UserFactory.createUser("student", userRepo.generateUserID(), name, email, password);
        userRepo.AddUser(newStudent);
    }
    //create an admin
    public void addNewAdmin(String name, String email, String password){
        User newAdmin = UserFactory.createUser("admin", userRepo.generateUserID(), name, email, password);
        userRepo.AddUser(newAdmin);
    }
    //delete a user
    public void deleteUserByID(String userID){
        if(userRepo.userIDInRepo(userID)!= null){
            userRepo.deleteUser(userID);
        }
    }

}