package online.miaostar.storage.handler

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
class UploadHandler {

    private val logger: Logger = LoggerFactory.getLogger("upload")

    @PostMapping("/upload")
    fun upload(@RequestParam("file") file: MultipartFile): ResponseEntity<Any> {
        logger.info(
            """
                
                filename: ${file.resource.filename}
                content-type: ${file.contentType}
                original: ${file.originalFilename}
                
            """.trimIndent()
        )
        return ResponseEntity.ok().build()
    }
}