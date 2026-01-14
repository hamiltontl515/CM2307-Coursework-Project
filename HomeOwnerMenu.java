import java.time.LocalDate;
import java.util.*;

public class HomeOwnerMenu{
    private Scanner scanner;
    private PropertyManager propertyManager;
    private RentalRequestManager rentalRequestManager;
    private RentalAgreementManager rentalAgreementManager;

    public HomeOwnerMenu(Scanner scanner, PropertyManager propertyManager, RentalRequestManager rentalRequestManager, RentalAgreementManager rentalAgreementManager){
        this.scanner = scanner;
        this.propertyManager = propertyManager;
        this.rentalRequestManager = rentalRequestManager;
        this.rentalAgreementManager = rentalAgreementManager;
    }

    public void lineBr(){
        System.out.println("==============================");
        System.out.println("==============================");

    }

    public String homeOwnerOptions(){
        /*lineBr();
        System.out.println("--------homeowner menu--------");
        System.out.println("press 1 to add a property");
        System.out.println("press 2 to add a room to a property");
        System.out.println("press 3 to view rental requests");
        System.out.println("press 4 to manage a rental request");
        System.out.println("press 5 to view rental agreements");
        System.out.println("press 6 to log out");*/

        return "--------homeowner menu--------\npress 1 to add a property\npress 2 to add a room to a property\npress 3 to view rental requests\npress 4 to manage a rental request\npress 5 to view rental agreements\npress 6 to log out";

    }
    public void addProperty(HomeOwner homeOwner){
        lineBr();
        System.out.println("--------add a property--------");
        //get address input
        String address;
        while (true) { 
            System.out.println("enter the address of the property");
            try {
                address = scanner.nextLine();
                break;
            } catch (Exception e) {
                System.out.println("ERROR: invlad address input.");
            }
        }
        //get university input
        String university;
        while (true) { 
            System.out.println("enter the university town/city");
            try {
                university = scanner.nextLine();
                if(!university.matches("^[A-Za-z]+(\\s[A-Za-z]+)*$")){
                    throw new IllegalArgumentException("ERROR: invalid university input, letters only.");
                }else{
                    String PropertyID = propertyManager.addPropertyAndReturnID(address, university, homeOwner.getUserID());
                    int roomDecision;
                    //System.out.println("would you like to add rooms to your property?enter 1 for yes, 2 for no");
                    try {
                        roomDecision = readInt("would you like to add rooms to your property?enter 1 for yes, 2 for no");
                        scanner.nextLine();
                        if(roomDecision == 1){
                            //go to add room to property method
                            addRoomsToProperty(PropertyID);
                            break;
                        }else if(roomDecision == 2){
                            break;
                        }else{
                            System.out.println("ERROR: enter a 1 or 2.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("ERROR: enter a either 1 or 2");
                    }
                    break;
                }
            } catch (Exception e) {
                System.out.println("ERROR: invlaid university input");
            }
        }
    }

    public void addRoom(HomeOwner homeOwner){
        lineBr();
        System.out.println("---add a room to a property---");
        //get property the homeowner wishes to add room to
        System.out.println("the id's of your properties:");
        try {
            propertyManager.displayHomeownersPropertyIDs(homeOwner.getUserID());
        } catch (Exception e) {
            System.out.println(e.getMessage());
                return;
        }
        String propertyID;
        while (true) { 
            System.out.println("enter the ID of the property you wish to add room(s) to:");
            propertyID = scanner.nextLine();

            if(propertyManager.propertyIdsByHomeownerID(homeOwner.getUserID()).contains(propertyID)){
                //allow user to add a property if the homowner owns the property
                addRoomsToProperty(propertyID);
                break;
            }else{
                System.out.println("ERROR:please enter a valid id of one of YOUR properties.");
            }
        }
    }
    public void addRoomsToProperty(String PropertyID){
        Boolean exit = false;
        while(!exit) { 
            String description;
            while (true) { 
                //get the description
                System.out.println("enter the room description.limit 200 characters");
                description = scanner.nextLine();
                try {
                    propertyManager.validateRoomDescription(description);
                    String price;
                    Double pricePerWeek;
                    while (true) { 
                        System.out.println("enter the rooms price. must be in pounds.pence format.");
                        price = scanner.nextLine();
                        try {
                            propertyManager.validateRoomPrice(price);
                            pricePerWeek = propertyManager.stringToPrice(price);
                            //identify if user wishes to add pre booked slots

                            int anyPreBooked;
                            List<BookingSlot> preBookings = new ArrayList<>();
                            //System.out.println("would you like to add any pre booked slots? 1 for yes, 2 for no.");

                            while (true) { 
                                try {
                                    anyPreBooked = readInt("would you like to add any pre booked slots? 1 for yes, 2 for no.");
                                    scanner.nextLine();
                                    if(anyPreBooked == 1){
                                        getPreBookings(preBookings);
                                        break;
                                    }else if(anyPreBooked == 2){
                                        break;
                                    }else{
                                        System.out.println("ERROR: enter a 1 or 2");
                                    }
                                } catch (Exception e) {
                                    System.out.println("ERROR: invalid input");
                                }
                                break;
                            }

                            propertyManager.addRoomToProperty(PropertyID, description, pricePerWeek, preBookings);
                            break;
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    }
                    break;
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }

            int anotherRoom;
            while(true){
                //System.out.println("would you like to add another room to this property? enter 1 for yes, 2 for no");
                try {
                    anotherRoom = readInt("would you like to add another room to this property? enter 1 for yes, 2 for no");
                    if(anotherRoom == 1){
                        break;
                    }else if(anotherRoom == 2){
                        exit = true;
                        break;
                    }else{
                        System.out.println("EEOR: invlaid input.");
                    }
                } catch (Exception e) {
                    System.out.println("ERROR: invlaid number input.");
                }
            }
        }
    }
    public void getPreBookings(List<BookingSlot> preBooked){
        //set the list to return
        Boolean exit = false;
        while(!exit){
            //get the start date from the user
            String startDate;
            String endDate;
            LocalDate start;
            LocalDate end;
            System.out.println("enter the start date as DD:MM:YYYY");
            while (true) { 
                startDate = scanner.nextLine();
                try {
                    propertyManager.validateDate(startDate);
                    start = propertyManager.stringToDate(startDate);
                    System.out.println("enter the end date as DD:MM:YYYY");
                    endDate = scanner.nextLine();
                    try {
                        propertyManager.validateDate(endDate);
                        end = propertyManager.stringToDate(endDate);
                        if(end.isBefore(start)){
                            System.out.println("ERROR: end date must be after start date.");
                            break;
                        }
                        BookingSlot booking = new BookingSlot(start, end);
                        preBooked.add(booking);
                        break;
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }

            int another;
            //System.out.println("would you like to add another booking? 1 for yes, 2 for no");
            while (true) { 
                try {
                    another = readInt("would you like to add another booking? 1 for yes, 2 for no");
                    if(another == 1){
                        break;
                    }else if(another == 2){
                        exit = true;
                        break;
                    }else{
                        System.out.println("ERROR: enter a 1 or 2");
                    }
                } catch (Exception e) {
                    System.out.println("ERROR: invalid input.");
                }
            }

        }
    }

    public void viewRentalRequests(HomeOwner owner){
        lineBr();
        System.out.println("--view your current requests--");
        //call manager method to show rental requests

        List<String> ownersRooms = propertyManager.roomIDsByHomeOwnerID(owner.getUserID());

        if(ownersRooms.isEmpty()){
            System.out.println("ERROR: you have no propertys with rooms");
            return;
        }
        
        List<RentalRequest> ownersRoomsRequests = rentalRequestManager.anyRequests(ownersRooms);
        if(ownersRoomsRequests.isEmpty()){
            System.out.println("ERROR: your property rooms have no rental requests");
            return;
        }

        System.out.println("your current requests:");
        for(RentalRequest request: ownersRoomsRequests){
            request.displayRequest();
        }
    }

    public void manageRentalRequests(HomeOwner owner){
        lineBr();
        System.out.println("-manage your current requests-");
        //check if the homeowner first has any requests
        List<String> ownersRooms = propertyManager.roomIDsByHomeOwnerID(owner.getUserID());

        if(ownersRooms.isEmpty()){
            System.out.println("ERROR: you have no propertys with rooms");
            return;
        }
        
        List<RentalRequest> ownersRoomsRequests = rentalRequestManager.anyRequests(ownersRooms);

        if(ownersRoomsRequests.isEmpty()){
            System.out.println("ERROR: your property rooms have no rental requests");
            return;
        }
        //takes away any accepted or denied requests
        //rentalRequestManager.onlyPendingRequests(ownersRoomsRequests);
        ownersRoomsRequests = rentalRequestManager.onlyPendingRequests(ownersRoomsRequests);
        
        //list of visitied rooms
        List<String> visitedRooms = new ArrayList<>();

        for(int i=0;i<ownersRoomsRequests.size();i++){
            String visitingRoom = ownersRoomsRequests.get(i).getRequestRoom().getRoomID();
            if(visitedRooms.contains(visitingRoom)){
                continue;
            }
            //String visitingRoom = ownersRoomsRequests.get(i).getRequestRoom().getRoomID();
            visitedRooms.add(visitingRoom);

            //List<RentalRequest> ownersRoomsCopy = ownersRoomsRequests;
            List<RentalRequest> ownersRoomsCopy = new ArrayList<>(ownersRoomsRequests);

            List<RentalRequest> requestsSameRoom = rentalRequestManager.stripOfOtherRoomIds(ownersRoomsCopy, visitingRoom);
            
            manageRequestsByRoom(requestsSameRoom, visitingRoom, owner);
        }
    }
    public void manageRequestsByRoom(List<RentalRequest> requests, String visitingRoom, HomeOwner owner){
        System.out.println("requests on room:"+ visitingRoom);
        for(RentalRequest request: requests){
            request.displayRequest();
        }
        String manageID;
        while (true) { 
            System.out.println("enter the request ID of the request you wish to manage:");
            manageID = scanner.nextLine();
            try {
                rentalRequestManager.validateRequestID(requests, manageID);

                int requestAction;
                //String requestActionString;
                while (true) { 
                    //System.out.println("enter 1 to accpet request, 2 to deny request, 3 to cancel");
                    try {
                        requestAction = readInt("enter 1 to accpet request, 2 to deny request, 3 to cancel");
                        if(requestAction == 1){
                            //accept request
                            RentalRequest acceptedRequest = rentalRequestManager.getRequest(manageID);
                            acceptedRequest.acceptRequest();
                            rentalAgreementManager.addNewAgreement(acceptedRequest, owner, acceptedRequest.getRequestStudent(), acceptedRequest.getRequestRoom() );
                            //cdenying any overlapping slots
                            BookingSlot acceptedSlot = acceptedRequest.getRentalRequestBookingSlot();
                            for(RentalRequest request: requests){
                                if(!request.getRequestID().equals(manageID)){
                                    if(rentalRequestManager.checkConflict(request.getRentalRequestBookingSlot(), acceptedRequest.getRentalRequestBookingSlot())){
                                        rentalRequestManager.denyRequest(request.getRequestID());
                                    }
                                }
                            }
                            System.out.println("SUCCESS: request accepted.");
                            return;
                        }else if(requestAction == 2){
                            //deny request
                            rentalRequestManager.denyRequest(manageID);
                            System.out.println("SUCCESS: request denied.");
                            return;
                        }else if(requestAction == 3){
                            break;
                        }else{
                            System.out.println("ERROR: Enter a valid number.");
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void viewRentalAgreements(HomeOwner owner){
        lineBr();
        System.out.println("--view your rental agreements-");
        //call manager method to show agreements
        List<RentalAgreement> currentAgreements = rentalAgreementManager.agreementByHomeOwner(owner.getUserID());

        if(currentAgreements.isEmpty()){
            System.out.println("you have no current agreements");
        }else{
            for(RentalAgreement agreement: currentAgreements){
                agreement.displayAgreement();
            }
        }
    }
    public int readInt(String prompt) {
        while (true) {
            System.out.println(prompt);
            String input = scanner.nextLine().trim();

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("ERROR: please enter a valid whole number.");
            }
        }
    }

    public void start(HomeOwner homeOwner){
        Boolean runStart = true;
        while(runStart){
            //System.out.println(homeOwnerOptions());
            int menuDecision;
            String decision;
            try {
                menuDecision = readInt(homeOwnerOptions());
                //scanner.nextLine();

                switch (menuDecision) {
                    case 1:
                        addProperty(homeOwner);
                        break;
                    case 2:
                        addRoom(homeOwner);
                        break;
                    case 3:
                        viewRentalRequests(homeOwner);
                        break;
                    case 4:
                        manageRentalRequests(homeOwner);
                        break;
                    case 5:
                        viewRentalAgreements(homeOwner);
                        break;
                    case 6:
                        runStart = false;               
                        break;
                    default:
                        throw new AssertionError();
                }
            } catch (Exception e) {
                System.out.println("ERROR: invalid menu selection");
            }
        }
    }
}
