package javarmi.core.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class News implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final int MAX_CONTENT_SIZE = 180;

    private String title;
    private String content;
    private LocalDateTime date = LocalDateTime.now();
    private String topicName;
    private String publisher;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getFormattedContent() {
        return "Date: " + date + "\nPublisher: " +  publisher + "\nTopic: " + topicName + "\n\n" + content;
    }

    @Override
    public String toString() {
        return title;
    }

}
