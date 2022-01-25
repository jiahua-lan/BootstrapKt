package online.miaostar.configuration

import com.fasterxml.jackson.databind.Module
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Configurations {
    @Bean
    fun hibernate5(): Module = Hibernate5Module()

    @Bean
    fun jdk8(): Module = Jdk8Module()

    @Bean
    fun kotlin(): Module = KotlinModule.Builder()
        .enable(KotlinFeature.NullToEmptyCollection)
        .enable(KotlinFeature.NullToEmptyMap)
        .build()
}
