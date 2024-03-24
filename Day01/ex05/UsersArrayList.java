package ex05;

public class UsersArrayList implements UsersList {
    private static final int START_CAPACITY = 10;
    private User[] array;
    private int size;
    private int capacity;

    public UsersArrayList() {
        array = new User[START_CAPACITY];
        capacity = START_CAPACITY;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void add(User value) {
        size++;
        if (size > capacity) {
            reserve();
        }
        array[size - 1] = value;
    }

    @Override
    public User getById(int id) {
        for (int i = 0; i < size; i++) {
            if (id == array[i].getId()) {
                return array[i];
            }
        }
        throw new UserNotFoundException("User with id = " + id + " does not exists");
    }

    @Override
    public User getByIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index exceeds list size");
        }
        return array[index];
    }

    private void reserve() {
        capacity *= 2;
        User[] newArray = new User[capacity];
        for (int i = 0; i < array.length; i++) {
            newArray[i] = array[i];
        }
        array = newArray;
    }
}