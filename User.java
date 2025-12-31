public abstract class User{
    protected String UserID;
    protected String Name;
    protected String Email;
    protected String Password;

    //constructor
    public User(String UserID, String Name, String Email, String Password){
        this.UserID = UserID;
        this.Name = Name;
        this.Email = Email;
        this.Password = Password;
    }

    //getters
    public String getUserID(){
        return UserID;
    }
    public String getName(){
        return Name;
    }
    public String getEmail(){
        return Email;
    }

    //setters
    public void setUserID(String Replacement){
        this.UserID = Replacement;
    }
    public void setName(String Replacement){
        this.Name = Replacement;
    }
    public void setEmail(String Replacement){
        this.Email = Replacement;
    }
    public void setPassword(String Replacement){
        this.Password = Replacement;
    }


    public static void main(String[] args){
    }
}