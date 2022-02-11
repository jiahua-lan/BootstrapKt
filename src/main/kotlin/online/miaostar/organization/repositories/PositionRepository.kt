package online.miaostar.organization.repositories

import online.miaostar.organization.entities.Position
import org.springframework.data.jpa.repository.JpaRepository

interface PositionRepository : JpaRepository<Position, Long>