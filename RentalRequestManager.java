import java.time.LocalDate;
import java.util.*;

public class RentalRequestManager{
    private RentalRequestRepository rentalRequestRepository;

    public RentalRequestManager(RentalRequestRepository rentalRequestRepository){
        this.rentalRequestRepository = rentalRequestRepository;
    }

    //method to create new request
    public void createRequest(Student student, Room room, LocalDate start, LocalDate end){
        BookingSlot slot = new BookingSlot(start, end); // converts the start and end to a booking slot
        RentalRequest request = new RentalRequest(rentalRequestRepository.generateRequestID(),room, student, slot); // makes the new request
        rentalRequestRepository.addRequest(request); // tells the repository class to add the request
    }

    //function returns a list of rental requests that a students user id has
    public List<RentalRequest> requestsByStudent(String userID){
        return rentalRequestRepository.requestByStudent(userID);
    }

    //function returns a list of rental requests for a property, iterates through its roomIDs and finds any requests
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

    //function returns a list of requests only if their status uis pending
    public List<RentalRequest> onlyPendingRequests(List<RentalRequest> requests){
        List<RentalRequest> returnRequests = new ArrayList<>();
        for(RentalRequest request: requests){
            if(request.getRentalRequestStatus() == RentalRequestStatus.REQUESTED){ 
                returnRequests.add(request);
            }
        }
        return returnRequests;
    }
    //function returns a list of rental requests where the room Id's are the same as the search id
    public List<RentalRequest> stripOfOtherRoomIds(List<RentalRequest> requests, String roomID){
        List<RentalRequest> returnRequests = new ArrayList<>();
        for(RentalRequest request: requests){
            if(request.getRequestRoom().getRoomID().equals(roomID)){
                returnRequests.add(request);
            }
        }
        return returnRequests;
    }
    //throws an exception if the id is not in the requests list
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

    //method to deny a request
    public void denyRequest(String requestID){
        RentalRequest request = rentalRequestRepository.getRequest(requestID); // gets the request by id

        request.denyRequest(); // denies request
    }

    //gets the request by id
    public RentalRequest getRequest(String requestID){
        RentalRequest request = rentalRequestRepository.getRequest(requestID);

        return request;
    }

    //checks to see if a booking slot is overlapping with another.
    public Boolean checkConflict(BookingSlot slot, BookingSlot checkSlot){
        return slot.checkOverlap(checkSlot.getStartDate(), checkSlot.getEndDate());
    }
}