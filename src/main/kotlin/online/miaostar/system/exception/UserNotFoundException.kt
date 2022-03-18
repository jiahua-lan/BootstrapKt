package online.miaostar.system.exception

open class UserNotFoundException(
    val id: Long
) : ResourceNotFoundException("user.not.found.exception", listOf(id))