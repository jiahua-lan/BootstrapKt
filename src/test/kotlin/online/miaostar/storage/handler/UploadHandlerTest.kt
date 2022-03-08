package online.miaostar.storage.handler

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.mock.web.MockMultipartFile
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.multipart

@AutoConfigureMockMvc
@SpringBootTest
internal class UploadHandlerTest {

    @field:Autowired
    private lateinit var mock: MockMvc

    @Test
    fun upload() {
        mock.multipart("/upload") {
            file(MockMultipartFile("file", "hello.txt", "text/plain", "Hello World!".toByteArray()))
            with(csrf())
        }.andDo {
            log()
        }.andExpect { status { isOk() } }
    }
}