package online.miaostar.organization.services

import online.miaostar.organization.entities.Member
import online.miaostar.organization.entities.Organization
import online.miaostar.organization.entities.Position
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface OrganizationService {

    companion object {
        const val ORGANIZATION_MANAGER_ROLE = "ORGANIZATION_MANAGER"
        const val MEMBER_MANAGER_ROLE = "MEMBER_MANAGER"
        const val POSITION_MANAGER_ROLE = "POSITION_MANAGER"
    }

    fun create(organization: Organization): Organization
    fun organization(id: Long): Organization
    fun modify(id: Long, organization: Organization): Organization
    fun organizations(organization: Organization, pageable: Pageable): Page<Organization>

    fun create(member: Member): Member
    fun member(id: Long): Member
    fun modify(id: Long, member: Member): Member

    fun create(position: Position): Position
    fun position(id: Long): Position
    fun modify(id: Long, position: Position): Position
}