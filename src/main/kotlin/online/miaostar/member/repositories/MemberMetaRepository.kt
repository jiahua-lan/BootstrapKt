package online.miaostar.member.repositories

import online.miaostar.member.entities.MemberMeta
import org.springframework.data.jpa.repository.JpaRepository

interface MemberMetaRepository : JpaRepository<MemberMeta, Long>