import java.util.*;

public class UserUI{
    private UserManager userManager;
    private Scanner scanner;
    private PropertyManager propertyManager;
    private RentalRequestManager rentalRequestManager;
    private RentalAgreementManager rentalAgreementManager;
    private StudentMenu studentMenu;
    private HomeOwnerMenu homeOwnerMenu;


    public UserUI(UserManager userManager, Scanner scanner, PropertyManager propertyManager, RentalRequestManager rentalRequestManager, RentalAgreementManager rentalAgreementManager){
        this.userManager = userManager;
        this.scanner = scanner;
        this.propertyManager = propertyManager;
        this.rentalRequestManager = rentalRequestManager;
        this.rentalAgreementManager = rentalAgreementManager;
        this.studentMenu = new StudentMenu(scanner, propertyManager, rentalRequestManager, rentalAgreementManager);
        this.homeOwnerMenu = new HomeOwnerMenu(scanner, propertyManager, rentalRequestManager, rentalAgreementManager);
    }

    public String userMenu(){
        //this is the string of the display menu
        /*lineBr();
        System.out.println("WELCOME TO STUDENT RENTALS");
        System.out.println("To log in press 1");
        System.out.println("To sign up press 2");
        System.out.println("to exit press 3.");*/
        return "WELCOME TO STUDENT RENTALS\nto log in press 1\nto sign up press 2\nto exit press 3";
    }

    public static void lineBr(){
        System.out.println("==============================");
        System.out.println("==============================");

    }
    public User logIn(){
        //called by start this gets the users login details
        lineBr();
        System.out.println("------------LOG IN------------");
        System.out.print("Enter your email:");
        String email = scanner.next();
        scanner.nextLine();
        System.out.print("Enter your password");
        String password = scanner.nextLine();

        return userManager.login(email, password);
    }

    public void signUp(){
        //called by start this gets the users option for if they want to be a student or a homeowner
        lineBr();
        System.out.println("------------Sign Up-----------");
        int type;
        while(true){
            //System.out.print("please press 1 to sign up as a student, 2 to sign up as a homeowner:");
            type = readInt("please press 1 to sign up as a student, 2 to sign up as a homeowner:");
            if(type == 1 || type == 2){
                break;
            }else{
                System.out.println("ERROR: please enter 1 for student or 2 for homeowner.");
            }
        }
        // gets the students name, catches the exception and repeats if invalid
        String name;
        while(true){
            System.out.print("please enter your name, must be first name follwoed by last name:");
            name = scanner.nextLine();
            try {
                userManager.nameValidator(name);      
                break;          
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        // gets the students enail, catches the exception and repeats loop if invalid
        String email;
        while(true){
            System.out.print("please enter your email:");
            email = scanner.nextLine();
            try {
                userManager.emailValidator(email);
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        // gets the users password, catches the error and repeats loop if invalid format
        String password;
        while(true){ 
            System.out.print("please enter your password, must be over 6 characters:");
            password = scanner.nextLine();
            try {
                userManager.passwordValidator(password);
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }            
        }

        if(type == 1){
            try {
                //creates the student, catching exception if user already exists
                userManager.signUp("student", name, email, password); 
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }else{
            try {
                //creates a homeowner, catching exception if user already exists, email must be different
                userManager.signUp("homeowner", name, email, password);                
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void start(){
        while (true) { 
            //repeats until until exited by entering 3
            //System.out.println(userMenu());
            try {
                int forwardTo = readInt(userMenu());
                
                if(forwardTo == 1){
                    User user = logIn();

                    if (user instanceof Student) {
                        studentMenu.start((Student) user); // goes to a student menu with the student as the user
                    }else if(user instanceof HomeOwner){
                        //start homeowner menu
                        homeOwnerMenu.start((HomeOwner) user); // goes to a homeowner menu with honeowner as the user
                    }else if(user instanceof Admin){
                        //start admin menu
                    }
                } else if(forwardTo ==2){
                    signUp();
                } else if(forwardTo == 3){
                    System.out.println("thank for using student rentals!");
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("ERROR: invalid number");
            }catch (IllegalArgumentException e){
                System.out.println(e.getMessage());
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

}