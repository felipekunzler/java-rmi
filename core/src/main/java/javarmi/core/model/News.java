package javarmi.core.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class News implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final int MAX_CONTENT_SIZE = 180;

    private String content;
    private LocalDateTime date = LocalDateTime.now();
    private String topicName;
    private String publisher;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        if (content.length() > MAX_CONTENT_SIZE) {
            content = content.substring(MAX_CONTENT_SIZE);
        }
        this.content = content;
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

    @Override
    public String toString() {
        return "News{" +
                "content='" + content + '\'' +
                ", date=" + date +
                ", topicName='" + topicName + '\'' +
                ", publisher='" + publisher + '\'' +
                '}';
    }

}
