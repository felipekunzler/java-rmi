package javarmi.core.model;

import java.util.ArrayList;
import java.util.List;

public class Topic {

    private String name;
    private List<String> subscribers = new ArrayList<>();
    private String publisher;

    public Topic(String topicName) {
        name = topicName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getSubscribers() {
        return subscribers;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
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
