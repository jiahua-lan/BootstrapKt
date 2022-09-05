package online.miaostar.coupon.entities

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.*

/**
 * 优惠卷实例
 * */
@Entity
@EntityListeners(AuditingEntityListener::class)
@Table(
    uniqueConstraints = [
        UniqueConstraint(name = "coupon_uk", columnNames = ["code", "template_id"])
    ]
)
data class Coupon(
    @field:Id
    @field:GeneratedValue
    var id: Long? = null,
    /**
     * 优惠卷唯一码
     * */
    @field:Column(nullable = false, updatable = false)
    var code: String? = null,
    @field:Column(nullable = false)
    var name: String? = null,
    @field:ManyToOne(optional = false)
    var template: CouponTemplate? = null,
    @field:Column(nullable = false)
    var rules: String? = null,
    @field:Column(nullable = false)
    var enabled: Boolean? = null
) {

    @field:CreatedDate
    var createdDate: LocalDateTime? = null

    @field:Column(nullable = false)
    var expiration: LocalDateTime? = null

}