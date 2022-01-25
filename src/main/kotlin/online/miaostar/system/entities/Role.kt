package online.miaostar.system.entities

import javax.persistence.*

@Entity
@Table(name = "sys_role")
data class Role(
    @field:Id @field:GeneratedValue var id: Long? = null,
    @field:Column(nullable = false, unique = true, updatable = false) var code: String? = null,
    @field:Column(nullable = false) var name: String? = null
) {
    @field:ManyToMany(mappedBy = "roles")
    var users: MutableSet<User> = mutableSetOf()
}
