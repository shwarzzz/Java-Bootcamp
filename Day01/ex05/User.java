package ex05;

import java.util.UUID;

public class User {
    private final Integer id;
    private final String name;
    private Integer balance;
    private TransactionsList transactions;

    public User(String name, Integer balance) {
        if (balance < 0) {
            throw new IllegalArgumentException("Balance cannot be negative");
        }
        this.id = UserIdsGenerator.getInstance().generateId();
        this.name = name;
        this.balance = balance;
        this.transactions = new TransactionsLinkedList();
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

    public Transaction[] getTransactionsList() {
        return transactions.toArray();
    }

    public void addTransaction(Transaction value) {
        transactions.add(value);
    }

    public boolean doesTransactionExist(UUID id) {
        return transactions.contains(id);
    }

    public Transaction removeTransaction(UUID transactionId) {
        return transactions.removeById(transactionId);
    }

    public void setBalance(Integer balance) {
        if (balance < 0) {
            throw new IllegalArgumentException("Balance cannot be negative");
        }
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Username: " + name + ", ID " + id +
                ", balance " + balance;
    }
}