package online.miaostar.system.exception

data class ResourceNotFoundException(
    override var code: String = "resource.not.found.exception",
    override var args: MutableList<Any> = mutableListOf()
) : SystemException(code, args)