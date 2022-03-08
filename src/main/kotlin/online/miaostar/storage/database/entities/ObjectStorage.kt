package online.miaostar.storage.database.entities

import online.miaostar.system.entities.User
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@EntityListeners(AuditingEntityListener::class)
data class ObjectStorage(
    @field:Id
    @field:GeneratedValue
    var id: Long? = null,

    @field:Lob
    @field:Column(nullable = false, unique = true, updatable = false)
    var path: String? = null
) {
    @field:CreatedDate
    var createdDate: LocalDateTime? = null

    @field:ManyToOne
    @field:CreatedBy
    var creator: User? = null
}