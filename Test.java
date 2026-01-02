import java.util.*;
import java.time.LocalDate;

public class Test{

    private static UserRepository userrepo = new UserRepository();
    private static PropertyRepository proprepo = new PropertyRepository();
    public static void main(String[] args) {
        //create some sample students and homeowners
        User user1 = UserFactory.createUser("student", "U001", "Tim", "tim@unimail.com", "12345");
        User user2 = UserFactory.createUser("student", "U002", "Adam", "adam@unimail.com", "67891");
        User user3 = UserFactory.createUser("student", "U003", "Steve", "steve@unimail.com", "abcde");
        User user4 = UserFactory.createUser("homeowner", "U004", "Mark", "mark@email.com", "fghij");
        User user5 = UserFactory.createUser("homeowner", "U005", "Tammy", "tammy@unimail.com", "klmno");
        userrepo.AddUser(user1);
        userrepo.AddUser(user2);
        userrepo.AddUser(user3);
        userrepo.AddUser(user4);
        userrepo.AddUser(user5);

        Property property1 = new Property("P001", "21 norfolk road", "cardiff", "U004");
        Property property2 = new Property("P002", "22 norfolk road", "cardiff", "U004");
        Property property3 = new Property("P003", "23 norfolk road", "cardiff", "U005");
        Property property4 = new Property("P004", "34 eastdown road", "swansea", "U005");

        Room p1r1 = new Room("R001", "downstairs double bed ensuite", 130.0);
        Room p1r2 = new Room("R002", "downstairs single bed ensuite", 120.0);
        Room p1r3 = new Room("R003", "upstairs double bed ensuite", 110.0);
        Room p1r4 = new Room("R004", "upstairs double bed ensuite", 130.0);
        Room p4r1 = new Room("R005", "blah blah blah description", 120.0);

        p1r1.addBooking(LocalDate.parse("2025-09-10"), LocalDate.parse("2025-10-10"));
        p1r1.addBooking(LocalDate.parse("2025-09-27"), LocalDate.parse("2025-11-27"));

        property1.addRoom(p1r1);
        property1.addRoom(p1r2);
        property1.addRoom(p1r3);
        property1.addRoom(p1r4);
        property4.addRoom(p4r1);
        
        proprepo.addProperty(property1);
        proprepo.addProperty(property2);
        proprepo.addProperty(property3);
        proprepo.addProperty(property4);

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
    }
}