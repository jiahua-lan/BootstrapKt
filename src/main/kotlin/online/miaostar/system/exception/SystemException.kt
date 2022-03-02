package online.miaostar.system.exception

open class SystemException(
    open val code: String = "system.exception",
    open val args: MutableList<Any> = mutableListOf()
): RuntimeException()