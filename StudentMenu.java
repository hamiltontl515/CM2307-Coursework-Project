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

    public void displayUserMenu(){
        lineBr();
        System.out.println("--welcome to the student menu-");
        System.out.println("press 1 to search rooms");
        System.out.println("press 2 to look at rental requests");
        System.out.println("press 3 to make a rental request");
        System.out.println("press 4 to look at rental agreements");
        System.out.println("press 5 to log out");
    }

    public void searchRooms(){
        if (propertyManager == null) {
            throw new IllegalStateException("PropertyManager not initialised");
        }
        lineBr();
        System.out.println("---------search rooms---------");
        System.out.println("universities we currently serve:");

        List<String> unis = propertyManager.validUnis();

        if(unis.isEmpty()){
            System.out.println("ERROR: no universities currently added");
            return;
        }
        for(String uni: unis){
            System.out.println(uni);
        }
        System.out.println("");

        String university;
        while(true){
            System.out.println("enter the university you wish to search for:");
            university = scanner.nextLine();
            try {
                propertyManager.validateUni(university);
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        int dateDecision;
        String startDate;
        String endDate;
        LocalDate start;
        LocalDate end;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String dateRegex = "^(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[0-2])-(\\d{4})$";
        while (true) { 
            System.out.println("if you dont wish your search to include dates press 1, otherwise press 2:");
            try {
                dateDecision = scanner.nextInt();
                scanner.nextLine();
                if(dateDecision==1){
                    start = null;
                    end = null;
                    break;
                }else if(dateDecision == 2){
                    while(true){
                        System.out.println("Enter start date as DD-MM-YYYY:");
                        startDate = scanner.nextLine();
                        try {
                            regexChecker(startDate, dateRegex);
                            start = LocalDate.parse(startDate, formatter);
                            System.out.println("Enter end date as DD-MM-YYYY");
                            endDate = scanner.nextLine();
                            try {
                                regexChecker(endDate, dateRegex);
                                end = LocalDate.parse(endDate, formatter);
                                break;
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    break;
                }
            } catch (Exception e) {
                System.out.println("ERROR: enter either 1 or 2");
            }

        }
        int priceDecision;
        String pricePerWeek;
        Double price;
        String priceRegex = "^\\d+\\.\\d{2}$";
        while(true){
            System.out.println("If you do not wish to enter a price budget (per week), press 1, if not press 2");
            try {
                priceDecision = scanner.nextInt();
                scanner.nextLine();
                if(priceDecision == 1){
                    price = null;
                    break;
                }else if(priceDecision == 2){
                    while (true) { 
                        System.out.println("please enter your price budget as pounds.pence");
                        pricePerWeek = scanner.nextLine();
                        try {
                            regexChecker(pricePerWeek, priceRegex);
                            
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

        displayRooms(university, start, end, price);

    }

    public void displayRentalRequests(Student student){
        lineBr();
        System.out.println("-------current requests-------");

        List<RentalRequest> currentRequests = rentalRequestManager.requestsByStudent(student.getUserID());

        if(currentRequests == null){
            System.out.println("you have no current requests");
        }else{
            for(RentalRequest request: currentRequests){
                request.displayRequest();
            }
        }
        
    }
    public void displayRentalArgreements(Student student){
        lineBr();
        System.out.println("-------rental agreements------");

        List<RentalAgreement> currentAgreements = rentalAgreementManager.agreementByStudent(student.getUserID());

        if(currentAgreements.isEmpty()){
            System.out.println("you have no current agreements");
        }else{
            for(RentalAgreement agreement: currentAgreements){
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
                propertyManager.validateRoom(roomID);
                propertyID = propertyManager.getPropertyIDByRoomID(roomID);
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        Room proposedRoom = propertyManager.getProperty(propertyID).getRoom(roomID);

        //get booking slot
        String startDate;
        String endDate;
        LocalDate start;
        LocalDate end;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String dateRegex = "^(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[0-2])-(\\d{4})$";
        while(true){
            System.out.println("Enter start date as DD-MM-YYYY:");
            startDate = scanner.nextLine();
            try {
                regexChecker(startDate, dateRegex);
                start = LocalDate.parse(startDate, formatter);
                System.out.println("Enter end date as DD-MM-YYYY");
                endDate = scanner.nextLine();
                try {
                    regexChecker(endDate, dateRegex);
                    end = LocalDate.parse(endDate, formatter);
                    if(proposedRoom.checkAvailability(start, end)){
                        //make request
                        rentalRequestManager.createRequest(student, proposedRoom, start, end);
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

    public void displayRooms(String university, LocalDate start, LocalDate end, Double price){
        List<Room> availableRooms = propertyManager.roomSearch(university, start, end, price);

        if(availableRooms.isEmpty()){
            System.out.println("ERROR: no rooms match your requirements.");
        }

        for(Room room : availableRooms){
            lineBr();
            room.displayRoom();
        }
    }

    public void start(Student student){
        Boolean runStart = true;
        while(runStart){
            lineBr();
            displayUserMenu();
            int menuDecision;
            try {
                menuDecision = scanner.nextInt();
                scanner.nextLine();
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
                //System.out.println("ERROR: please input a valid option.");
                e.printStackTrace();
            }
        }
    }
    public void regexChecker(String string, String regex){
        if(!string.matches(regex)){
            throw new IllegalArgumentException("EROR: wrong date format");
        }
    }
}