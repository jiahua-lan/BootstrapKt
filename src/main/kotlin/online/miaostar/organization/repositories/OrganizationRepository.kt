package online.miaostar.organization.repositories

import online.miaostar.organization.entities.Organization
import org.springframework.data.jpa.repository.JpaRepository

interface OrganizationRepository : JpaRepository<Organization, Long>