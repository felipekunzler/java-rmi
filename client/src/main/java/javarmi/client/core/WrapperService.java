package javarmi.client.core;

import javarmi.core.Service;
import javarmi.core.model.News;
import javarmi.core.model.Topic;

import java.time.LocalDateTime;
import java.util.List;

import static javarmi.core.Util.rethrow;

public class WrapperService {

    private Service service;

    public WrapperService(Service service) {
        this.service = service;
    }

    public void addTopic(Topic topic, String password) {
        rethrow(() -> service.addTopic(topic, password));
    }

    public List<Topic> getTopics() {
        return rethrow(() -> service.getTopics());
    }

    public void subscribeTopic(String topicName, String subscriber) {
        rethrow(() -> service.subscribeTopic(topicName, subscriber));
    }

    public void addNews(News aNews, String password) {
        rethrow(() -> service.addNews(aNews, password));
    }

    public News getLastNews(String topicName) {
        return rethrow(() -> service.getLastNews(topicName));
    }

    public List<News> getNews(String password) {
        return rethrow(() -> service.getNews(password));
    }

    public List<News> getNews(LocalDateTime start, LocalDateTime end, String topicName) {
        return rethrow(() -> service.getNews(start, end, topicName));
    }

}
