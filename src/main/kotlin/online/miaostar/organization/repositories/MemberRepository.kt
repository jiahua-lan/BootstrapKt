package online.miaostar.organization.repositories

import online.miaostar.organization.entities.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Member, Long>