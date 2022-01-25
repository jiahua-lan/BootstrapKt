package online.miaostar.system.services.impl

import online.miaostar.security.services.PasswordEncoderUtils
import online.miaostar.system.entities.Role
import online.miaostar.system.entities.User
import online.miaostar.system.event.RoleEntryEvent
import online.miaostar.system.repositories.RoleRepository
import online.miaostar.system.repositories.UserCredentialRepository
import online.miaostar.system.repositories.UserRepository
import online.miaostar.system.services.UserService
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.ApplicationEventPublisherAware
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.context.event.EventListener
import org.springframework.data.domain.Example
import org.springframework.data.domain.ExampleMatcher
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val userCredentialRepository: UserCredentialRepository,
    private val roleRepository: RoleRepository,
    private val utils: PasswordEncoderUtils
) : UserService, ApplicationEventPublisherAware {

    override fun users(
        probe: User, pageable: Pageable
    ): Page<User> = userRepository.findAll(
        Example.of(
            probe, ExampleMatcher.matching().withIgnoreNullValues()
        ), pageable
    )

    override fun user(id: Long): User = userRepository.findById(id).orElseThrow {
        RuntimeException()
    }

    override fun user(user: User): User = userRepository.save(user).let {
        userCredentialRepository.save(it.credential!!.apply {
            this.user = it
            this.credential = utils.encode(this.credential)
        })
        it
    }

    override fun user(id: Long, user: User): User = userRepository.findById(id).map {
        userRepository.save(user)
    }.orElseThrow {
        RuntimeException()
    }

    @EventListener(RoleEntryEvent::class)
    fun handle(event: RoleEntryEvent) {
        event.code.takeUnless {
            roleRepository.exists(Example.of(Role(code = it)))
        }?.let {
            roleRepository.save(
                Role(code = it, name = it)
            )
        }
    }

    @EventListener(ContextRefreshedEvent::class)
    fun handle(event: ContextRefreshedEvent) {
        UserService.roles.onEach {
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
