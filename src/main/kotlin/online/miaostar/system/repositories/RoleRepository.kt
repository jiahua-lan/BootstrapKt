package online.miaostar.system.repositories

import online.miaostar.system.entities.Role
import org.springframework.data.jpa.repository.JpaRepository

interface RoleRepository : JpaRepository<Role, Long>
