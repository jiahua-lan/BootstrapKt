package online.miaostar.storage.database.repositories

import online.miaostar.storage.database.entities.ObjectStorage
import org.springframework.data.jpa.repository.JpaRepository

interface ObjectStorageRepository : JpaRepository<ObjectStorage, Long>