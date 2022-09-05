package online.miaostar.coupon.repositories

import online.miaostar.coupon.entities.CouponTemplate
import org.springframework.data.jpa.repository.JpaRepository

interface CouponTemplateRepository : JpaRepository<CouponTemplate, Long>