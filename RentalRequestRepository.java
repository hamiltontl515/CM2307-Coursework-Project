import java.util.*;
import java.time.LocalTime;

public class RentalRequestRepository{
    private HashMap<String, RentalRequest> requestsID = new HashMap<>();

    public void addRequest(RentalRequest newRequest){
        requestsID.put(newRequest.getRequestID(), newRequest);
    }
}