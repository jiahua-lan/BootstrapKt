package online.miaostar.organization.services

import online.miaostar.organization.entities.Member
import online.miaostar.organization.entities.Organization
import online.miaostar.organization.entities.Position

interface OrganizationService {

    companion object {
        const val ORGANIZATION_MANAGER_ROLE = "ORGANIZATION_MANAGER"
        const val MEMBER_MANAGER_ROLE = "MEMBER_MANAGER"
        const val POSITION_MANAGER_ROLE = "POSITION_MANAGER"
    }

    fun create(organization: Organization): Organization
    fun organization(id: Long): Organization

    fun create(member: Member): Member
    fun member(id: Long): Member

    fun create(position: Position): Position
    fun position(id: Long): Position
}