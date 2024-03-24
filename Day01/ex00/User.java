package ex00;

public class User {
    private final Integer id;
    private final String name;
    private Integer balance;

    public User(Integer id, String name, Integer balance) {
        if (balance < 0) {
            System.out.println("Balance cannot be negative");
            System.exit(-1);
        }
        this.id = id;
        this.name = name;
        this.balance = balance;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        if (balance < 0) {
            System.out.println("Balance cannot be negative");
            System.exit(-1);
        }
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Username: " + name + ", ID " + id + ", balance " + balance;
    }
}
