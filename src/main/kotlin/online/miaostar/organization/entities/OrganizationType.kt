package online.miaostar.organization.entities

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import javax.persistence.*

@Entity
@JsonIgnoreProperties(value = ["organizations"])
data class OrganizationType(
    @field:Id
    @field:GeneratedValue
    var id: Long? = null,
    @field:Column(nullable = false, unique = true, updatable = false)
    var code: String? = null,
    @field:Column(nullable = false)
    var name: String? = null
) {
    @field:OneToMany(mappedBy = "type")
    var organizations: MutableSet<Organization> = mutableSetOf()
}