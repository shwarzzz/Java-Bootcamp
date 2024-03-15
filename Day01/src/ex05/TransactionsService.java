package ex05;

import java.util.UUID;

public class TransactionsService {
    private UsersList users;

    public TransactionsService() {
        users = new UsersArrayList();
    }

    public void addUser(String userName, Integer startBalance) {
        users.add(new User(userName, startBalance));
    }

    public int getUserBalance(int userId) {
        return users.getById(userId).getBalance();
    }

    public String getUserName(int userId) {
        return users.getById(userId).getName();
    }

    public void performTransaction(Integer senderId, Integer recipientId, Integer amount) {
        User sender = users.getById(senderId);
        User recipient = users.getById(recipientId);
        if (sender.getBalance() - amount < 0 || recipient.getBalance() + amount < 0) {
            throw new IllegalTransactionException();
        }
        addTransactionToUsers(sender, recipient, amount);
    }

    public Transaction[] getUserTransactions(Integer userId) {
        return users.getById(userId).getTransactionsList();
    }

    public Transaction removeUserTransaction(Integer userId, UUID transactionId) {
        return users.getById(userId).removeTransaction(transactionId);
    }

    public Transaction[] checkValidityOfTransactions() {
        TransactionsList result = new TransactionsLinkedList();
        for (int i = 0; i < users.size(); i++) {
            for (Transaction t : users.getByIndex(i).getTransactionsList()) {
                User recipient = t.getRecipient();
                if (!recipient.doesTransactionExist(t.getId())) {
                    result.add(t);
                }
            }
        }
        return result.toArray();
    }

    public int getUsersCount() {
        return users.size();
    }

    public int getUserId(int index) {
        return users.getByIndex(index).getId();
    }

    private void addTransactionToUsers(User sender, User recipient, Integer amount) {
        Transaction senderTransaction = new Transaction(sender, recipient, amount);
        sender.addTransaction(senderTransaction);
        sender.setBalance(sender.getBalance() - amount);
        Transaction recipientTransaction = new Transaction(senderTransaction.getId(), recipient, sender, -amount);
        recipient.addTransaction(recipientTransaction);
        recipient.setBalance((recipient.getBalance() + amount));
    }
}
