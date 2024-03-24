package ex05;

import java.util.UUID;

public interface TransactionsList {
    void add(Transaction value);

    Transaction removeById(UUID id);

    Transaction[] toArray();

    int size();

    boolean contains(UUID id);
}
