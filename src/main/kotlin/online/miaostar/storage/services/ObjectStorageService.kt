package online.miaostar.storage.services

import org.springframework.web.multipart.MultipartFile

interface ObjectStorageService {
    fun putObject(file: MultipartFile): String
}