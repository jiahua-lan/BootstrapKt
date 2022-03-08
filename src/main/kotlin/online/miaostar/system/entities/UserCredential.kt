package online.miaostar.system.entities

import javax.persistence.*

@Entity
data class UserCredential(
    @field:Id var id: Long? = null,
    @field:MapsId
    @field:OneToOne(optional = false)
    @field:JoinColumn(name = "id", updatable = false)
    var user: User? = null,
    @field:Lob
    @field:Column(nullable = false)
    var credential: String? = null,
)
