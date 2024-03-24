package ex04;

import java.util.UUID;

public class TransactionsLinkedList implements TransactionsList {
    private TransactionsNode front;
    private TransactionsNode back;
    private int size;

    public TransactionsLinkedList() {
        this.size = 0;
    }

    @Override
    public void add(Transaction value) {
        if (size == 0) {
            this.front = new TransactionsNode(value, null);
            this.back = front;
        } else {
            back.next = new TransactionsNode(value, back);
            back = back.next;
        }
        size++;
    }

    @Override
    public void removeById(UUID id) {
        TransactionsNode iterator = front;
        while (iterator != null) {
            if (id.equals(iterator.item.getId())) {
                if (iterator != front) {
                    iterator.prev.next = iterator.next;
                } else {
                    front = front.next;
                }
                if (iterator != back) {
                    iterator.next.prev = iterator.prev;
                } else {
                    back = back.prev;
                }
                size--;
                return;
            }
            iterator = iterator.next;
        }
        throw new TransactionNotFoundException("Transaction does not exist");
    }

    @Override
    public Transaction[] toArray() {
        Transaction[] array = new Transaction[size];
        TransactionsNode iterator = front;
        for (int i = 0; i < size; i++) {
            array[i] = iterator.item;
            iterator = iterator.next;
        }
        return array;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean contains(UUID id) {
        TransactionsNode iterator = front;
        while (iterator != null) {
            if (id.equals(iterator.item.getId())) {
                return true;
            }
            iterator = iterator.next;
        }
        return false;
    }


    private static class TransactionsNode {
        public Transaction item;
        public TransactionsNode next;
        public TransactionsNode prev;

        public TransactionsNode(Transaction element, TransactionsNode prev) {
            this.item = element;
            this.prev = prev;
        }
    }
}
