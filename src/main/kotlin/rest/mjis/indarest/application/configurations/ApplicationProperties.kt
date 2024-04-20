package rest.mjis.indarest.application.configurations

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "storage")
data class ApplicationProperties(
    val endpoint: StorageEndpoint,
) {
    data class StorageEndpoint(
        val pins: String,
    )
}