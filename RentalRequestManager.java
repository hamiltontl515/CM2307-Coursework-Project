import java.time.LocalDate;
import java.util.*;

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

    public List<RentalRequest> anyRequests(List<String> roomIDs){
        List<RentalRequest> returnRequestIDs = new ArrayList<>();

        for(String roomId: roomIDs){
            List<RentalRequest> roomRequests = rentalRequestRepository.requestByRoom(roomId);


            if(!roomRequests.isEmpty()){
                for(RentalRequest rentalRequest: roomRequests){
                    //rentalRequest.displayRequest();
                    returnRequestIDs.add(rentalRequest);
                }
            }
        }

        return returnRequestIDs;
    }

    public List<RentalRequest> onlyPendingRequests(List<RentalRequest> requests){
        List<RentalRequest> returnRequests = new ArrayList<>();
        for(RentalRequest request: requests){
            if(request.getRentalRequestStatus() == RentalRequestStatus.REQUESTED){
                returnRequests.add(request);
            }
        }
        return returnRequests;
    }
    public List<RentalRequest> stripOfOtherRoomIds(List<RentalRequest> requests, String roomID){
        List<RentalRequest> returnRequests = new ArrayList<>();
        for(RentalRequest request: requests){
            if(request.getRequestRoom().getRoomID().equals(roomID)){
                returnRequests.add(request);
            }
        }
        return returnRequests;
    }
    public void validateRequestID(List<RentalRequest> requests, String requestID){
        Boolean isIn = false;

        for(RentalRequest request: requests){
            if(request.getRequestID().equals(requestID)){
                isIn = true;
                break;
            }
        }
        if(!isIn){
            throw new IllegalArgumentException("ERROR: please enter a valid request ID.");
        }
    }

    public void denyRequest(String requestID){
        RentalRequest request = rentalRequestRepository.getRequest(requestID);

        request.denyRequest();
    }
    public RentalRequest getRequest(String requestID){
        RentalRequest request = rentalRequestRepository.getRequest(requestID);

        return request;
    }

    public Boolean checkConflict(BookingSlot slot, BookingSlot checkSlot){
        return slot.checkOverlap(checkSlot.getStartDate(), checkSlot.getEndDate());
    }
}