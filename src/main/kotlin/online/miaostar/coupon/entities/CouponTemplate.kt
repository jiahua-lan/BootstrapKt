package online.miaostar.coupon.entities

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import javax.persistence.*

/**
 * 优惠卷模板
 * */
@Entity
@EntityListeners(AuditingEntityListener::class)
@JsonIgnoreProperties(value = ["coupons"])
data class CouponTemplate(
    @field:Id
    @field:GeneratedValue
    var id: Long? = null,

    @field:Column(nullable = false)
    var name: String? = null,

    @field:ElementCollection(fetch = FetchType.EAGER)
    var rules: MutableSet<String> = mutableSetOf()
) {
    @field:OneToMany(mappedBy = "template")
    var coupons: MutableSet<Coupon> = mutableSetOf()
}