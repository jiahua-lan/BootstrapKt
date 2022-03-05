package online.miaostar.oss

import io.minio.BucketExistsArgs
import io.minio.MakeBucketArgs
import io.minio.MinioClient
import io.minio.UploadObjectArgs
import online.miaostar.oss.minio.MinioConfigurationProperties
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class OSSTests {
    @field:Autowired
    private lateinit var properties: MinioConfigurationProperties

    private val logger: Logger = LoggerFactory.getLogger("oss")

    @Test
    fun `create bucket`() {
        val client: MinioClient = MinioClient.builder()
            .endpoint(properties.endpoint)
            .credentials(properties.credentials.accessKey, properties.credentials.secretKey)
            .build()
        client.bucketExists(
            BucketExistsArgs.builder()
                .bucket(properties.bucket)
                .build()
        ).takeUnless {
            logger.info("bucket name: {}", properties.bucket)
            logger.info("bucket exists: {}", it)
            it
        }?.let {
            client.makeBucket(
                MakeBucketArgs.builder()
                    .bucket(properties.bucket)
                    .build()
            )
        }

        logger.info("------------------------------")
        logger.info("list buckets")
        client.listBuckets().forEach { bucket ->
            logger.info("------------------------------")
            logger.info("bucket name: {}", bucket.name())
            logger.info("bucket creation date: {}", bucket.creationDate())
            logger.info("------------------------------")
        }
    }

    @Test
    fun `put object`() {
        val client: MinioClient = MinioClient.builder()
            .endpoint(properties.endpoint)
            .credentials(properties.credentials.accessKey, properties.credentials.secretKey)
            .build()

        client.uploadObject(
            UploadObjectArgs.builder()
                .bucket(properties.bucket)
                .`object`("cat.png")
                .filename("/home/jiahua-lan/Nextcloud/猫/cat-2.jpeg")
                .build()
        )

    }


}