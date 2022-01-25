package online.miaostar.security.configuration

import online.miaostar.security.domain.UserDetailsImpl
import online.miaostar.system.entities.User
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import java.util.*

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditor")
class SecurityConfigurations {
    @Bean
    fun encoder(): PasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder()

    @Bean
    fun auditor(): AuditorAware<User> = AuditorAware {
        Optional.ofNullable(SecurityContextHolder.getContext()).map { it.authentication }.filter { it.isAuthenticated }
            .map { it.principal }.filter { it is UserDetailsImpl }.map { it as UserDetailsImpl }
            .map { User(id = it.id) }
    }
}

