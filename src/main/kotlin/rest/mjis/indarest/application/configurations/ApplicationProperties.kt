package rest.mjis.indarest.application.configurations

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "storage")
data class ApplicationProperties(
    val endpoint: String,
    val bucket: String,
    val path: StoragePath,
    val credential: StorageCredential,
) {
    data class StoragePath(
        val pins: String,
    )

    data class StorageCredential(
        val accessKey: String,
        val secretKey: String,
    )
}