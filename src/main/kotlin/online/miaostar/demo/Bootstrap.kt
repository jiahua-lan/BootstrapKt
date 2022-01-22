package online.miaostar.demo

import com.fasterxml.jackson.databind.Module
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.Example
import org.springframework.data.domain.ExampleMatcher
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
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
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import java.security.Principal
import javax.persistence.*

@Entity
@Table(name = "sys_user")
data class User(
    @field:Id
    @field:GeneratedValue
    var id: Long? = null,
    @field:Column(nullable = false, unique = true, updatable = false)
    var username: String? = null,
    @field:Lob
    @field:Column(nullable = false)
    var password: String? = null,
    @field:Column(nullable = false)
    var enabled: Boolean? = null,
    @field:Column(nullable = false)
    var locked: Boolean? = null,
    @field:ManyToMany(fetch = FetchType.EAGER)
    var roles: MutableSet<Role> = mutableSetOf()
)

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
    fun users(
        probe: User,
        pageable: Pageable
    ): Page<User>

    fun user(id: Long): User
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
}

@RestController
class UserHandler(
    private val userService: UserService
) {

    @GetMapping("/users")
    fun users(probe: User, pageable: Pageable): Page<User> = userService.users(probe, pageable)

    @GetMapping("/user/{id}")
    fun user(@PathVariable("id") id: Long): User = userService.user(id)

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
class SecurityConfigurations {
    @Bean
    fun encoder(): PasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder()
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
            it.disable()
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
