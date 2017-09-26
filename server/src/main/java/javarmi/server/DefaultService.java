package javarmi.server;

import javarmi.core.JavaRMIException;
import javarmi.core.Service;
import javarmi.core.model.News;
import javarmi.core.model.Topic;
import javarmi.core.model.User;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DefaultService extends UnicastRemoteObject implements Service {

    private static final int MAX_NEWS_PER_TOPIC = 5;
    private static final String SECRET = "SEGREDO";

    private List<Topic> topics = new ArrayList<>();
    private List<News> news = new ArrayList<>();
    private List<User> publishers = new ArrayList<>();

    protected DefaultService() throws RemoteException {
        super();
    }

    @Override // publisher
    public synchronized void addTopic(Topic topic, User publisher, String password) {
        checkPassword(password);
        publishers.remove(publisher);
        publishers.add(publisher);
        topics.add(topic);
    }

    @Override // publisher
    public List<Topic> getTopics(String password) {
        return topics;
    }

    @Override // subscriber
    public synchronized boolean subscribeTopic(String topicName, User subscriber) {
        for (Topic topic : topics) {
            if (topic.getName().equals(topicName)) {
                topic.getSubscribers().remove(subscriber);
                topic.getSubscribers().add(subscriber);
                return true;
            }
        }
        return false;
    }

    @Override // publisher
    public synchronized void addNews(News aNews, String password) {
        checkPassword(password);
        if (topics.contains(new Topic(aNews.getTopicName()))) {
            releaseNewsIfFull(aNews.getTopicName());
            news.add(aNews);
        }
        throw new JavaRMIException("Adding news to non existing topic");
    }

    private void releaseNewsIfFull(String topicName) {
        ListIterator<News> iterator = news.listIterator(news.size());
        int count = 0;
        while (iterator.hasPrevious()) {
            News n = iterator.previous();
            if (n.getTopicName().equals(topicName)) {
                count++;
            }
            if (count == MAX_NEWS_PER_TOPIC) {
                iterator.remove();
            }
        }
    }

    @Override // all
    public News getLastNews(String topicName) {
        return news.stream()
                .filter(n -> n.getTopicName().equals(topicName))
                .reduce((f, s) -> s)
                .orElse(null);
    }

    @Override // publisher
    public List<News> getNews(String password) {
        checkPassword(password);
        return news;
    }

    @Override // all
    public List<News> getNews(LocalDateTime start, LocalDateTime end, String topicName) {
        return news.stream()
                .filter(n -> n.getTopicName().equals(topicName))
                .flatMap(n -> Stream.of(n.getDate())
                        .filter(start::isBefore)
                        .filter(end::isAfter)
                        .map(d -> n))
                .collect(Collectors.toList());
    }

    private void checkPassword(String password) {
        if (!SECRET.equals(password)) {
            throw new JavaRMIException("Invalid credentials");
        }
    }

}
