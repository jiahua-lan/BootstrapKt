package online.miaostar.coupon.handlers

import online.miaostar.coupon.entities.CouponTemplate
import online.miaostar.coupon.services.CouponService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
class CouponHandler(
    private val couponService: CouponService
) {

    @GetMapping("/coupon/template/{id}")
    fun template(
        @PathVariable("id") id: Long
    ): CouponTemplate = couponService.template(id)

    @GetMapping("/coupon/templates")
    fun templates(probe: CouponTemplate, pageable: Pageable): Page<CouponTemplate> =
        couponService.templates(probe, pageable)

    @PutMapping("/coupon/template/{id}")
    fun template(
        @PathVariable("id") id: Long,
        @RequestBody @Validated template: CouponTemplate
    ): CouponTemplate = couponService.template(id, template)

    @PostMapping("/coupon/template")
    fun template(
        @RequestBody @Validated template: CouponTemplate
    ): CouponTemplate = couponService.template(template)

}