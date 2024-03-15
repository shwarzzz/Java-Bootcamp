package ex04;

public class Program {
    public static void main(String[] args) {
        TransactionsService service = new TransactionsService();
        service.addUser("Nick", 500);
        service.addUser("Igor", 0);
        for (int i = 0; i < 100; i++) {
            service.addUser("Test", 1000);
        }
        service.performTransaction(1, 2, 400);
        service.performTransaction(3, 1, 500);
        System.out.println("Check balance: ");
        System.out.println(service.getUserBalance(1) + " "
                + service.getUserBalance(2) + " "
                + service.getUserBalance(3));
        System.out.println("Check validity: ");
        for (Transaction t : service.checkValidityOfTransactions()) {
            System.out.println(t);
        }
        Transaction[] userTransactions = service.getUserTransactions(1);
        System.out.println("Show user 1 transactions:");
        for (int i = 0; i < userTransactions.length; i++) {
            System.out.println(userTransactions[i]);
            if (i == userTransactions.length - 1) {
                service.removeUserTransaction(1, userTransactions[i].getId());
            }
        }
        System.out.println("Show user 1 transactions after delete :");
        userTransactions = service.getUserTransactions(1);
        for (int i = 0; i < userTransactions.length; i++) {
            System.out.println(userTransactions[i]);

        }
        System.out.println("Check validity: ");
        for (Transaction t : service.checkValidityOfTransactions()) {
            System.out.println(t);
        }
    }
}
