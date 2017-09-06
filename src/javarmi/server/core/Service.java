package javarmi.server.core;


import javarmi.server.core.model.News;
import javarmi.server.core.model.Topic;
import javarmi.server.core.model.User;

import java.util.Date;
import java.util.List;

public interface Service {

    void addTopic(Topic topic, User publisher);
    List<Topic> getTopics();
    boolean subscribeTopic(String topicName, User reader);

    void addNews(News news, User publisher);
    News getLastNews(String topicName);
    List<News> getNews();
    List<News> getNews(Date start, Date end, String topicName);

}
