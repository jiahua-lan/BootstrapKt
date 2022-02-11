package online.miaostar.organization.entities

import online.miaostar.system.entities.User
import javax.persistence.*
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Size

@Entity
data class Member(
    @field:Id
    @field:GeneratedValue
    var id: Long? = null,
    @field:NotEmpty(message = "{member.code.isEmpty}")
    @field:Column(nullable = false, unique = true, updatable = false)
    var code: String? = null,
    @field:NotEmpty(message = "{member.name.isEmpty}")
    @field:Column(nullable = false)
    var name: String? = null,
    @field:ManyToOne(optional = false)
    var account: User? = null,
    @Size(message = "{member.positions.size}", min = 1)
    @field:ManyToMany(fetch = FetchType.EAGER)
    var positions: MutableSet<Position> = mutableSetOf()
)