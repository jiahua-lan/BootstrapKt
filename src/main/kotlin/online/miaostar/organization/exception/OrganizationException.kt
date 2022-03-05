package online.miaostar.organization.exception

import online.miaostar.system.exception.SystemException

data class OrganizationException(
    override val code: String = "organization.module.exception",
    override val args: List<Any> = listOf()
) : SystemException(code, args)