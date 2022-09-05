package online.miaostar.coupon.repositories

import online.miaostar.coupon.entities.Coupon
import org.springframework.data.jpa.repository.JpaRepository

interface CouponRepository : JpaRepository<Coupon, Long>