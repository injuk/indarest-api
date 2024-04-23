package rest.mjis.indarest.application.useCases

import rest.mjis.indarest.application.configurations.ApplicationProperties

object MockApplicationPropertiesHolder {
    const val VALID_ENDPOINT = "http://localhost"
    const val VALID_BUCKET_NAME = "test-resources"

    fun getInstance() = ApplicationProperties(
        endpoint = VALID_ENDPOINT,
        bucket = VALID_BUCKET_NAME,
        path = ApplicationProperties.StoragePath("pins", "profiles"),
        credential = ApplicationProperties.StorageCredential(
            accessKey = "test-access-key",
            secretKey = "test-secret-key",
        )
    )
}