package online.miaostar.organization.repositories

import online.miaostar.organization.entities.OrganizationType
import org.springframework.data.jpa.repository.JpaRepository

interface OrganizationTypeRepository : JpaRepository<OrganizationType, Long>