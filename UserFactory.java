public class UserFactory{
    public static User createUser(String Role, String UserID, String Name, String Email, String Password){
        switch (Role.toLowerCase()){
            case "student":
                return new Student(UserID, Name, Email, Password);
            case "homeowner":
                return new HomeOwner(UserID, Name, Email, Password);
            case "admin":
                return new Admin(UserID, Name, Email, Password);
            default:
                throw new IllegalArgumentException("no such role type exists");
        }
    }
}