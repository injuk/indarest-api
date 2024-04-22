package rest.mjis.indarest.infrastructure.storage

import io.minio.MinioAsyncClient
import io.minio.MinioClient
import io.minio.RemoveObjectsArgs
import io.minio.StatObjectArgs
import io.minio.errors.ErrorResponseException
import io.minio.messages.DeleteObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.future.await
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.withContext
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import reactor.kotlin.core.publisher.toFlux
import rest.mjis.indarest.application.configurations.ApplicationProperties
import rest.mjis.indarest.application.gateways.clients.StorageClient
import java.util.concurrent.CompletionException

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
            val response = client.statObject(
                StatObjectArgs.builder()
                    .bucket(properties.bucket)
                    .`object`(key)
                    .build()
            )
                .await()

            logger.info("statObject returns object($response)")

            return@withContext response.`object`() == key
        } catch (e: CompletionException) {
            val minioException = e.cause?.cause

            if (minioException !is ErrorResponseException) {
                throw e
            }

            if (minioException.errorResponse().code() != "NoSuchKey") {
                logger.error("minio returns error${minioException.errorResponse()}")
                throw e
            }

            logger.error("there is no object($key)")

            return@withContext false
        } catch (e: Exception) {
            logger.error("cannot handle storage client exception: ${e.message}")
            throw e
        }
    }

    override suspend fun deleteAll(keys: List<String>): Unit = withContext(Dispatchers.IO) {
        logger.info("try to delete objects($keys)")
        val response = client.removeObjects(
            RemoveObjectsArgs.builder()
                .bucket(properties.bucket)
                .objects(
                    keys.map { DeleteObject(it) }
                )
                .build()
        ).toFlux().collectList().awaitFirst()
        logger.info("removeObjects command returns response: $response")
    }
}