package ex00;

public class Program {
    public static void main(String[] args) {
        User firstUser = new User(0, "Andrew", 100);
        User secondUser = new User(1, "John", 0);
        Transaction firstTransaction = new Transaction(firstUser, secondUser, 15);
        Transaction secondTransaction = new Transaction(secondUser, secondUser, 15);
        System.out.println(firstUser);
        System.out.println(secondUser);
        System.out.println(firstTransaction);
        System.out.println(secondTransaction);
    }
}
