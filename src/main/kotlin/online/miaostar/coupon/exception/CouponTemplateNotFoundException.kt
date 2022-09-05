package online.miaostar.coupon.exception

import online.miaostar.system.exception.ResourceNotFoundException

class CouponTemplateNotFoundException(
    val id: Long
) : ResourceNotFoundException(code = "coupon.template.not.found.exception", listOf(id))