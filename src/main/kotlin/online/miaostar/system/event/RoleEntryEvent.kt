package online.miaostar.system.event

import org.springframework.context.ApplicationEvent

data class RoleEntryEvent(
    val code: String
) : ApplicationEvent(code) {
    override fun getSource(): String {
        return super.getSource() as String
    }
}