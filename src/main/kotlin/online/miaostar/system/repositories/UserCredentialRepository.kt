package online.miaostar.system.repositories

import online.miaostar.system.entities.UserCredential
import org.springframework.data.jpa.repository.JpaRepository

interface UserCredentialRepository : JpaRepository<UserCredential, Long>
