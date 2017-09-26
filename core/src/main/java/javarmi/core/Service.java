package javarmi.core;


import javarmi.core.model.News;
import javarmi.core.model.Topic;
import javarmi.core.model.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.List;

public interface Service extends Remote {

    void addTopic(Topic topic, User publisher, String password) throws RemoteException;
    List<Topic> getTopics(String password) throws RemoteException;

    boolean subscribeTopic(String topicName, User subscriber) throws RemoteException;

    void addNews(News aNews, String password) throws RemoteException;

    News getLastNews(String topicName) throws RemoteException;
    List<News> getNews(String password) throws RemoteException;
    List<News> getNews(LocalDateTime start, LocalDateTime end, String topicName) throws RemoteException;

}
