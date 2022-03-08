package online.miaostar.storage.minio.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConditionalOnProperty(prefix = "oss", name = "strategy",havingValue = "MINIO")
@Configuration
@ConfigurationProperties("oss.minio")
public class MinioConfigurationProperties implements InitializingBean {

    private final Logger logger = LoggerFactory.getLogger("oss.minio");

    private String endpoint;

    private String bucket;

    private Credentials credentials;

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    @Override
    public void afterPropertiesSet() {
        logger.info("\n" +
                "endpoint: {}\n" +
                "bucket: {}\n", endpoint, bucket);
    }

    public static class Credentials {
        private String accessKey;
        private String secretKey;

        public String getAccessKey() {
            return accessKey;
        }

        public void setAccessKey(String accessKey) {
            this.accessKey = accessKey;
        }

        public String getSecretKey() {
            return secretKey;
        }

        public void setSecretKey(String secretKey) {
            this.secretKey = secretKey;
        }
    }

}
