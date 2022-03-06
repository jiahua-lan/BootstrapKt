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
import org.springframework.test.web.servlet.put

@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
internal class OrganizationHandlerTest {

    @field:Autowired
    private lateinit var mock: MockMvc

    @Test
    @WithUserDetails(value = "mike")
    fun `create organization`() {
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
    @WithUserDetails(value = "mike")
    fun `modify organization`() {
        mock.put("/organization/{id}", 30) {
            contentType = MediaType.APPLICATION_JSON
            content = """
                {
                    "id": 30,
                    "code": "MAIN_STORE",
                    "name": "MAIN_STORE_M",
                    "type": { "id": 20 },
                    "creator": { "id": 1 }
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
    fun `get organizations`() {
        mock.get("/organizations") {
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
    fun `create member`() {
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
    @WithUserDetails(value = "mike")
    fun `modify member`() {
        mock.put("/organization/member/{id}", 50) {
            contentType = MediaType.APPLICATION_JSON
            content = """
                {
                    "id": 50,
                    "code": "EMPLOYEE_01",
                    "name": "CAT_M",
                    "account": { "id": 1 }
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

    @Test
    @WithUserDetails(value = "mike")
    fun `modify position`() {
        mock.put("/organization/position/{id}", 40) {
            contentType = MediaType.APPLICATION_JSON
            content = """
                {
                    "id": 40,
                    "code": "STORE_MANAGER",
                    "name": "STORE_MANAGER_M",
                    "organization": { "id": 30 }
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
    fun `get positions`() {
        mock.get("/organization/{organizationId}/positions", 30) {
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