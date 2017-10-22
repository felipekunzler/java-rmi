package javarmi.server;

import javarmi.core.Config;
import javarmi.core.Service;
import javarmi.core.model.News;
import javarmi.core.model.Topic;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.time.LocalDateTime;

public class Server {

    public static void main(String[] args) throws RemoteException {
        Server server = new Server();
        server.start();
    }

    public void start() {
        try {
            // Call rmiregistry with the Service interface on the classpath first.
            // rmiregistry -J-classpath -J/java-rmi/core/out/production/classes
            // RabbitMQ server also need to be running: rabbitmq-server
            MessageQueueing mq = new MessageQueueing(Config.getRabbitHost(), Config.getRabbitUser(), Config.getRabbitPassword());
            Service service = new DefaultService(mq, Config.getPublisherSecret());
            Naming.rebind(Service.REMOTE_BINDING, service);
            mockNews(service);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void mockNews(Service service) throws RemoteException {
        String secret = Config.getPublisherSecret();

        service.addTopic(new Topic("Tech"), secret);
        News newsA = new News();
        newsA.setPublisher("Admin");
        newsA.setTopicName("Tech");
        newsA.setTitle("Diam tamquam forensibus ei mel");
        newsA.setContent("Lorem ipsum dolor sit amet, diam tamquam forensibus ei mel, meis recusabo sententiae cu per.");
        newsA.setDate(LocalDateTime.now().plusDays(2));

        News newsB = new News();
        newsB.setPublisher("Admin");
        newsB.setTopicName("Tech");
        newsB.setTitle("In brute autem modus has");
        newsB.setContent("Quo epicurei deterruisset ut, per vide soleat luptatum ex. In nec amet agam persecuti. Qui no unum pertinax repudiare.");
        newsB.setDate(LocalDateTime.now().minusDays(2));

        service.addNews(newsA, secret);
        service.addNews(newsB, secret);
    }

}
