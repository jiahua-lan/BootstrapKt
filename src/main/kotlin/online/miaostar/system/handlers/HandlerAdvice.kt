package online.miaostar.system.handlers

import online.miaostar.system.exception.ResourceNotFoundException
import online.miaostar.system.exception.SystemException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class HandlerAdvice {
    @ExceptionHandler(SystemException::class)
    fun handle(e: SystemException): ResponseEntity<*> = ResponseEntity.badRequest()
        .body(
            mapOf(
                "message" to ""
            )
        )

    @ExceptionHandler(ResourceNotFoundException::class)
    fun handle(e: ResourceNotFoundException): ResponseEntity<Any> = ResponseEntity
        .notFound()
        .build()

}