package javarmi.server.core;


import javarmi.server.core.model.News;
import javarmi.server.core.model.Topic;
import javarmi.server.core.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface Service {

    void addTopic(Topic topic, User publisher);
    List<Topic> getTopics();
    boolean subscribeTopic(String topicName, User reader);

    void addNews(News news, User publisher);
    Optional<News> getLastNews(String topicName);
    List<News> getNews();
    List<News> getNews(LocalDateTime start, LocalDateTime end, String topicName);

}
