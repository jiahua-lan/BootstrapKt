package online.miaostar.member.entities

import online.miaostar.system.entities.Role
import javax.persistence.*

/**
 * 会员等级元数据
 * */
@Entity
data class MemberMeta(
    @field:Id
    @field:GeneratedValue
    var id: Long? = null,
    @field:Column(nullable = false)
    var name: String? = null,
    @field:Column(nullable = false)
    var level: Int? = null,
    @field:ManyToOne(optional = false)
    var role: Role? = null
) {
    @field:OneToMany(mappedBy = "meta", fetch = FetchType.EAGER)
    var benefits: MutableSet<MemberBenefits> = mutableSetOf()
}