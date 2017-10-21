package javarmi.client.ui;

public class User {

    private String user;
    private String password;
    private Group group;

    public User(String user, String password, Group group) {
        this.user = user;
        this.password = password;
        this.group = group;
    }

    public enum Group {
        WRITER, SUBSCRIBER, GUEST
    }

    public boolean isGuest() {
        return Group.GUEST == group;
    }

    public boolean isWriter() {
        return Group.WRITER == group;
    }

    public boolean isSubscriber() {
        return Group.SUBSCRIBER == group;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

}
