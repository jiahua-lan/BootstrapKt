package online.miaostar.storage.minio.services.impl

import io.minio.BucketExistsArgs
import io.minio.MakeBucketArgs
import io.minio.MinioClient
import io.minio.PutObjectArgs
import online.miaostar.storage.configuration.StorageConfigurationProperties
import online.miaostar.storage.minio.configuration.MinioConfigurationProperties
import online.miaostar.storage.services.ObjectStorageService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.InitializingBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@ConditionalOnProperty(prefix = "oss", value = ["strategy"], havingValue = "MINIO")
@Service
class MinioServiceImpl(
    private val storage: StorageConfigurationProperties,
    private val minio: MinioConfigurationProperties
) : ObjectStorageService, InitializingBean {

    private lateinit var client: MinioClient

    private val logger: Logger = LoggerFactory.getLogger("oss.minio")

    override fun afterPropertiesSet() {
        this.client = MinioClient.builder()
            .endpoint(minio.endpoint)
            .credentials(minio.credentials.accessKey, minio.credentials.secretKey)
            .build()

        this.client.bucketExists(
            BucketExistsArgs.builder()
                .bucket(minio.bucket)
                .build()
        ).takeUnless {
            logger.info("bucket name: {}", minio.bucket)
            logger.info("bucket exists: {}", it)
            it
        }?.let {
            client.makeBucket(
                MakeBucketArgs.builder()
                    .bucket(minio.bucket)
                    .build()
            )
        }

    }

    override fun putObject(file: MultipartFile): String = client.putObject(
        PutObjectArgs.builder()
            .`object`(file.originalFilename)
            .contentType(file.contentType)
            .bucket(minio.bucket)
            .stream(file.inputStream, file.inputStream.available().toLong(), -1)
            .build()
    ).let {
        storage.prefix + it.`object`()
    }

}