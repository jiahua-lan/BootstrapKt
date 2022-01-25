package online.miaostar.security.configuration

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.config.BeanIds
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.csrf.CookieCsrfTokenRepository

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
            it.clearAuthentication(true).invalidateHttpSession(true).logoutSuccessHandler { _, response, _ ->
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
        builder.eraseCredentials(true).userDetailsService(userDetailsService).passwordEncoder(encoder)
    }
}
