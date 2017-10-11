package javarmi.client.core;

import javarmi.core.Config;
import javarmi.core.Service;
import javarmi.core.Util;
import javarmi.core.model.News;
import javarmi.core.model.Topic;

import java.rmi.Naming;

public class Client2 {

    public static void main(String[] args) throws Exception {
        Config c = Config.getInstance();

        Service service = (Service) Naming.lookup(Util.getRemoteBinding());
        QueueConsumer queueConsumer = new QueueConsumer(c.getRabbitHost(), c.getRabbitUser(), c.getRabbitPassword());
        queueConsumer.consume("client2", System.out::println);

        service.addTopic(new Topic("tech"), "SEGREDO");
        service.addTopic(new Topic("sports"), "SEGREDO");

        service.subscribeTopic("tech", "client2");
        service.subscribeTopic("sports", "client2");

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
