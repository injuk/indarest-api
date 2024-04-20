package rest.mjis.indarest.infrastructure.storage

import io.minio.MinioAsyncClient
import io.minio.MinioClient
import io.minio.StatObjectArgs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import rest.mjis.indarest.application.configurations.ApplicationProperties
import rest.mjis.indarest.application.gateways.clients.StorageClient

@Component
class MinioClient(
    private val properties: ApplicationProperties,
) : StorageClient {
    private val logger = LoggerFactory.getLogger(MinioClient::class.java)
    private val client: MinioAsyncClient by lazy {
        MinioAsyncClient.builder()
            .endpoint(properties.endpoint)
            .credentials(properties.credential.accessKey, properties.credential.secretKey)
            .build()
    }
    override suspend fun isExists(key: String): Boolean = withContext(Dispatchers.IO) {
        try {
            val response = client.statObject(StatObjectArgs.builder()
                .bucket(properties.bucket)
                .`object`(key)
                .build()).get()

            return@withContext response.`object`() == key
        } catch (e: Exception) {
            logger.error("cannot stat object: ${e.message}")

            return@withContext false
        }
    }
}