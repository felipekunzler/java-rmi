package javarmi.client.core;

import javarmi.core.Service;
import javarmi.core.model.News;
import javarmi.core.model.Topic;

import java.rmi.Naming;
import java.util.function.Consumer;

public class Client {

    public static void main(String[] args) throws Exception {
        Service service = (Service) Naming.lookup("rmiServiceServer");
        QueueConsumer queueConsumer = new QueueConsumer("localhost");
        queueConsumer.consume("felipe", System.out::println);

        service.addTopic(new Topic("tech"), "SEGREDO");
        service.subscribeTopic("tech", "felipe");

        News news = new News();
        news.setContent("first news");
        news.setTopicName("tech");
        news.setPublisher("myself");
        service.addNews(news, "SEGREDO");

        News news2 = new News();
        news2.setContent("second news");
        news2.setTopicName("sports");
        news2.setPublisher("myself");
        service.addNews(news, "SEGREDO");
    }

}
