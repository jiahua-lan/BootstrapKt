package online.miaostar.coupon.services.impl

import online.miaostar.coupon.entities.CouponTemplate
import online.miaostar.coupon.exception.CouponTemplateNotFoundException
import online.miaostar.coupon.repositories.CouponRepository
import online.miaostar.coupon.repositories.CouponTemplateRepository
import online.miaostar.coupon.services.CouponService
import org.springframework.data.domain.Example
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CouponServiceImpl(
    private val couponRepository: CouponRepository,
    private val couponTemplateRepository: CouponTemplateRepository
) : CouponService {

    override fun template(template: CouponTemplate): CouponTemplate = couponTemplateRepository.save(template)

    override fun template(id: Long): CouponTemplate = couponTemplateRepository.findById(id).orElseThrow {
        CouponTemplateNotFoundException(id)
    }

    override fun template(id: Long, template: CouponTemplate): CouponTemplate =
        couponTemplateRepository.findById(id).map {
            couponTemplateRepository.save(template)
        }.orElseThrow {
            CouponTemplateNotFoundException(id)
        }

    override fun templates(
        probe: CouponTemplate,
        pageable: Pageable
    ): Page<CouponTemplate> = couponTemplateRepository.findAll(
        Example.of(probe),
        pageable
    )
}