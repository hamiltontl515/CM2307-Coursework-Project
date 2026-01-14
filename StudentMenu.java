import java.time.LocalDate;
import java.util.*;
import java.time.format.DateTimeFormatter;

public class StudentMenu{
    private Scanner scanner;
    private PropertyManager propertyManager;
    private RentalRequestManager rentalRequestManager;
    private RentalAgreementManager rentalAgreementManager;

    public StudentMenu(Scanner scanner, PropertyManager propertyManager, RentalRequestManager rentalRequestManager, RentalAgreementManager rentalAgreementManager){
        this.scanner = scanner;
        this.propertyManager = propertyManager;
        this.rentalRequestManager = rentalRequestManager;
        this.rentalAgreementManager = rentalAgreementManager;

    }

    public void lineBr(){
        System.out.println("==============================");
        System.out.println("==============================");

    }

    //returns string of menu options to be used in the readInt for start
    public String studentMenu(){
        lineBr(); 
        /*System.out.println("--welcome to the student menu-");
        System.out.println("press 1 to search rooms");
        System.out.println("press 2 to look at rental requests");
        System.out.println("press 3 to make a rental request");
        System.out.println("press 4 to look at rental agreements");
        System.out.println("press 5 to log out");*/
        return "--welcome to the student menu-\npress 1 to search rooms\npress 2 to look at rental requests\npress 3 to make a rental request\npress 4 to look at rental agreements\npress 5 to log out";
    }

    //this method gets the users inputs to search rooms and then performs search
    public void searchRooms(){
        /*if (propertyManager == null) {
            throw new IllegalStateException("PropertyManager not initialised");
        }*/
        lineBr();
        System.out.println("---------search rooms---------");
        System.out.println("universities we currently serve:");

        List<String> unis = propertyManager.validUnis(); //gets the universities that peroperties have

        if(unis.isEmpty()){
            System.out.println("ERROR: no universities currently added");
            return; // makes sure that if no universities added the method stops to save time
        }
        for(String uni: unis){
            System.out.println(uni); // calls the manager to get and output all unis in property reposoitory
        }
        System.out.println("");

        //gets the university input from user
        String university;
        while(true){
            System.out.println("enter the university you wish to search for:");
            university = scanner.nextLine();
            try {
                propertyManager.validateUni(university); // makes sure entered uni is in propeperty repository
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage()); // catches the exception if it is not and displays error, causing loop to repeat until correct input is given
            }
        }

        int dateDecision;
        String startDate;
        String endDate;
        LocalDate start = null;
        LocalDate end = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy"); // fate formatter to ensure consistent dd/mm/yyyy
        String dateRegex = "^(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[0-2])-(\\d{4})$"; //regex ensuring that entered date is dd-mm-yyyy
        Boolean carryOn = false;
        while (!carryOn) { //keeps repeating loop until a date is given
            //System.out.println("if you dont wish your search to include dates press 1, otherwise press 2:");
            try {
                dateDecision = readInt("if you dont wish your search to include dates press 1, otherwise press 2:");
                if(dateDecision==1){
                    start = null;
                    end = null;
                    carryOn = true; // breaks out of loop if the uers does not wish to enter date restraint
                }else if(dateDecision == 2){
                    while(true){
                        System.out.println("Enter start date as DD-MM-YYYY:"); 
                        startDate = scanner.nextLine(); //get start date input from users, repeating until valid
                        try {
                            regexChecker(startDate, dateRegex);
                            start = LocalDate.parse(startDate, formatter);
                            System.out.println("Enter end date as DD-MM-YYYY");
                            endDate = scanner.nextLine(); // gets end date input from user, repeating until valid
                            try {
                                regexChecker(endDate, dateRegex);
                                end = LocalDate.parse(endDate, formatter); 
                                if(end.isBefore(start)){ // check that the start date is before the end date, repeating input if not true
                                    System.out.println("ERROR: end date must be after start date");
                                    //break;
                                }else{
                                    carryOn = true;
                                    break;
                                }
                                //break;
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    //break;
                }
            } catch (Exception e) {
                System.out.println("ERROR: enter either 1 or 2");
            }

        }
        int priceDecision;
        String pricePerWeek;
        Double price;
        String priceRegex = "^\\d+\\.\\d{2}$"; //price regex ensuring price is a number followed by a . follwed by 2 digit
        while(true){
            //System.out.println("If you do not wish to enter a price budget (per week), press 1, if not press 2");
            try {
                priceDecision = readInt("If you do not wish to enter a price budget (per week), press 1, if not press 2"); //repeats the prompt until valid input given
                if(priceDecision == 1){
                    price = null;
                    break; // exits the loop if the user does not want to enter a price restricition
                }else if(priceDecision == 2){
                    while (true) { 
                        System.out.println("please enter your price budget as pounds.pence");
                        pricePerWeek = scanner.nextLine();
                        try {
                            regexChecker(pricePerWeek, priceRegex); // checks the entered string against the regex, repeating loop if invalid
                            
                            price = Double.parseDouble(pricePerWeek);
                            break;
                        } catch (Exception e) {
                            System.out.println("ERROR: please enter as pounds.pence");
                        }
                    }
                    break;
                }
            } catch (Exception e) {
                System.out.println("ERROR: please enter 1 or 2.");
            }
        }

        displayRooms(university, start, end, price); // calls the display rooms function later in the menu

    }

    public void displayRentalRequests(Student student){
        lineBr();
        System.out.println("-------current requests-------");

        List<RentalRequest> currentRequests = rentalRequestManager.requestsByStudent(student.getUserID()); //gets all requests from the student from the request manger

        if(currentRequests == null){
            System.out.println("you have no current requests");
        }else{
            for(RentalRequest request: currentRequests){
                request.displayRequest(); // iterates through list of requests outputting them
            }
        }
        
    }
    public void displayRentalArgreements(Student student){
        lineBr();
        System.out.println("-------rental agreements------");

        List<RentalAgreement> currentAgreements = rentalAgreementManager.agreementByStudent(student.getUserID()); //gets all the requests from the student from the agreement manager

        if(currentAgreements.isEmpty()){
            System.out.println("you have no current agreements");
        }else{
            for(RentalAgreement agreement: currentAgreements){ //iterates through the request if there are any and outputs them
                agreement.displayAgreement();
            }
        }
    }

    public void makeRentalRequest(Student student){
        lineBr();
        System.out.println("--------request a room--------");

        //get room id
        String roomID;
        String propertyID;
        while(true){
            System.out.println("please enter the room ID that you wish to book:");
            roomID = scanner.nextLine();
            try {
                propertyManager.validateRoom(roomID); //validates room id is in property repository
                propertyID = propertyManager.getPropertyIDByRoomID(roomID); //gets the property the room is in
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage()); // loops back if invalid input given
            }
        }
        Room proposedRoom = propertyManager.getProperty(propertyID).getRoom(roomID); // stores the room

        //get booking slot
        String startDate;
        String endDate;
        LocalDate start;
        LocalDate end;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy"); // date fromatter for dd/mm/yyyy
        String dateRegex = "^(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[0-2])-(\\d{4})$"; // date regex from dd/mm/yyy seperated by hyphens
        while(true){
            //gets the start date
            System.out.println("Enter start date as DD-MM-YYYY:");
            startDate = scanner.nextLine(); 
            try {
                regexChecker(startDate, dateRegex); //validates the date before making the localdate, loops if invalid
                start = LocalDate.parse(startDate, formatter);
                //get the end date
                System.out.println("Enter end date as DD-MM-YYYY");
                endDate = scanner.nextLine();
                try {
                    regexChecker(endDate, dateRegex); //validates the end date before making localdate, loops if invalid
                    end = LocalDate.parse(endDate, formatter);
                    if(end.isBefore(start)){
                        System.out.println("ERROR: end date must be after start date"); // checls the start date is before the end date, loops if invalid
                        break;
                    }
                    if(proposedRoom.checkAvailability(start, end)){ // if the room is available at the time given
                        //make request
                        rentalRequestManager.createRequest(student, proposedRoom, start, end); // create the new rental request
                        System.out.println("SUCCESS: Request added.");
                        break;
                    }else{
                        System.out.println("ERROR: Room unavailable for this period.");
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        
    }

    //called bys search rooms , just handles the outputting of the rooms
    public void displayRooms(String university, LocalDate start, LocalDate end, Double price){
        List<Room> availableRooms = propertyManager.roomSearch(university, start, end, price); // gets the list of matching rooms

        if(availableRooms.isEmpty()){
            System.out.println("ERROR: no rooms match your requirements."); // catches if the list is empty
            return;
        }

        for(Room room : availableRooms){
            lineBr();
            room.displayRoom(); //loops through returned rooms and outputsthem
        }
    }

    public void start(Student student){
        Boolean runStart = true;
        while(runStart){
            lineBr();
            //System.out.println(studentMenu());
            int menuDecision;
            try {
                menuDecision = readInt(studentMenu()); // repeats the menu prompt until a valid input is given
                if(menuDecision == 1){
                    searchRooms();
                }else if(menuDecision == 2){
                        displayRentalRequests(student);
                }else if (menuDecision == 3) {
                    makeRentalRequest(student);
                }else if(menuDecision == 4){
                    displayRentalArgreements(student);
                }else if(menuDecision == 5){
                    System.out.println("Returning to log in menu");
                    runStart = false;
                }
            } catch (Exception e) {
                System.out.println("ERROR: please input a valid option.");
                //e.printStackTrace();
            }
        }
    }
    public void regexChecker(String string, String regex){
        if(!string.matches(regex)){
            throw new IllegalArgumentException("EROR: wrong date format");
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

}