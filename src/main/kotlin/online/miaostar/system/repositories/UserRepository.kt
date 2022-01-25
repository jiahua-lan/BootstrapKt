package online.miaostar.system.repositories

import online.miaostar.system.entities.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long>
