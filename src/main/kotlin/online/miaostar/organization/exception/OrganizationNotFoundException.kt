package online.miaostar.organization.exception

import online.miaostar.system.exception.ResourceNotFoundException

data class OrganizationNotFoundException(
    val id: Long
) : ResourceNotFoundException(
    "organization.not.found",
    listOf(id)
)