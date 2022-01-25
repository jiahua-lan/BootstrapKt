package online.miaostar.system.entities

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.*
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Entity
@EntityListeners(AuditingEntityListener::class)
@Table(name = "sys_user")
data class User(
    @field:Id
    @field:GeneratedValue
    var id: Long? = null,
    @field:NotEmpty(message = "{user.username.isEmpty}")
    @field:Column(
        nullable = false, unique = true, updatable = false
    )
    var username: String? = null,
    @field:NotNull(message = "{user.enabled.isNull}")
    @field:Column(nullable = false)
    var enabled: Boolean? = null,
    @field:NotNull(message = "{user.locked.isNull}")
    @field:Column(nullable = false)
    var locked: Boolean? = null,
    @field:Size(
        message = "{user.roles.Size}",
        min = 1
    )
    @field:ManyToMany(fetch = FetchType.EAGER)
    var roles: MutableSet<Role> = mutableSetOf(),
    @field:OneToOne(mappedBy = "user")
    @field:JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    var credential: UserCredential? = null
) {
    @field:CreatedDate
    var createdDate: LocalDateTime? = null

    @field:LastModifiedDate
    var lastModifiedDate: LocalDateTime? = null
}


