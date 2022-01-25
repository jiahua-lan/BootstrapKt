package online.miaostar.security.services

interface PasswordEncoderUtils {
    fun matches(raw: String?, encoded: String?): Boolean
    fun encode(raw: String?): String
}