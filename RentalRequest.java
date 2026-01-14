
import java.util.*;
import java.time.LocalDate;

public class RentalRequest{
    private String requestID;
    private Room requestRoom;
    private Student requestStudent;
    private RentalRequestStatus requestStatus;
    private BookingSlot requestSlot;

    public RentalRequest(String requestID, Room requestRoom, Student requestStudent, BookingSlot requestSlot){
        this.requestID = requestID;
        this.requestRoom = requestRoom;
        this.requestStudent = requestStudent;
        this.requestSlot = requestSlot;
        this.requestStatus = RentalRequestStatus.REQUESTED;

    }

    //getters
    public String getRequestID(){
        return requestID;
    }
    public Room getRequestRoom(){
        return requestRoom;
    }
    public Student getRequestStudent(){
        return requestStudent;
    }
    public RentalRequestStatus getRentalRequestStatus(){
        return requestStatus;
    }
    public BookingSlot getRentalRequestBookingSlot(){
        return requestSlot;
    }

    //setters
    public void setRequestID(String newRequestID){
        requestID = newRequestID;
    }
    public void setRequestRoom(Room newRequestRoom){
        requestRoom = newRequestRoom;
    }
    public void setRequestStudent(Student newRequestStudent){
        requestStudent = newRequestStudent;
    }
    public void setRequestBookingSlot(BookingSlot newRequestSlot){
        requestSlot = newRequestSlot;
    }
    public void setRequestToAccepted(){
        requestStatus = RentalRequestStatus.ACCEPTED;
    }

    //changes the requests to accepted if only if it is currently requested
    public void acceptRequest(){
        if(requestStatus == RentalRequestStatus.REQUESTED){
            requestStatus = RentalRequestStatus.ACCEPTED;
        }else{
            System.out.println("Request status already accepted/denied");
        }
    }
    //changes the requests staus to denied only if the requests status is requested
    public void denyRequest(){
        if(requestStatus == RentalRequestStatus.REQUESTED){
            requestStatus = RentalRequestStatus.DENIED;
        }else{
            System.out.println("Request status already accepted/denied");
        }
    }

    //outputs the request in nice format
    public void displayRequest(){
        System.out.println("Request ID:"+ requestID);
        System.out.println("Request Room ID:" + requestRoom.getRoomID());
        System.out.println("Request slot:" + requestSlot.bookingSlotString());
        System.out.println("Request status:" + requestStatus);
    }

}