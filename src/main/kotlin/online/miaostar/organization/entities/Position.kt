package online.miaostar.organization.entities

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import javax.persistence.*
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

@Entity
@JsonIgnoreProperties(value = ["members"])
data class Position(
    @field:Id
    @field:GeneratedValue
    var id: Long? = null,
    @field:NotEmpty(message = "position.code.isEmpty")
    @field:Column(nullable = false, unique = true)
    var code: String? = null,
    @field:NotEmpty(message = "position.name.isEmpty")
    @field:Column(nullable = false)
    var name: String? = null,
    @field:NotNull(message = "{position.organization.isNull}")
    @field:ManyToOne(optional = false)
    var organization: Organization? = null
) {
    @field:ManyToMany(mappedBy = "positions")
    var members: MutableSet<Member> = mutableSetOf()
}