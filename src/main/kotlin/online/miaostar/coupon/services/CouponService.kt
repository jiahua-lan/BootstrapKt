package online.miaostar.coupon.services

import online.miaostar.coupon.entities.CouponTemplate
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface CouponService {
    fun template(template: CouponTemplate): CouponTemplate
    fun template(id: Long): CouponTemplate
    fun template(id: Long, template: CouponTemplate): CouponTemplate
    fun templates(probe: CouponTemplate, pageable: Pageable): Page<CouponTemplate>
}