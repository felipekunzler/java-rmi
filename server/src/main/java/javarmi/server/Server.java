package javarmi.server;

import javarmi.core.Service;

import java.rmi.Naming;
import java.rmi.RemoteException;

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
            MessageQueueing mq = new MessageQueueing();
            Service service = new DefaultService(mq);
            Naming.rebind(Service.REMOTE_BINDING, service);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
