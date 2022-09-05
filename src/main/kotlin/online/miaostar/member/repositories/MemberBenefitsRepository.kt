package online.miaostar.member.repositories

import online.miaostar.member.entities.MemberBenefits
import org.springframework.data.jpa.repository.JpaRepository

interface MemberBenefitsRepository : JpaRepository<MemberBenefits, Long>