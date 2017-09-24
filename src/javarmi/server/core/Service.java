package javarmi.server.core;


import javarmi.server.core.model.News;
import javarmi.server.core.model.Topic;
import javarmi.server.core.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface Service {

    void addTopic(Topic topic, User publisher, String password);
    List<Topic> getTopics(String password);

    boolean subscribeTopic(String topicName, User subscriber);

    void addNews(News aNews, String password);

    Optional<News> getLastNews(String topicName);
    List<News> getNews(String password);
    List<News> getNews(LocalDateTime start, LocalDateTime end, String topicName);

}
