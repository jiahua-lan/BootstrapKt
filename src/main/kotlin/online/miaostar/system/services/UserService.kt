package online.miaostar.system.services

import online.miaostar.system.entities.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface UserService {
    companion object {
        const val USER_ROLE = "USER"
        const val ROOT_ROLE = "ROOT"
        val roles = arrayOf(
            USER_ROLE,
            ROOT_ROLE
        )
    }

    fun users(probe: User, pageable: Pageable): Page<User>
    fun user(id: Long): User
    fun user(user: User): User
    fun user(id: Long, user: User): User
}

