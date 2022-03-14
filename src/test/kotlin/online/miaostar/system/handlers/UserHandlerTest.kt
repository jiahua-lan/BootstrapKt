package online.miaostar.system.handlers

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithUserDetails
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.put

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
        mock.post("/user") {
            contentType = MediaType.APPLICATION_JSON
            content = """{ 
                    "username" : "NEW",
                    "enabled" : true,
                    "locked" : false,
                    "roles" : [{"id": 2}],
                    "credential": { "credential": "987654321" }
                 }""".trimMargin()
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
    fun testUser1() {
        mock.put("/user/{id}", 1) {
            contentType = MediaType.APPLICATION_JSON
            content = """{
                "id": 1,
                "username" : "NEW",
                "enabled" : true,
                "locked" : false,
                "roles" : [{"id": 2}]
             }""".trimMargin()
            with(csrf())
        }.andDo {
            log()
        }.andExpect {
            status {
                isOk()
            }
        }
    }
}