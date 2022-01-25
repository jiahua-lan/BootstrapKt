package online.miaostar

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.context.support.WithUserDetails
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
class BootstrapTests {

    @field:Autowired
    private lateinit var mock: MockMvc

    @Test
    fun contextLoads() {
    }

    @Test
    fun login() {
        mock.post("/login") {
            param("username", "root")
            param("password", "12345678")
            with(csrf())
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
                csrf()
            )
        }
    }

}
