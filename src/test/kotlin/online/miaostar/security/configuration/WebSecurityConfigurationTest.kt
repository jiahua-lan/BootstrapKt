package online.miaostar.security.configuration

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.context.support.WithUserDetails
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
internal class WebSecurityConfigurationTest {
    @field:Autowired
    private lateinit var mock: MockMvc

    @Test
    fun login() {
        mock.post("/login") {
            param("username", "root")
            param("password", "12345678")
            with(SecurityMockMvcRequestPostProcessors.csrf())
        }.andDo {
            log()
        }.andExpect {
            status {
                isOk()
            }
        }
    }

    @Test
    @WithUserDetails(value = "root")
    fun self() {
        mock.get("/self") {
            with(
                SecurityMockMvcRequestPostProcessors.csrf()
            )
        }
    }
}