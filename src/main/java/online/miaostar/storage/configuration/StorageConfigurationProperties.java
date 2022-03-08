package online.miaostar.storage.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("oss")
public class StorageConfigurationProperties implements InitializingBean {

    private final Logger logger = LoggerFactory.getLogger("oss");

    private String prefix;

    private Strategy strategy;

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public Strategy getStrategy() {
        return strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public void afterPropertiesSet() {
        logger.info("\n" +
                "prefix: {}\n" +
                "strategy: {}", prefix, strategy);
    }

    public enum Strategy {
        DATABASE,
        MINIO
    }
}
