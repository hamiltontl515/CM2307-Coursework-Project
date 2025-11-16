import java.time.LocalDate;

class Booking{
    private String UserID;
    private LocalDate Start;
    private LocalDate End;

    public Booking(String UserID, LocalDate Start, LocalDate End){
        this.UserID = UserID;
        this.Start = Start;
        this.End = End;
    }

    //getters
    public String getUserID(){
        return this.UserID;
    }
    public LocalDate getStart(){
        return this.Start;
    }
    public LocalDate getEnd(){
        return this.End;
    }

    public void printBooking(){
        System.out.println("userID: "+this.UserID+", Start Date: "+this.Start+", End Date: "+this.End);
    }
}