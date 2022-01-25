package online.miaostar.security.domain

import online.miaostar.system.entities.User
import org.springframework.security.core.CredentialsContainer
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

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
        password = user.credential?.credential,
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
