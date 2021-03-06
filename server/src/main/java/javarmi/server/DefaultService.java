package javarmi.server;

import javarmi.core.JavaRMIException;
import javarmi.core.Service;
import javarmi.core.model.News;
import javarmi.core.model.Topic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DefaultService extends UnicastRemoteObject implements Service {

    private static final Logger log = LoggerFactory.getLogger(DefaultService.class);

    private MessageQueueing messageQueueing;
    private String publisherSecret;

    private List<Topic> topics = new ArrayList<>();
    private List<News> news = new ArrayList<>();

    public DefaultService(MessageQueueing messageQueueing, String publisherSecret) throws RemoteException {
        this.messageQueueing = messageQueueing;
        this.publisherSecret = publisherSecret;
    }

    @Override // publisher
    public synchronized void addTopic(Topic topic, String password) {
        assertPassword(password);
        log.info("New topic created {}", topic.getName());
        if (!topics.contains(topic)) {
            topics.add(topic);
        }
    }

    @Override // subscriber
    public List<Topic> getTopics() {
        return Collections.unmodifiableList(topics);
    }

    @Override // subscriber
    public synchronized void subscribeTopic(String topicName, String subscriber) {
        Optional<Topic> topic = getTopic(topicName);
        if (topic.isPresent()) {
            if (!topic.get().getSubscribers().contains(subscriber)) {
                topic.get().getSubscribers().add(subscriber);
            }
            messageQueueing.bind(subscriber, topicName);
        }
        else {
            throw new JavaRMIException("Adding news to non existing topic");
        }
    }

    @Override
    public boolean isSubscribed(String topicName, String subscriber) {
        Optional<Topic> topic = getTopic(topicName);
        if (topic.isPresent()) {
            return topic.get().getSubscribers().contains(subscriber);
        }
        return false;
    }

        @Override // subscriber
    public synchronized void unsubscribeTopic(String topicName, String subscriber) {
        Optional<Topic> topic = getTopic(topicName);
        if (topic.isPresent()) {
            topic.get().getSubscribers().remove(subscriber);
            messageQueueing.unbind(subscriber, topicName);
        }
        else {
            throw new JavaRMIException("Adding news to non existing topic");
        }
    }

    @Override // publisher
    public synchronized void addNews(News aNews, String password) {
        assertPassword(password);
        Optional<Topic> topic = getTopic(aNews.getTopicName());
        if (topic.isPresent()) {
            news.add(aNews);
            messageQueueing.publish(aNews);
        }
        else {
            throw new JavaRMIException("Adding news to non existing topic");
        }
    }

    private Optional<Topic> getTopic(String name) {
        int i = topics.indexOf(new Topic(name));
        if (i >= 0) {
            return Optional.of(topics.get(i));
        }
        return Optional.empty();
    }

    @Override // all
    public News getLastNews(String topicName) {
        for (int i = news.size() - 1; i >= 0; i--) {
            News aNews = news.get(i);
            if (aNews.getTopicName().equals(topicName)) {
                return aNews;
            }
        }
        return null;
    }

    @Override // publisher
    public List<News> getNews(String password) {
        assertPassword(password);
        return news.stream()
                .sorted(Comparator.comparing(News::getDate))
                .collect(Collectors.toList());
    }

    @Override // all
    public List<News> getNews(LocalDateTime start, LocalDateTime end, String topicName) {
        return news.stream()
                .filter(n -> n.getTopicName().equals(topicName))
                .flatMap(n -> Stream.of(n.getDate())
                        .filter(start::isBefore)
                        .filter(end::isAfter)
                        .map(d -> n))
                .sorted(Comparator.comparing(News::getDate))
                .collect(Collectors.toList());
    }

    @Override
    public boolean checkPassword(String password) {
        return publisherSecret.equals(password);
    }

    private void assertPassword(String password) {
        if (!checkPassword(password)) {
            throw new JavaRMIException("Invalid credentials");
        }
    }

}
