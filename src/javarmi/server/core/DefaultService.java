package javarmi.server.core;

import javarmi.server.core.model.News;
import javarmi.server.core.model.Topic;
import javarmi.server.core.model.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DefaultService implements Service {

    private List<Topic> topics = new ArrayList<>();
    private List<News> news = new ArrayList<>();
    private List<User> publishers = new ArrayList<>();
    private List<User> users = new ArrayList<>();


    @Override
    public void addTopic(Topic topic, User publisher) {
        publishers.remove(publisher);
        publishers.add(publisher);
        topics.add(topic);
    }

    @Override
    public List<Topic> getTopics() {
        return topics;
    }

    @Override
    public boolean subscribeTopic(String topicName, User reader) {
        for (Topic topic: topics) {
            if (topic.getName().equals(topicName)) {
                topic.getSubscribers().remove(reader);
                topic.getSubscribers().add(reader);
                return true;
            }
        }
        return false;
    }

    @Override
    public void addNews(News news, User publisher) {
        if (!topics.contains(news.getTopic())) {
            topics.add(news.getTopic());
        }
        // TODO: Add news
    }

    @Override
    public News getLastNews(String topicName) {
        return null;
    }

    @Override
    public List<News> getNews() {
        return null;
    }

    @Override
    public List<News> getNews(Date start, Date end, String topicName) {
        return null;
    }

}
