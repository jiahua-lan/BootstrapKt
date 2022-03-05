package online.miaostar.system.exception

open class SystemException(
    open val code: String = "system.exception",
    open val args: List<Any> = listOf()
): RuntimeException()