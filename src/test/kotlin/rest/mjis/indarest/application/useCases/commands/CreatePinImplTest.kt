package rest.mjis.indarest.application.useCases.commands

import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import rest.mjis.indarest.application.configurations.ApplicationProperties
import rest.mjis.indarest.application.gateways.clients.StorageClient
import rest.mjis.indarest.application.gateways.dataAccesses.PinsDataAccess
import rest.mjis.indarest.application.useCases.MockPinDataAccessImpl
import rest.mjis.indarest.application.useCases.MockStorageClassImpl
import rest.mjis.indarest.domain.User
import rest.mjis.indarest.domain.useCases.CreatePin

class CreatePinImplTest {
    companion object {
        private const val VALID_ENDPOINT = "http://localhost"
        private const val VALID_BUCKET_NAME = "test-resources"
        private const val VALID_ENDPOINT_PREFIX = "$VALID_ENDPOINT/$VALID_BUCKET_NAME"
        private const val VALID_RESOURCE_URL = "$VALID_ENDPOINT_PREFIX/${MockStorageClassImpl.VALID_OBJECT_KEY}"
    }

    val properties: ApplicationProperties = ApplicationProperties(
        endpoint = VALID_ENDPOINT,
        bucket = VALID_BUCKET_NAME,
        path = ApplicationProperties.StoragePath("pins"),
        credential = ApplicationProperties.StorageCredential(
            accessKey = "test-access-key",
            secretKey = "test-secret-key",
        )
    )

    private val storageClient: StorageClient = MockStorageClassImpl()

    private val pinsDataAccess: PinsDataAccess = MockPinDataAccessImpl()

    @Test
    fun `핀 생성시 Long 형태의 id가 반환된다`(): Unit = runBlocking {
        // given
        val user = User.create()
        val request = CreatePin.Request(
            name = "테스트용 핀",
            description = "테스트용 핀입니다.",
            resourceUrl = VALID_RESOURCE_URL,
        )

        // when
        val result = CreatePinImpl(
            properties = properties,
            storageClient = storageClient,
            pinsDataAccess = pinsDataAccess,
        ).execute(user, request)

        // then
        assertThat(result.id).isEqualTo(MockPinDataAccessImpl.VALID_PIN_ID)
    }

    @Test
    fun `resourceUrl이 지정된 오브젝트 스토리지가 아닌 경우 예외가 발생한다`(): Unit = runBlocking {
        // given
        val user = User.create()
        val request = CreatePin.Request(
            name = "테스트용 핀",
            description = "테스트용 핀입니다.",
            resourceUrl = "https://my-s3-bucket.com/hello.png",
        )

        // when
        val result = assertThrows<IllegalArgumentException> {
            CreatePinImpl(
                properties = properties,
                storageClient = storageClient,
                pinsDataAccess = pinsDataAccess,
            ).execute(user, request)
        }

        // then
        assertThat(result.message).isEqualTo("resource url has invalid storage endpoint")
    }

    @Test
    fun `존재하지 않는 오브젝트를 지정하여 핀을 생성할 경우 예외가 발생한다`(): Unit = runBlocking {
        // given
        val invalidObjectKey = "pins/invalid-object.png"
        val user = User.create()
        val request = CreatePin.Request(
            name = "테스트용 핀",
            description = "테스트용 핀입니다.",
            resourceUrl = "$VALID_ENDPOINT_PREFIX/$invalidObjectKey",
        )

        // when
        val result = assertThrows<RuntimeException> {
            CreatePinImpl(
                properties = properties,
                storageClient = storageClient,
                pinsDataAccess = pinsDataAccess,
            ).execute(user, request)
        }

        // then
        assertThat(result.message).isEqualTo("resource($invalidObjectKey) not found")
    }
}