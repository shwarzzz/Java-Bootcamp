package school21.spring.service.models;

import java.util.Objects;
import java.util.StringJoiner;

public class User {
    public static final String TABLE_NAME = "'user'";
    private Long id;
    private String email;
    private String password;

    public User(Long id, String email) {
        this.id = id;
        this.email = email;
    }

    public User(Long id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        User other = (User) obj;
        return Objects.equals(id, other.id)
                && Objects.equals(email, other.email)
                && Objects.equals(password, other.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, password);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", User.class.getSimpleName() + "{", "}")
                .add("id=" + id.toString())
                .add("email='" + email + "'")
                .add("password='" + password + "'")
                .toString();
    }
}
