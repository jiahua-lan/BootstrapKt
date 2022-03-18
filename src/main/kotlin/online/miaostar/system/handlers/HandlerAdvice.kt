package online.miaostar.system.handlers

import online.miaostar.system.exception.ResourceNotFoundException
import online.miaostar.system.exception.SystemException
import org.springframework.context.MessageSource
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest

@RestControllerAdvice
class HandlerAdvice(
    private val source: MessageSource
) {
    @ExceptionHandler(SystemException::class)
    fun handle(
        e: SystemException,
        request: WebRequest
    ): ResponseEntity<*> = ResponseEntity.badRequest()
        .body(
            mapOf(
                "message" to source.getMessage(e.code, e.args.toTypedArray(), request.locale)
            )
        )

    @ExceptionHandler(ResourceNotFoundException::class)
    fun handle(e: ResourceNotFoundException): ResponseEntity<Any> = ResponseEntity
        .notFound()
        .build()

}