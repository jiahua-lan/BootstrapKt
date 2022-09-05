package online.miaostar.coupon.exception

import online.miaostar.system.exception.SystemException

class CouponTemplateException(
    override val code: String = "coupon.template.exception",
    override val args: List<Any>
) : SystemException(code, args)