package javarmi.server;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import javarmi.core.Util;
import javarmi.core.model.News;

public class MessageQueueing {

    private static final String EXCHANGE_NAME = "newsExchange";
    private static final String EXCHANGE_TYPE = "direct";

    private Channel channel;

    public MessageQueueing() {
        Util.rethrow(() -> {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            Connection connection = factory.newConnection();
            channel = connection.createChannel();
            channel.exchangeDeclare(EXCHANGE_NAME, EXCHANGE_TYPE);
        });
    }

    public void bind(String subscriber, String topic) {
        Util.rethrow(() -> {
            channel.queueDeclare(subscriber, false, false, false, null);
            channel.queueBind(subscriber, EXCHANGE_NAME, topic);
        });
    }

    public void publish(News aNews) {
        Util.rethrow(() -> {
            byte[] bytes = Util.serialize(aNews);
            channel.basicPublish(EXCHANGE_NAME, aNews.getTopicName(), null, bytes);
        });
    }

}
