package online.miaostar.member.entities

import online.miaostar.coupon.entities.CouponTemplate
import javax.persistence.*

/**
 * 会员福利
 * */
@Entity
@Table(
    uniqueConstraints = [
        UniqueConstraint(
            columnNames = ["meta_id", "template_id"]
        )
    ]
)
data class MemberBenefits(
    @field:Id
    @field:GeneratedValue
    var id: Long? = null,
    @field:ManyToOne(optional = false)
    var meta: MemberMeta? = null,
    @field:ManyToOne(optional = false)
    var template: CouponTemplate? = null,
    @field:Column(nullable = false)
    var count: Int? = null
)
