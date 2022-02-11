package online.miaostar.organization.handlers

import online.miaostar.organization.entities.Member
import online.miaostar.organization.entities.Organization
import online.miaostar.organization.entities.Position
import online.miaostar.organization.services.OrganizationService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
class OrganizationHandler(
    private val organizationService: OrganizationService
) {

    @PreAuthorize("hasRole('${OrganizationService.ORGANIZATION_MANAGER_ROLE}')")
    @PostMapping("/organization")
    fun organization(
        @RequestBody @Validated organization: Organization
    ): Organization = organizationService.create(organization)

    @GetMapping("/organization/{id}")
    fun organization(
        @PathVariable("id") id: Long
    ): Organization = organizationService.organization(id)

    @PreAuthorize("hasRole('${OrganizationService.MEMBER_MANAGER_ROLE}')")
    @PostMapping("/organization/member")
    fun member(
        @RequestBody @Validated member: Member
    ): Member = organizationService.create(member)

    @GetMapping("/organization/member/{id}")
    fun member(@PathVariable("id") id: Long): Member = organizationService.member(id)

    @PreAuthorize("hasRole('${OrganizationService.POSITION_MANAGER_ROLE}')")
    @PostMapping("/organization/position")
    fun position(
        @RequestBody @Validated position: Position
    ): Position = organizationService.create(position)

    @GetMapping("/organization/position/{id}")
    fun position(@PathVariable("id") id: Long): Position = organizationService.position(id)

}