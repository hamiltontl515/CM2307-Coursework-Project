
import java.time.LocalDate;
import java.util.*;

public class Room{
    private String roomID;
    private String roomDescription;
    private Double pricePerWeek;
    private List<BookingSlot> bookings;

    public Room(String roomID, String roomDescription, Double pricePerWeek){
        this.roomID = roomID;
        this.roomDescription = roomDescription;
        this.pricePerWeek = pricePerWeek;
        this.bookings = new ArrayList<>();
    }

    //getters
    public String getRoomID(){
        return roomID;
    }
    public String getRoomDescription(){
        return roomDescription;
    }
    public Double getPricePerWeek(){
        return pricePerWeek;
    }
    public List<BookingSlot> getBookings(){
        return bookings;
    }

    //setters
    public void setRoomID(String newRoomID){
        roomID = newRoomID;
    }
    public void setRoomDescription(String newDescription){
        roomDescription = newDescription;
    }
    public void setPricePerWeek(Double newPricePerWeek){
        pricePerWeek = newPricePerWeek;
    }

    //this method iterates through the rooms bookings to assertain whether the proposed start and end dates conflict with current bookings
    public Boolean checkAvailability(LocalDate start, LocalDate end){
        for(BookingSlot booking : bookings){
            if(booking.checkOverlap(start, end)){
                return false;
            }
        }
        return true;
    }

    //this method adds a booking to the list of booking slots, checking for overlap
    public void addBooking(LocalDate start, LocalDate end){
        if(checkAvailability(start, end)){
            bookings.add(new BookingSlot(start, end));
        }else{
            System.out.println("unfortunately, room is already booked, try other dates or another room.");
        }
    }

    public void displayRoom(){
        System.out.println("Room description:"+roomDescription);
        System.out.println("Room Price:£"+ pricePerWeek);
    }


}