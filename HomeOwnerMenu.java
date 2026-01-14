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
        //returns the string for the start menu to print
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
                if(!university.matches("^[A-Za-z]+(\\s[A-Za-z]+)*$")){ // makes sure that university is at most two words seperated by a space, no numbers etc, repeats if invalid input
                    throw new IllegalArgumentException("ERROR: invalid university input, letters only.");
                }else{
                    String PropertyID = propertyManager.addPropertyAndReturnID(address, university, homeOwner.getUserID());
                    int roomDecision;
                    //System.out.println("would you like to add rooms to your property?enter 1 for yes, 2 for no");
                    try {
                        roomDecision = readInt("would you like to add rooms to your property?enter 1 for yes, 2 for no"); // repeats until the user enters a valid input
                        scanner.nextLine();
                        if(roomDecision == 1){
                            //go to add room to property method
                            addRoomsToProperty(PropertyID); //calls the addRoom to property method later in menu
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
                //get the description, repeats if invalid input
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
                            //identify if user wishes to add pre booked slots, repeats if invalid input

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

            //idetifys if the user wants to continue adding rooms or leave this method
            int anotherRoom;
            while(true){
                //System.out.println("would you like to add another room to this property? enter 1 for yes, 2 for no");
                try {
                    anotherRoom = readInt("would you like to add another room to this property? enter 1 for yes, 2 for no");
                    if(anotherRoom == 1){
                        break;
                    }else if(anotherRoom == 2){
                        exit = true; // this stops the function
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
                    propertyManager.validateDate(startDate); //validates the start date, looping until valid input
                    start = propertyManager.stringToDate(startDate); //converts to a localdate
                    System.out.println("enter the end date as DD:MM:YYYY");
                    //gets the end date of the booking
                    endDate = scanner.nextLine();
                    try {
                        propertyManager.validateDate(endDate); // validates the end date, looping until valid input
                        end = propertyManager.stringToDate(endDate); //converts to a localdate
                        if(end.isBefore(start)){
                            System.out.println("ERROR: end date must be after start date.");
                            break;
                        }
                        BookingSlot booking = new BookingSlot(start, end); // adds booking if all valid
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

            //identifys if user wishes to continue adding booking, exits external loop if not
            while (true) { 
                try {
                    another = readInt("would you like to add another booking? 1 for yes, 2 for no"); //repeats until valid input given
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
        //call manager method to sget rental requests

        List<String> ownersRooms = propertyManager.roomIDsByHomeOwnerID(owner.getUserID());

        if(ownersRooms.isEmpty()){
            System.out.println("ERROR: you have no propertys with rooms"); //catches an empty list and exits i so
            return;
        }
        
        List<RentalRequest> ownersRoomsRequests = rentalRequestManager.anyRequests(ownersRooms); //gets list of rental requests by room
        if(ownersRoomsRequests.isEmpty()){
            System.out.println("ERROR: your property rooms have no rental requests"); //catches empty list
            return;
        }

        System.out.println("your current requests:");
        for(RentalRequest request: ownersRoomsRequests){
            request.displayRequest(); //displays all valid requests
        }
    }

    public void manageRentalRequests(HomeOwner owner){
        lineBr();
        System.out.println("-manage your current requests-");
        //check if the homeowner first has any requests
        List<String> ownersRooms = propertyManager.roomIDsByHomeOwnerID(owner.getUserID());

        if(ownersRooms.isEmpty()){
            System.out.println("ERROR: you have no propertys with rooms"); //catches if no rooms
            return;
        }
        
        List<RentalRequest> ownersRoomsRequests = rentalRequestManager.anyRequests(ownersRooms); //converts tolist of rental requests

        if(ownersRoomsRequests.isEmpty()){
            System.out.println("ERROR: your property rooms have no rental requests"); // catches if rooms have no requests
            return;
        }
        //takes away any accepted or denied requests
        //rentalRequestManager.onlyPendingRequests(ownersRoomsRequests);
        ownersRoomsRequests = rentalRequestManager.onlyPendingRequests(ownersRoomsRequests);
        
        //list of visitied rooms
        List<String> visitedRooms = new ArrayList<>();

        for(int i=0;i<ownersRoomsRequests.size();i++){
            String visitingRoom = ownersRoomsRequests.get(i).getRequestRoom().getRoomID(); //when each room is visited, it is added 
            if(visitedRooms.contains(visitingRoom)){
                continue; //if room has been visited go to next room in owners room requests
            }
            //String visitingRoom = ownersRoomsRequests.get(i).getRequestRoom().getRoomID();
            visitedRooms.add(visitingRoom); // add room to visited

            //List<RentalRequest> ownersRoomsCopy = ownersRoomsRequests;
            List<RentalRequest> ownersRoomsCopy = new ArrayList<>(ownersRoomsRequests); //copies list to later change/reduce

            List<RentalRequest> requestsSameRoom = rentalRequestManager.stripOfOtherRoomIds(ownersRoomsCopy, visitingRoom); // remove any requests with other room ids
            
            manageRequestsByRoom(requestsSameRoom, visitingRoom, owner); //go to manage the request of requests of that specific room
        }
    }
    public void manageRequestsByRoom(List<RentalRequest> requests, String visitingRoom, HomeOwner owner){
        System.out.println("requests on room:"+ visitingRoom);
        for(RentalRequest request: requests){
            request.displayRequest(); //output the available requests
        }
        String manageID;
        while (true) { 
            System.out.println("enter the request ID of the request you wish to manage:"); 
            manageID = scanner.nextLine();
            try {
                rentalRequestManager.validateRequestID(requests, manageID); // validate that the request the user wishes to manage is  one of the available requests

                int requestAction;
                //String requestActionString;
                while (true) { 
                    //System.out.println("enter 1 to accpet request, 2 to deny request, 3 to cancel");
                    try {
                        requestAction = readInt("enter 1 to accpet request, 2 to deny request, 3 to cancel"); // repeats input until valid input is given
                        if(requestAction == 1){
                            //accept request
                            RentalRequest acceptedRequest = rentalRequestManager.getRequest(manageID);
                            acceptedRequest.acceptRequest(); // change requests staus to accepted
                            rentalAgreementManager.addNewAgreement(acceptedRequest, owner, acceptedRequest.getRequestStudent(), acceptedRequest.getRequestRoom() ); //add a new corresponding agreement
                            //cdenying any overlapping slots
                            BookingSlot acceptedSlot = acceptedRequest.getRentalRequestBookingSlot(); //get the booking slot to remove any requests which conflict
                            for(RentalRequest request: requests){
                                if(!request.getRequestID().equals(manageID)){
                                    if(rentalRequestManager.checkConflict(request.getRentalRequestBookingSlot(), acceptedRequest.getRentalRequestBookingSlot())){ //if requests booking slot conflicts
                                        rentalRequestManager.denyRequest(request.getRequestID()); //deny request
                                    }
                                }
                            }
                            System.out.println("SUCCESS: request accepted.");
                            return;
                        }else if(requestAction == 2){
                            //deny request
                            rentalRequestManager.denyRequest(manageID); //change the requests status to denied
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

    //panic placed function to replace all instances of nextInt() I used that broke when entering a non int input, repeats prompt until valid input is given
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
