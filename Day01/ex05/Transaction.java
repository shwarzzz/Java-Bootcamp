package ex05;

import java.util.UUID;

public class Transaction {
    private final UUID id;
    private final User recipient;
    private final User sender;
    private final TransferCategory category;
    private final Integer transferAmount;

    public Transaction(User sender, User recipient, Integer transferAmount) {
        this.id = UUID.randomUUID();
        this.recipient = recipient;
        this.sender = sender;
        if (transferAmount < 0) {
            category = TransferCategory.CREDITS;
        } else {
            category = TransferCategory.DEBITS;
        }
        this.transferAmount = transferAmount;
    }

    public Transaction(UUID id, User sender, User recipient, Integer transferAmount) {
        this.id = id;
        this.recipient = recipient;
        this.sender = sender;
        if (transferAmount < 0) {
            category = TransferCategory.CREDITS;
        } else {
            category = TransferCategory.DEBITS;
        }
        this.transferAmount = transferAmount;
    }

    public UUID getId() {
        return id;
    }

    public User getRecipient() {
        return recipient;
    }

    public User getSender() {
        return sender;
    }

    public TransferCategory getCategory() {
        return category;
    }

    public Integer getTransferAmount() {
        return transferAmount;
    }

    @Override
    public String toString() {
        return sender.getName() + " -> " + recipient.getName() + ", " + transferAmount
                + ", " + category + ", ID " + id;
    }

    public enum TransferCategory {
        DEBITS,
        CREDITS
    }
}
