package ex02;

public interface UsersList {
    int size();
    void add(User value);
    User getById(int id);
    User getByIndex(int index);
}
