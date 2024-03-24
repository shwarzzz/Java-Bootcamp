package edu.school21.models;

public class User {
    public static final String ENTITY_TABLE_NAME = "user";
    private Long id;
    private String login;
    private String password;
    private Boolean authenticationStatus;

    public User(Long id, String login, String password, Boolean authenticationStatus) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.authenticationStatus = authenticationStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getAuthenticationStatus() {
        return authenticationStatus;
    }

    public void setAuthenticationStatus(Boolean authenticationStatus) {
        this.authenticationStatus = authenticationStatus;
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
        return (id == other.getId() || (id != null && id.equals(other.getId())))
                && (login == other.login
                || (login != null && login.equals(other.getLogin())))
                && (password == other.password
                || (password != null && password.equals(other.getPassword())))
                && (authenticationStatus == other.authenticationStatus
                || (authenticationStatus != null
                && authenticationStatus.equals(other.getAuthenticationStatus())));
    }

    @Override
    public int hashCode() {
        int result = 1;
        final int FACTOR = 7;
        result = result * FACTOR + (id == null ? 0 : id.hashCode());
        result = result * FACTOR + (login == null ? 0 : login.hashCode());
        result = result * FACTOR + (password == null ? 0 : password.hashCode());
        result = result * FACTOR + (authenticationStatus == null ? 0 :
                authenticationStatus.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "{id = " + id + ", login = \"" + login + "\", password = \"" +
                password + "\", authentication status = " +
                authenticationStatus + "|";
    }
}
