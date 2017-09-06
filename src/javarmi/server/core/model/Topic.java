package javarmi.server.core.model;

import java.util.List;

public class Topic {

    private String name;
    private List<User> subscribers;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(List<User> subscribers) {
        this.subscribers = subscribers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Topic topic = (Topic) o;

        return name.equals(topic.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
