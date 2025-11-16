
import java.util.List;

final class Student extends User{
    private int StudentID;
    private String University;
    private List<Booking> Bookings;

    public Student(String UserID, String Name, String Email, int StudentID, String University){
        super(UserID, Name, Email);
        setStudentID(StudentID);
        setUniversity(University);
    }
    //getters
    public int getStudentID(){
        return this.StudentID;
    }
    public String getUniversity(){
        return this.University;
    }

    //setters
    public void setStudentID(int replacement){
        this.StudentID = replacement;
    }
    public void setUniversity(String replacement){
        this.University = replacement;
    }
}