import java.util.*;

public class RentalRequestRepository{
    private HashMap<String, RentalRequest> requestsID = new HashMap<>();
    private int repositoryIndex;

    public RentalRequestRepository(){
        this.repositoryIndex =0;
    }

    public void setRepositoryIndex(int index){
        repositoryIndex = index;
    }

    public void addRequest(RentalRequest newRequest){
        requestsID.put(newRequest.getRequestID(), newRequest);
        repositoryIndex =0;
    }
    public RentalRequest getRequest(String id){
        return requestsID.get(id);
    }

    public String generateRequestID(){
        String paddedNum = String.format("%03d", repositoryIndex);
        repositoryIndex++;
        return "R".concat(paddedNum);

    }

    public List<RentalRequest> requestByStudent(String userID){
        List<RentalRequest> returnRequests = new ArrayList<>();

        for(RentalRequest request: requestsID.values()){
            if(request.getRequestStudent().getUserID().equals(userID)){
                returnRequests.add(request);
            }
        }
        return returnRequests;
    }

    public List<RentalRequest> requestByRoom(String roomID){
        List<RentalRequest> returnRequests = new ArrayList<>();

        for(RentalRequest request: requestsID.values()){
            if(request.getRequestRoom().getRoomID().equals(roomID)){
                returnRequests.add(request);
            }
        }

        return returnRequests;
    }

    public void requestCount(){
        System.out.println(requestsID.size());
    }
}
