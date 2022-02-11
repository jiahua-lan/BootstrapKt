package online.miaostar.organization.event

import org.springframework.context.ApplicationEvent

data class OrganizationTypeEntryEvent(val code: String) : ApplicationEvent(code) {
    override fun getSource(): String {
        return super.getSource() as String
    }
}