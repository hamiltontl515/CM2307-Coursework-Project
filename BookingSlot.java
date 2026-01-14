
import java.time.LocalDate;

public class BookingSlot{
    private LocalDate startDate;
    private LocalDate endDate;

    public BookingSlot(LocalDate startDate, LocalDate endDate){
        this.startDate = startDate;
        this.endDate = endDate;
    }

    //getters
    public LocalDate getStartDate(){
        return this.startDate;
    }
    public LocalDate getEndDate(){
        return this.endDate;
    }
    //setters
    public void setStartDate(LocalDate newStartDate){
        this.startDate = newStartDate;
    }
    public void setEndDate(LocalDate newEndDate){
        this.endDate = newEndDate;
    }

    //method to check if another booking slot if overlapping with its own start/end dates, returns true if overlap occurs
    public Boolean checkOverlap(LocalDate otherStartDate, LocalDate otherEndDate){
        return !(otherEndDate.isBefore(startDate)|| otherStartDate.isAfter(endDate));
    }

    public String bookingSlotString(){
        return "From:"+startDate+"to:"+endDate;
    }

}