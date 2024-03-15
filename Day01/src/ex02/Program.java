package ex02;

public class Program {
    public static void main(String[] args) {
        UsersArrayList myList = new UsersArrayList();
        System.out.println(myList.size());
        myList.add(new User("David", 100));
        myList.add(new User("Igor", 0));
        myList.add(new User("Alex", 1333));
        myList.add(new User("Nick", 521342134));
        myList.add(new User("Kirill", 78573746));
        myList.add(new User("Anna", 4444213));
        myList.add(new User("Victoria", 2334141));
        myList.add(new User("Ulya", 52135123));
        myList.add(new User("Danil", 412));
        myList.add(new User("Ivan", 0));
        myList.add(new User("Polina", 521351));
        System.out.println(myList.size());
        for (int i = 0; i < myList.size(); i++) {
            System.out.println(myList.getByIndex(i));
        }
        for (int i = myList.size(); i > 0; i--) {
            System.out.println(myList.getById(i));
        }
    }
}
