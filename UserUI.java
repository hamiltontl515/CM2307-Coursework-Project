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

    public static void displayUserMenu(){
        lineBr();
        System.out.println("WELCOME TO STUDENT RENTALS");
        System.out.println("To log in press 1");
        System.out.println("To sign up press 2");
        System.out.println("to exit press 3.");
    }

    public static void lineBr(){
        System.out.println("==============================");
        System.out.println("==============================");

    }
    public User logIn(){
        lineBr();
        System.out.println("------------LOG IN------------");
        System.out.print("Enter your email:");
        String email = scanner.next();
        scanner.nextLine();
        System.out.print("Enter your password");
        String password = scanner.next();

        return userManager.login(email, password);
    }

    public void signUp(){
        lineBr();
        System.out.println("------------Sign Up-----------");
        //student or homeowner
        int type;
        while(true){
            System.out.print("please press 1 to sign up as a student, 2 to sign up as a homeowner:");
            type = scanner.nextInt();
            scanner.nextLine();
            if(type == 1 || type == 2){
                break;
            }else{
                System.out.println("ERROR: please enter 1 for student or 2 for homeowner.");
            }
        }
        // name
        String name;
        while(true){
            System.out.print("please enter your name:");
            name = scanner.nextLine();
            try {
                userManager.nameValidator(name);      
                break;          
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        // email
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
        // password
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
                userManager.signUp("student", name, email, password);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }else{
            try {
                userManager.signUp("homeowner", name, email, password);                
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void start(){
        while (true) { 
            displayUserMenu();
            try {
                int forwardTo = scanner.nextInt();
                
                if(forwardTo == 1){
                    User user = logIn();

                    if (user instanceof Student) {
                        studentMenu.start((Student) user);
                    }else if(user instanceof HomeOwner){
                        //start homeowner menu
                        homeOwnerMenu.start((HomeOwner) user);
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
}