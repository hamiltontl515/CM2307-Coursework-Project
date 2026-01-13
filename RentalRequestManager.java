import java.util.*;
import java.time.LocalDate;

public class RentalRequestManager{
    private RentalRequestRepository rentalRequestRepository;

    public RentalRequestManager(RentalRequestRepository rentalRequestRepository){
        this.rentalRequestRepository = rentalRequestRepository;
    }

    public void createRequest(Student student, Room room, LocalDate start, LocalDate end){
        BookingSlot slot = new BookingSlot(start, end);
        RentalRequest request = new RentalRequest(rentalRequestRepository.generateRequestID(),room, student, slot);
        rentalRequestRepository.addRequest(request);
    }

    public List<RentalRequest> requestsByStudent(String userID){
        return rentalRequestRepository.requestByStudent(userID);
    }
}