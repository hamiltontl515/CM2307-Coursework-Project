import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Main{

    private static UserRepository userrepo = new UserRepository();
    private static PropertyRepository proprepo = new PropertyRepository();
    private static RentalRequestRepository requestrepo = new RentalRequestRepository();
    private static RentalAgreementRepository agreementrepo = new RentalAgreementRepository();
    public static void main(String[] args) {
        //create some sample students and homeowners
        User user1 = UserFactory.createUser("student", "U001", "Tim Tim", "tim@unimail.com", "123456");
        User user2 = UserFactory.createUser("student", "U002", "Adam Adam", "adam@unimail.com", "123456");
        User user3 = UserFactory.createUser("student", "U003", "Steve Marley", "steve@unimail.com", "123456");
        User user4 = UserFactory.createUser("homeowner", "U004", "Mark Clapton", "mark@email.com", "123456");
        User user5 = UserFactory.createUser("homeowner", "U005", "Tammy Knopfler", "tammy@unimail.com", "123456");
        userrepo.AddUser(user1);
        userrepo.AddUser(user2);
        userrepo.AddUser(user3);
        userrepo.AddUser(user4);
        userrepo.AddUser(user5);
        userrepo.setRepositoryIndex(5);

        Property property1 = new Property("P001", "21 norfolk road", "cardiff", "U004");
        Property property2 = new Property("P002", "22 norfolk road", "cardiff", "U004");
        Property property3 = new Property("P003", "23 norfolk road", "cardiff", "U005");
        Property property4 = new Property("P004", "34 eastdown road", "swansea", "U005");

        Room p1r1 = new Room("RO001", "downstairs double bed ensuite", 130.0);
        Room p1r2 = new Room("RO002", "downstairs single bed ensuite", 120.0);
        Room p1r3 = new Room("RO003", "upstairs double bed ensuite", 110.0);
        Room p1r4 = new Room("RO004", "upstairs double bed ensuite", 130.0);
        Room p2r1 = new Room("RO006", "downstairs ensuite", 130.0);
        Room p4r1 = new Room("RO005", "blah blah blah description", 120.0);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        p1r1.addBooking(LocalDate.parse("10-09-2025", formatter), LocalDate.parse("10-10-2025", formatter));
        p1r1.addBooking(LocalDate.parse("10-11-2025", formatter), LocalDate.parse("10-12-2025", formatter));
        p2r1.addBooking(LocalDate.parse("10-11-2026", formatter), LocalDate.parse("10-12-2026", formatter));


        BookingSlot r1Booking = new BookingSlot(LocalDate.parse("10-09-2025", formatter), LocalDate.parse("10-10-2025", formatter));
        BookingSlot r2Booking = new BookingSlot(LocalDate.parse("10-09-2026", formatter), LocalDate.parse("10-10-2026", formatter));

        RentalRequest r1 = new RentalRequest("R000", p1r1, (Student) user1, r1Booking);
        RentalRequest r2 = new RentalRequest("R001", p1r2, (Student) user1, r2Booking);

        r2.acceptRequest();

        RentalAgreement a1 = new RentalAgreement("A000",(HomeOwner) user4, (Student) user1, p1r1, r1Booking);

        requestrepo.addRequest(r1);
        requestrepo.addRequest(r2);
        requestrepo.setRepositoryIndex(3);
        agreementrepo.addRentalAgreement(a1);
        agreementrepo.setAgreementIndex(2);




        property1.addRoom(p1r1);
        property1.addRoom(p1r2);
        property1.addRoom(p1r3);
        property1.addRoom(p1r4);
        property4.addRoom(p4r1);
        
        proprepo.addProperty(property1);
        proprepo.addProperty(property2);
        proprepo.addProperty(property3);
        proprepo.addProperty(property4);
        proprepo.setPropertyIndex(5);
        proprepo.setRoomIndex(7);

        /*

        List<Room> noRestrictions = proprepo.roomSearch(null, null, null, null);
        List<Room> universityRestriction = proprepo.roomSearch("swansea", null, null, null);
        List<Room> priceRestriction = proprepo.roomSearch(null, null, null, 121.0);
        List<Room> timeRestriction = proprepo.roomSearch(null, LocalDate.parse("2025-09-10"), LocalDate.parse("2025-10-10"), null);
        


        for(Room room: noRestrictions){
            System.out.println(room.getRoomID()+" "+room.getRoomDescription());
        }
        System.out.println("newSearch");

        for(Room room: universityRestriction){
            System.out.println(room.getRoomID()+" "+room.getRoomDescription());
        }
        System.out.println("newSearch");

        for(Room room: priceRestriction){
            System.out.println(room.getRoomID()+" "+room.getRoomDescription());
        }
        System.out.println("newSearch");

        for(Room room: timeRestriction){
            System.out.println(room.getRoomID()+" "+room.getRoomDescription());
        }



        System.out.println(userrepo.getUser("U004"));
        */
       userrepo.userCount();
       proprepo.propertyCount();
       requestrepo.requestCount();

       Scanner scanner = new Scanner(System.in);
       UserManager userManager = new UserManager(userrepo);
       PropertyManager propertyManager = new PropertyManager(proprepo);
       RentalRequestManager requestManager = new RentalRequestManager(requestrepo);
       RentalAgreementManager agreementManager = new RentalAgreementManager(agreementrepo);
       UserUI userUI = new UserUI(userManager, scanner, propertyManager, requestManager, agreementManager);

       userUI.start();
       scanner.close();
    }
}