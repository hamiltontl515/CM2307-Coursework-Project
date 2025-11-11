public class User{
    private String UserID;
    private String Name;
    private String Email;

    //constructor

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


    public static void main(String[] args){
    }
}