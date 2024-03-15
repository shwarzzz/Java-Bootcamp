package ex04;

import java.util.UUID;

public interface TransactionsList {
    void add(Transaction value);

    void removeById(UUID id);

    Transaction[] toArray();

    int size();

    boolean contains(UUID id);
}
