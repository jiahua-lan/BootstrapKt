package online.miaostar.organization.entities

import online.miaostar.system.entities.User
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.*
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

@Entity
@EntityListeners(AuditingEntityListener::class)
data class Organization(
    @field:Id
    @field:GeneratedValue
    var id: Long? = null,
    @field:NotEmpty(message = "{organization.code.isEmpty}")
    @field:Column(nullable = false, unique = true)
    var code: String? = null,
    @field:NotEmpty(message = "{organization.name.isEmpty}")
    @field:Column(nullable = false)
    var name: String? = null,
    @field:NotNull(message = "{organization.type.isNull}")
    @field:ManyToOne(optional = false)
    var type: OrganizationType? = null
) {
    @field:OneToMany(mappedBy = "organization")
    var positions: MutableSet<Position> = mutableSetOf()

    @field:CreatedBy
    @field:ManyToOne
    var creator: User? = null

    @field:CreatedDate
    var createdDate: LocalDateTime? = null
}