package online.miaostar.organization.exception

import online.miaostar.system.exception.ResourceNotFoundException

data class PositionNotFoundException(
    val id: Long
) : ResourceNotFoundException(
    "position.not.found",
    listOf(id)
)