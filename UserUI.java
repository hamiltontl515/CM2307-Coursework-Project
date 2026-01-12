import java.util.*;

public class UserUI{
    private UserManager userManager;
    private Scanner scanner;

    public UserUI(UserManager userManager, Scanner scanner){
        this.userManager = userManager;
        this.scanner = scanner;
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
    public void logIn(){
        lineBr();
        System.out.println("------------LOG IN------------");
        System.out.print("Enter your email:");
        String email = scanner.next();
        scanner.nextLine();
        System.out.print("Enter your password");
        String password = scanner.next();
        
        try {
            User user = userManager.login(email, password);

            if (user instanceof Student) {
                //open up student menu            
                System.out.println("student menu goes here");
            }else if (user instanceof HomeOwner) {
                //open up homeowner menu
                System.out.println("homeowner menu goes here");

            }else if (user instanceof Admin){
                //open up admin menu
                System.out.println("admin menu goes here");
            }else{
                System.out.println("how have u ended up here");
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void signUp(){
        lineBr();
        System.out.println("------------Sign Up-----------");
        //student or homeowner
        int type;
        while(true){
            System.out.print("please press 1 to sign up as a student, 2 to sign up as a homeowner:");
            type = scanner.nextInt();
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
        displayUserMenu();

        int forwardTo;
        while(true){
            try {
                forwardTo = scanner.nextInt();
                if(forwardTo == 1){
                    logIn();
                    start();
                }else if(forwardTo == 2){
                    signUp();
                    start();
                }else{
                    break;
                }
            }catch(IllegalArgumentException e){
                System.out.println("ERROR: enter a valid number");
            }
        }
    }
}