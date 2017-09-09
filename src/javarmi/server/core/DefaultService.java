package javarmi.server.core;

import javarmi.server.core.model.News;
import javarmi.server.core.model.Topic;
import javarmi.server.core.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        for (Topic topic : topics) {
            if (topic.getName().equals(topicName)) {
                topic.getSubscribers().remove(reader);
                topic.getSubscribers().add(reader);
                return true;
            }
        }
        return false;
    }

    @Override
    public void addNews(News aNews, User publisher) {
        if (!topics.contains(aNews.getTopic())) {
            topics.add(aNews.getTopic());
        }
        this.news.add(aNews);
    }

    @Override
    public Optional<News> getLastNews(String topicName) {
        return news.stream()
                .filter(new Topic(topicName)::equals)
                .reduce((f, s) -> s);
    }

    @Override
    public List<News> getNews() {
        return news;
    }

    @Override
    public List<News> getNews(LocalDateTime start, LocalDateTime end, String topicName) {
        return news.stream()
                .filter(new Topic(topicName)::equals)
                .flatMap(n -> Stream.of(n.getDate())
                        .filter(start::isBefore)
                        .filter(end::isAfter)
                        .map(d -> n))
                .collect(Collectors.toList());
    }

}
