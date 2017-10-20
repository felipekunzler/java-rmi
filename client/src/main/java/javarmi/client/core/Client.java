package javarmi.client.core;

import javarmi.core.Config;
import javarmi.core.Service;
import javarmi.core.Util;
import javarmi.core.model.News;
import javarmi.core.model.Topic;

import java.rmi.Naming;
import java.util.function.Consumer;

public class Client {

    public static void main(String[] args) throws Exception {
        Service service = (Service) Naming.lookup(Util.getRemoteBinding());
        QueueConsumer queueConsumer = new QueueConsumer(Config.getRabbitHost(), Config.getRabbitUser(), Config.getRabbitPassword());
        queueConsumer.consume("client1", System.out::println);

        service.addTopic(new Topic("tech"), "SEGREDO");
        service.addTopic(new Topic("sports"), "SEGREDO");

        service.subscribeTopic("tech", "client1");

        News news = new News();
        news.setContent("first news");
        news.setTopicName("tech");
        news.setPublisher("myself");
        service.addNews(news, "SEGREDO");

        News news2 = new News();
        news2.setContent("second news");
        news2.setTopicName("sports");
        news2.setPublisher("myself");
        service.addNews(news2, "SEGREDO");
    }

}
