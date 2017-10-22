package javarmi.core;

import org.apache.commons.configuration2.CompositeConfiguration;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.ConfigurationUtils;
import org.apache.commons.configuration2.ImmutableConfiguration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.io.File;

public class Config {

    private static ImmutableConfiguration configuration;

    private Config() {
    }

    public static ImmutableConfiguration getConfig() {
        if (configuration == null) {
            CompositeConfiguration compositeConfig = new CompositeConfiguration();
            addConfiguration(compositeConfig, "config.properties");
            addConfiguration(compositeConfig, "default.properties");
            configuration = ConfigurationUtils.unmodifiableConfiguration(compositeConfig);
        }
        return configuration;
    }

    private static void addConfiguration(CompositeConfiguration compositeConfiguration, String file) {
        Configurations configurations = new Configurations();
        try {
            Configuration defaultConfig = configurations.properties(new File(file));
            compositeConfiguration.addConfiguration(defaultConfig);
        }
        catch (ConfigurationException e) {
        }
    }

    public static String getRabbitHost() {
        return getConfig().getString("rabbitmq.serverhost");
    }

    public static String getRabbitUser() {
        return getConfig().getString("rabbitmq.username");
    }

    public static String getRabbitPassword() {
        return getConfig().getString("rabbitmq.password");
    }

    public static int getMaxNews() {
        return getConfig().getInt("rmi.maxNewsPerTopic");
    }

    public static String getPublisherSecret() {
        return getConfig().getString("publisher.secret");
    }
}
