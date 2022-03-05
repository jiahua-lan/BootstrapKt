package online.miaostar.system.exception

open class ResourceNotFoundException(
    override val code: String = "resource.not.found.exception",
    override val args: List<Any> = listOf()
) : SystemException(code, args)