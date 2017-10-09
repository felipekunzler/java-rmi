package javarmi.core;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.io.File;

public class Config {

    private static final Config config = new Config();
    private Configuration configuration;

    private Config() {
        try {
            Configurations conf = new Configurations();
            configuration = conf.properties(new File("config.properties"));
        }
        catch (ConfigurationException e) {
            configuration = new PropertiesConfiguration();
        }
    }

    public static Config getInstance() {
        return config;
    }

    public String getRabbitHost() {
        return configuration.getString("rabbitmq.serverhost", "localhost");
    }

    public String getRabbitUser() {
        return configuration.getString("rabbitmq.username", "admin");
    }

    public String getRabbitPassword() {
        return configuration.getString("rabbitmq.password", "admin");
    }

    public int getMaxNews() {
        return configuration.getInt("rmi.maxNewsPerTopic", 5);
    }

}
