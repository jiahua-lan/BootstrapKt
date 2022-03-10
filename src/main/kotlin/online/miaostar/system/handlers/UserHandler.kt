package online.miaostar.system.handlers

import online.miaostar.system.entities.Role
import online.miaostar.system.entities.User
import online.miaostar.system.services.UserService
import online.miaostar.system.services.UserService.Companion.ROOT_ROLE
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
class UserHandler(
    private val userService: UserService
) {

    @PreAuthorize("hasRole('${ROOT_ROLE}')")
    @GetMapping("/users")
    fun users(
        probe: User,
        pageable: Pageable
    ): Page<User> = userService.users(probe, pageable)

    @PreAuthorize("hasRole('${ROOT_ROLE}')")
    @GetMapping("/user/{id}")
    fun user(@PathVariable("id") id: Long): User = userService.user(id)

    @PostMapping("/user")
    fun user(@RequestBody @Validated user: User): User = userService.user(user)

    @PreAuthorize("hasRole('${ROOT_ROLE}')")
    @PutMapping("/user/{id}")
    fun user(
        @PathVariable("id") id: Long,
        @RequestBody @Validated user: User
    ): User = userService.user(id, user)

    @GetMapping("/roles")
    fun roles(probe: Role): Page<Role> = userService.roles(probe)

}