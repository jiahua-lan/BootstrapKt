package online.miaostar.storage.database.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConditionalOnProperty(prefix = "oss", value = "strategy", havingValue = "DATABASE")
@Configuration
@ConfigurationProperties("oss.database")
public class DatabaseStorageConfigurationProperties {

    private String path = "./";

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
