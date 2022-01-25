package online.miaostar

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.context.support.WithUserDetails
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
internal class UserHandlerTest {

    @field:Autowired
    private lateinit var mock: MockMvc

    @Test
    @WithUserDetails(value = "root")
    fun users() {
        mock.get("/users") {
            param("page", "0")
            param("size", "5")
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
    fun user() {
        mock.get("/user/{id}", 1) {
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
    fun testUser() {

    }

    @Test
    fun testUser1() {
    }
}