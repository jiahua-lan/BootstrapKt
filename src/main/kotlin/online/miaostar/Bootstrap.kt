package online.miaostar

import com.fasterxml.jackson.databind.Module
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import online.miaostar.UserService.Companion.ROOT_ROLE
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.domain.*
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.config.BeanIds
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.CredentialsContainer
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.csrf.CookieCsrfTokenRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.security.Principal
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Entity
@EntityListeners(AuditingEntityListener::class)
@Table(name = "sys_user")
data class User(
    @field:Id
    @field:GeneratedValue
    var id: Long? = null,
    @field:NotEmpty(message = "{user.username.isEmpty}")
    @field:Column(nullable = false, unique = true, updatable = false)
    var username: String? = null,
    @field:Lob
    @field:Column(nullable = false)
    var password: String? = null,
    @field:NotNull(message = "{user.enabled.isNull}")
    @field:Column(nullable = false)
    var enabled: Boolean? = null,
    @field:NotNull(message = "{user.locked.isNull}")
    @field:Column(nullable = false)
    var locked: Boolean? = null,
    @field:Size(message = "{user.roles.Size}", min = 1)
    @field:ManyToMany(fetch = FetchType.EAGER)
    var roles: MutableSet<Role> = mutableSetOf()
) {
    @field:CreatedDate
    var createdDate: LocalDateTime? = null

    @field:LastModifiedDate
    var lastModifiedDate: LocalDateTime? = null
}

@Entity
@Table(name = "sys_role")
data class Role(
    @field:Id
    @field:GeneratedValue
    var id: Long? = null,
    @field:Column(nullable = false, unique = true, updatable = false)
    var code: String? = null,
    @field:Column(nullable = false)
    var name: String? = null
) {
    @field:ManyToMany(mappedBy = "roles")
    var users: MutableSet<User> = mutableSetOf()
}

interface UserRepository : JpaRepository<User, Long>
interface RoleRepository : JpaRepository<Role, Long>

interface UserService {
    companion object {
        const val USER_ROLE = "USER"
        const val ROOT_ROLE = "ROOT"
    }

    fun users(probe: User, pageable: Pageable): Page<User>
    fun user(id: Long): User
    fun user(user: User): User
    fun user(id: Long, user: User): User
}

@Service
@Transactional
class UserServiceImpl(
    private val userRepository: UserRepository
) : UserService {

    override fun users(
        probe: User,
        pageable: Pageable
    ): Page<User> = userRepository.findAll(
        Example.of(
            probe,
            ExampleMatcher.matching().withIgnoreNullValues()
        ),
        pageable
    )

    override fun user(id: Long): User = userRepository.findById(id).orElseThrow {
        RuntimeException()
    }

    override fun user(user: User): User = userRepository.save(user)

    override fun user(id: Long, user: User): User = userRepository.findById(id).map {
        userRepository.save(user)
    }.orElseThrow {
        RuntimeException()
    }
}

@RestController
class UserHandler(
    private val userService: UserService
) {

    @PreAuthorize("hasRole('${ROOT_ROLE}')")
    @GetMapping("/users")
    fun users(probe: User, pageable: Pageable): Page<User> = userService.users(probe, pageable)

    @PreAuthorize("hasRole('${ROOT_ROLE}')")
    @GetMapping("/user/{id}")
    fun user(@PathVariable("id") id: Long): User = userService.user(id)

    @PostMapping("/user")
    fun user(@RequestBody @Validated user: User): User = userService.user(user)

    @PreAuthorize("hasRole('${ROOT_ROLE}')")
    @PutMapping("/user/{id}")
    fun user(
        @PathVariable("id") id: Long,
        @RequestBody @Validated user: User
    ): User = userService.user(id, user)
}

data class UserDetailsImpl(
    val id: Long,
    private val username: String,
    private var password: String?,
    private val locked: Boolean,
    private val enabled: Boolean,
    private val authorities: MutableCollection<out GrantedAuthority>
) : UserDetails, CredentialsContainer {

    constructor(user: User) : this(
        id = user.id!!,
        username = user.username!!,
        password = user.password,
        enabled = user.enabled!!,
        locked = user.locked!!,
        authorities = user.roles.map {
            SimpleGrantedAuthority("ROLE_${it.code}")
        }.toMutableSet()
    )

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = this.authorities
    override fun getPassword(): String? = this.password
    override fun getUsername(): String = this.username
    override fun isAccountNonExpired(): Boolean = true
    override fun isAccountNonLocked(): Boolean = this.locked.not()
    override fun isCredentialsNonExpired(): Boolean = true
    override fun isEnabled(): Boolean = this.enabled
    override fun eraseCredentials() {
        this.password = null
    }
}

@Service(BeanIds.USER_DETAILS_SERVICE)
@Transactional
class UserDetailsServiceImpl(
    private val userRepository: UserRepository
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails = userRepository.findOne(
        Example.of(User(username = username))
    ).map { UserDetailsImpl(it) }.orElseThrow { UsernameNotFoundException(username) }

}

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditor")
class SecurityConfigurations {
    @Bean
    fun encoder(): PasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder()

    @Bean
    fun auditor(): AuditorAware<User> = AuditorAware {
        Optional.ofNullable(SecurityContextHolder.getContext())
            .map { it.authentication }
            .filter { it.isAuthenticated }
            .map { it.principal }
            .filter { it is UserDetailsImpl }
            .map { it as UserDetailsImpl }
            .map { User(id = it.id) }
    }
}

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class WebSecurityConfigurations(
    private val encoder: PasswordEncoder,
    @Qualifier(BeanIds.USER_DETAILS_SERVICE)
    private val userDetailsService: UserDetailsService
) : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        http.cors {
            it.disable()
        }.csrf {
            it.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
        }.formLogin {
            it.successHandler { _, response, _ ->
                response.apply {
                    status = HttpStatus.OK.value()
                    contentType = MediaType.APPLICATION_JSON_VALUE
                    writer.write("""{ "message": "success" }""")
                }
            }.failureHandler { _, response, exception ->
                response.apply {
                    status = HttpStatus.UNAUTHORIZED.value()
                    contentType = MediaType.APPLICATION_JSON_VALUE
                    writer.write("""{ "message": "${exception.message}" }""")
                }
            }
        }.logout {
            it.clearAuthentication(true)
                .invalidateHttpSession(true)
                .logoutSuccessHandler { _, response, _ ->
                    response.apply {
                        status = HttpStatus.OK.value()
                        contentType = MediaType.APPLICATION_JSON_VALUE
                        writer.write("""{ "message": "success" }""")
                    }
                }
        }.exceptionHandling {
            it.accessDeniedHandler { _, response, exception ->
                response.apply {
                    status = HttpStatus.FORBIDDEN.value()
                    contentType = MediaType.APPLICATION_JSON_VALUE
                    writer.write("""{ "message": "${exception.message}" }""")
                }
            }.authenticationEntryPoint { _, response, exception ->
                response.apply {
                    status = HttpStatus.UNAUTHORIZED.value()
                    contentType = MediaType.APPLICATION_JSON_VALUE
                    writer.write("""{ "message": "${exception.message}" }""")
                }
            }
        }
    }

    override fun configure(builder: AuthenticationManagerBuilder) {
        builder.eraseCredentials(true)
            .userDetailsService(userDetailsService)
            .passwordEncoder(encoder)
    }
}

@RestController
class PrincipalHandler {
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/self")
    fun principal(principal: Principal): Principal = principal
}

@Configuration
class Configurations {
    @Bean
    fun hibernate5(): Module = Hibernate5Module()

    @Bean
    fun jdk8(): Module = Jdk8Module()

    @Bean
    fun kotlin(): Module = KotlinModule.Builder()
        .enable(KotlinFeature.NullToEmptyCollection)
        .enable(KotlinFeature.NullToEmptyMap)
        .build()
}

@SpringBootApplication
class Bootstrap

fun main(args: Array<String>) {
    runApplication<Bootstrap>(*args)
}
