package online.miaostar.organization.services.impl

import online.miaostar.organization.entities.Member
import online.miaostar.organization.entities.Organization
import online.miaostar.organization.entities.OrganizationType
import online.miaostar.organization.entities.Position
import online.miaostar.organization.event.OrganizationTypeEntryEvent
import online.miaostar.organization.repositories.MemberRepository
import online.miaostar.organization.repositories.OrganizationRepository
import online.miaostar.organization.repositories.OrganizationTypeRepository
import online.miaostar.organization.repositories.PositionRepository
import online.miaostar.organization.services.OrganizationService
import online.miaostar.system.event.RoleEntryEvent
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.ApplicationEventPublisherAware
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.context.event.EventListener
import org.springframework.data.domain.Example
import org.springframework.data.domain.ExampleMatcher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class OrganizationServiceImpl(
    private val organizationRepository: OrganizationRepository,
    private val organizationTypeRepository: OrganizationTypeRepository,
    private val memberRepository: MemberRepository,
    private val positionRepository: PositionRepository
) : OrganizationService, ApplicationEventPublisherAware {

    override fun create(organization: Organization): Organization = organizationRepository.save(organization)

    override fun organization(id: Long): Organization = organizationRepository.findById(id).orElseThrow {
        RuntimeException()
    }

    override fun modify(
        id: Long,
        organization: Organization
    ): Organization = organizationRepository.findById(id).map {
        organizationRepository.save(organization)
    }.orElseThrow {
        RuntimeException()
    }

    override fun create(member: Member): Member = memberRepository.save(member)

    override fun member(id: Long): Member = memberRepository.findById(id).orElseThrow {
        RuntimeException()
    }

    override fun modify(id: Long, member: Member): Member = memberRepository.findById(id).map {
        memberRepository.save(member)
    }.orElseThrow {
        RuntimeException()
    }

    override fun create(position: Position): Position = positionRepository.save(position)

    override fun position(id: Long): Position = positionRepository.findById(id).orElseThrow {
        RuntimeException()
    }

    override fun modify(
        id: Long,
        position: Position
    ): Position = positionRepository.findById(id).map {
        positionRepository.save(position)
    }.orElseThrow {
        RuntimeException()
    }

    @EventListener(OrganizationTypeEntryEvent::class)
    fun handle(event: OrganizationTypeEntryEvent) {
        event.code.takeUnless {
            organizationTypeRepository.exists(
                Example.of(
                    OrganizationType(code = it),
                    ExampleMatcher.matching().withIgnoreNullValues()
                )
            )
        }?.let {
            organizationTypeRepository.save(
                OrganizationType(
                    name = event.code,
                    code = event.code
                )
            )
        }
    }

    @EventListener(ContextRefreshedEvent::class)
    fun handle(event: ContextRefreshedEvent) {
        arrayOf(
            OrganizationService.ORGANIZATION_MANAGER_ROLE,
            OrganizationService.MEMBER_MANAGER_ROLE,
            OrganizationService.MEMBER_MANAGER_ROLE
        ).forEach {
            publisher.publishEvent(
                RoleEntryEvent(code = it)
            )
        }
    }

    private lateinit var publisher: ApplicationEventPublisher

    override fun setApplicationEventPublisher(publisher: ApplicationEventPublisher) {
        this.publisher = publisher
    }
}