package online.miaostar.organization.exception

import online.miaostar.system.exception.ResourceNotFoundException

data class MemberNotFoundException(
    val id: Long
) : ResourceNotFoundException(
    "member.not.found",
    listOf(id)
)