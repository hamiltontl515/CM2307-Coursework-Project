import java.util.*;

public class RentalRequestRepository{
    private HashMap<String, RentalRequest> requestsID = new HashMap<>();
    private int repositoryIndex;

    public RentalRequestRepository(){
        this.repositoryIndex =0;
    }
    //setters
    public void setRepositoryIndex(int index){
        repositoryIndex = index;
    }
    //adds a new request to the repo
    public void addRequest(RentalRequest newRequest){
        requestsID.put(newRequest.getRequestID(), newRequest);
    }
    //gets a request from repository by id
    public RentalRequest getRequest(String id){
        return requestsID.get(id);
    }
    //creates new request id
    public String generateRequestID(){
        String paddedNum = String.format("%03d", repositoryIndex); // makes sure id has 3 digits
        repositoryIndex++;
        return "R".concat(paddedNum);

    }

    //function returns a list of requests by student of student id
    public List<RentalRequest> requestByStudent(String userID){
        List<RentalRequest> returnRequests = new ArrayList<>();

        for(RentalRequest request: requestsID.values()){ //iterates through repo hashnmap
            if(request.getRequestStudent().getUserID().equals(userID)){
                returnRequests.add(request);
            }
        }
        return returnRequests;
    }

    //returns list of requests by room id
    public List<RentalRequest> requestByRoom(String roomID){
        List<RentalRequest> returnRequests = new ArrayList<>();

        for(RentalRequest request: requestsID.values()){ //iterates through repository hashmap
            if(request.getRequestRoom().getRoomID().equals(roomID)){
                returnRequests.add(request);
            }
        }

        return returnRequests;
    }

    //gets the amount of requests in the repository hashmap
    public void requestCount(){
        System.out.println(requestsID.size());
    }
}
