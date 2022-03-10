package online.miaostar.organization.handlers

import online.miaostar.organization.entities.Member
import online.miaostar.organization.entities.Organization
import online.miaostar.organization.entities.OrganizationType
import online.miaostar.organization.entities.Position
import online.miaostar.organization.services.OrganizationService
import online.miaostar.organization.services.OrganizationService.Companion.MEMBER_MANAGER_ROLE
import online.miaostar.organization.services.OrganizationService.Companion.ORGANIZATION_MANAGER_ROLE
import online.miaostar.organization.services.OrganizationService.Companion.POSITION_MANAGER_ROLE
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
class OrganizationHandler(
    private val organizationService: OrganizationService
) {

    @GetMapping("/organization/types")
    fun types(
        probe: OrganizationType
    ): Page<OrganizationType> = organizationService.types(
        probe,
        Pageable.unpaged()
    )

    @PreAuthorize("hasRole('${ORGANIZATION_MANAGER_ROLE}')")
    @PostMapping("/organization")
    fun organization(
        @RequestBody @Validated organization: Organization
    ): Organization = organizationService.create(organization)

    @PreAuthorize("hasRole('${ORGANIZATION_MANAGER_ROLE}')")
    @PutMapping("/organization/{id}")
    fun organization(
        @PathVariable("id") id: Long,
        @RequestBody organization: Organization
    ): Organization = organizationService.modify(id, organization)

    @GetMapping("/organization/{id}")
    fun organization(
        @PathVariable("id") id: Long
    ): Organization = organizationService.organization(id)

    @GetMapping("/organizations")
    fun organization(probe: Organization, pageable: Pageable): Page<Organization> = organizationService.organizations(
        probe,
        pageable
    )

    @PreAuthorize("hasRole('${MEMBER_MANAGER_ROLE}')")
    @PostMapping("/organization/member")
    fun member(
        @RequestBody @Validated member: Member
    ): Member = organizationService.create(member)

    @PreAuthorize("hasRole('${MEMBER_MANAGER_ROLE}')")
    @PutMapping("/organization/member/{id}")
    fun member(
        @PathVariable("id") id: Long,
        @RequestBody @Validated member: Member
    ): Member = organizationService.modify(id, member)

    @GetMapping("/organization/member/{id}")
    fun member(@PathVariable("id") id: Long): Member = organizationService.member(id)

    @PreAuthorize("hasRole('${POSITION_MANAGER_ROLE}')")
    @PostMapping("/organization/position")
    fun position(
        @RequestBody @Validated position: Position
    ): Position = organizationService.create(position)

    @PreAuthorize("hasRole('${POSITION_MANAGER_ROLE}')")
    @PutMapping("/organization/position/{id}")
    fun position(
        @PathVariable("id") id: Long,
        @RequestBody @Validated position: Position
    ): Position = organizationService.modify(id, position)

    @GetMapping("/organization/position/{id}")
    fun position(@PathVariable("id") id: Long): Position = organizationService.position(id)

    @GetMapping("/organization/{organizationId}/positions")
    fun positions(
        @PathVariable("organizationId") id: Long,
        probe: Position,
        pageable: Pageable
    ): Page<Position> = organizationService.positions(probe.apply {
        this.organization = Organization(id = id)
    }, pageable)

}