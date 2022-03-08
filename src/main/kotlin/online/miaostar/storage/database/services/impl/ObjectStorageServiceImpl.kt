package online.miaostar.storage.database.services.impl

import online.miaostar.storage.configuration.StorageConfigurationProperties
import online.miaostar.storage.database.configuration.DatabaseStorageConfigurationProperties
import online.miaostar.storage.database.repositories.ObjectStorageRepository
import online.miaostar.storage.services.ObjectStorageService
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.io.InputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@ConditionalOnProperty(prefix = "oss", value = ["strategy"], havingValue = "DATABASE")
@Service
@Transactional
class ObjectStorageServiceImpl(
    private val objectStorageRepository: ObjectStorageRepository,
    private val storage: StorageConfigurationProperties,
    private val properties: DatabaseStorageConfigurationProperties
) : ObjectStorageService {

    fun save(stream: InputStream): String {
        val format = LocalDateTime.now().format(
            DateTimeFormatter.ofPattern("yyyy/MM/dd", Locale.getDefault())
        )
        return "${storage.prefix}"
    }

    fun remove(path: String) {}

}