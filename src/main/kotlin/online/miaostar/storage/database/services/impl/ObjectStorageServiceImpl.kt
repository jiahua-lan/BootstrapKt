package online.miaostar.storage.database.services.impl

import online.miaostar.storage.configuration.StorageConfigurationProperties
import online.miaostar.storage.database.configuration.DatabaseStorageConfigurationProperties
import online.miaostar.storage.database.repositories.ObjectStorageRepository
import online.miaostar.storage.services.ObjectStorageService
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@ConditionalOnProperty(prefix = "oss", value = ["strategy"], havingValue = "DATABASE")
@Service
@Transactional
class ObjectStorageServiceImpl(
    private val objectStorageRepository: ObjectStorageRepository,
    private val storage: StorageConfigurationProperties,
    private val properties: DatabaseStorageConfigurationProperties
) : ObjectStorageService {

    override fun putObject(file: MultipartFile): String {
        TODO("Not yet implemented")
    }

}