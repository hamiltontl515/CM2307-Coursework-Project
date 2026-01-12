import java.util.*;
import java.time.LocalDate;

public class StudentMenu{
    private Scanner scanner;
    private PropertyManager propertyManager;

    public StudentMenu(Scanner scanner, PropertyManager propertyMAnager){
        this.scanner = scanner;
        this.propertyManager = propertyMAnager;
    }

    public static void lineBr(){
        System.out.println("==============================");
        System.out.println("==============================");

    }

    public void displayUserMenu(){
        lineBr();
        System.out.println("--welcome to the student menu-");
        System.out.println("press 1 to search rooms");
        System.out.println("press 2 to look at rental requests");
        System.out.println("press 3 to look at rental agreements");
    }

    public void searchRooms(){
        lineBr();
        System.out.println("---------search rooms---------");
        System.out.println("universities we currently serve:");

        for(String uni: propertyManager.validUnis()){
            System.out.print("uni"+", ");
        }

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
        String dateRegex = "^(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[0-2])-(\\d{4})$";
        while (true) { 
            System.out.println("if you dont wish your search to include dates press 1, otherwise press 2:");
            try {
                dateDecision = scanner.nextInt();
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
                            start = LocalDate.parse(startDate);
                            System.out.println("Enter end date as DD-MM-YYYY");
                            endDate = scanner.nextLine();
                            try {
                                regexChecker(endDate, dateRegex);
                                end = LocalDate.parse(endDate);
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("ERROR: enter either 1 or 2");
            }

        }
        int priceDecision;
        String pricePerWeek;
        Double price;
        String priceRegex = "^\\d+(\\.\\d{2})$^\\d+(\\.\\d{2})$";
        while(true){
            System.out.println("If you do not wish to enter a price budget (per week), press 1, if not press 2");
            try {
                priceDecision = scanner.nextInt();
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
                        } catch (Exception e) {
                            System.out.println("ERROR: please enter as pounds.pence");
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("ERROR: please enter 1 or 2.");
            }
        }

        displayRooms(university, start, end, price);

    }

    public void displayRooms(String universiry, LocalDate start, LocalDate end, Double price){
        List<Room> availableRooms = propertyManager.roomSearch(universiry, start, end, price);

        for(Room room : availableRooms){
            room.displayRoom();
        }
    }
    public void regexChecker(String string, String regex){
        if(!string.matches(regex)){
            throw new IllegalArgumentException("EROR: wrong date format");
        }
    }
}