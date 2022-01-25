package online.miaostar.security.services.impl

import online.miaostar.security.domain.UserDetailsImpl
import online.miaostar.system.entities.User
import online.miaostar.system.repositories.UserRepository
import org.springframework.data.domain.Example
import org.springframework.security.config.BeanIds
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service(BeanIds.USER_DETAILS_SERVICE)
@Transactional
class UserDetailsServiceImpl(
    private val userRepository: UserRepository
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails = userRepository.findOne(
        Example.of(User(username = username))
    ).map { UserDetailsImpl(it) }.orElseThrow { UsernameNotFoundException(username) }

}
