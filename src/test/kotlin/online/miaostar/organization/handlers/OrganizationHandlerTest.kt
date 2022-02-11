package online.miaostar.organization.handlers

import org.hamcrest.Matchers
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

@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
internal class OrganizationHandlerTest {

    @field:Autowired
    private lateinit var mock: MockMvc

    @Test
    @WithUserDetails(value = "mike")
    fun organization() {
        mock.post("/organization") {
            contentType = MediaType.APPLICATION_JSON
            content = """
                {
                    "code": "C0001",
                    "name": "C",
                    "type": {
                        "id": 20
                    }
                }
            """.trimIndent()
            with(csrf())
        }.andDo {
            log()
        }.andExpect {
            status {
                isOk()
            }
            jsonPath("$.id", Matchers.notNullValue())
        }
    }

    @Test
    fun `get organization`() {
        mock.get("/organization/{id}", 30) {
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
    @WithUserDetails(value = "mike")
    fun member() {
        mock.post("/organization/member") {
            contentType = MediaType.APPLICATION_JSON
            content = """
                {
                    "code": "EMPLOYEE_02",
                    "name": "Mike",
                    "account": {
                        "id": 5
                    },
                    "positions": [
                        { "id": 41 }
                    ]
                }
            """.trimIndent()
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
    fun `get member`() {
        mock.get("/organization/member/{id}", 50) {
            with(csrf())
        }.andDo {
            log()
        }.andExpect {
            status { isOk() }
        }
    }

    @Test
    @WithUserDetails(value = "mike")
    fun position() {
        mock.post("/organization/position") {
            contentType = MediaType.APPLICATION_JSON
            content = """
                {
                    "name": "accountant",
                    "code": "ACCOUNTANT",
                    "organization": {
                        "id": 30
                    }
                }
            """.trimIndent()
            with(csrf())
        }.andDo {
            log()
        }.andExpect {
            status { isOk() }
        }
    }

    @Test
    fun `get position`() {
        mock.get("/organization/position/{id}", 40) {
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