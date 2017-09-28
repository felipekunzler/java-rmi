package javarmi.client.core;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import javarmi.core.Util;
import javarmi.core.model.News;

import java.io.IOException;

public class QueueConsumer {

    private static final String EXCHANGE_NAME = "newsExchange";
    private static final String EXCHANGE_TYPE = "direct";

    private Channel channel;

    public QueueConsumer(String host) {
        Util.rethrow(() -> {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(host);
            Connection connection = factory.newConnection();
            channel = connection.createChannel();
            channel.exchangeDeclare(EXCHANGE_NAME, EXCHANGE_TYPE);
        });
    }

    public void consume(String subscriber, java.util.function.Consumer<News> onNewsReceived) {
        Util.rethrow(() -> {
            channel.queueDeclare(subscriber, false, false, false, null);
            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope,
                                           AMQP.BasicProperties properties, byte[] body) throws IOException {
                    System.out.println("[x] Received news; consumerTag: " + consumerTag + "; envelope: " + envelope.getRoutingKey());
                    News news = Util.deserialize(body);
                    onNewsReceived.accept(news);
                }
            };
            channel.basicConsume(subscriber, true, consumer);
        });
    }

}
