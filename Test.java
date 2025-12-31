
public class Test{

    private static UserRepository userrepo = new UserRepository();
    public static void main(String[] args) {
        User user1 = UserFactory.createUser("student", "U001", "Tim", "tim@unimail.com", "12345");
        User user2 = UserFactory.createUser("student", "U002", "Adam", "adam@unimail.com", "67891");
        User user3 = UserFactory.createUser("student", "U003", "Steve", "steve@unimail.com", "abcde");
        User user4 = UserFactory.createUser("homeowner", "U004", "Mark", "mark@email.com", "fghij");
        User user5 = UserFactory.createUser("homeowner", "U005", "Tony", "tonym@email.com", "klmno");
        User user6 = UserFactory.createUser("student", "U006", "Luke", "luke@unimail.com", "pqrst");

        userrepo.AddUser(user1);
        userrepo.AddUser(user2);
        userrepo.AddUser(user3);
        userrepo.AddUser(user4);
        userrepo.AddUser(user5);
        userrepo.AddUser(user6);

        System.out.println(userrepo.getUser("U004"));
    }
}