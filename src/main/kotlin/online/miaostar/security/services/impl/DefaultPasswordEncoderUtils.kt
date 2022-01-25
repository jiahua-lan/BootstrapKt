package online.miaostar.security.services.impl

import online.miaostar.security.services.PasswordEncoderUtils
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class DefaultPasswordEncoderUtils(
    private val encoder: PasswordEncoder
) : PasswordEncoderUtils {
    override fun matches(raw: String?, encoded: String?): Boolean = encoder.matches(raw, encoded)
    override fun encode(raw: String?): String = encoder.encode(raw)
}