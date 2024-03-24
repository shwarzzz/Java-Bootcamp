package ex03;

public class Program {
    public static void main(String[] args) {
        TransactionsLinkedList myList = new TransactionsLinkedList();
        System.out.println(myList.size());
        User first = new User("First", 500);
        User second = new User("Second", 500);
        for (int i = 0; i < 10000; i++) {
            myList.add(new Transaction(first, second, 100));
        }
        for (Transaction t : myList.toArray()) {
            System.out.println(t);
            myList.removeById(t.getId());
        }
        System.out.println(myList.size());
    }
}
