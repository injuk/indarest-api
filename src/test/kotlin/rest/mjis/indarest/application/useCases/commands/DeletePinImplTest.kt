package rest.mjis.indarest.application.useCases.commands

import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import rest.mjis.indarest.application.configurations.ApplicationProperties
import rest.mjis.indarest.application.gateways.clients.StorageClient
import rest.mjis.indarest.application.gateways.dataAccesses.PinsDataAccess
import rest.mjis.indarest.application.useCases.MockPinDataAccessImpl
import rest.mjis.indarest.application.useCases.MockStorageClassImpl
import rest.mjis.indarest.application.utils.IdConverter.encode
import rest.mjis.indarest.domain.User
import rest.mjis.indarest.domain.useCases.DeletePin

class DeletePinImplTest {
    companion object {
        private const val VALID_ENDPOINT = "http://localhost"
        private const val VALID_BUCKET_NAME = "test-resources"
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
    fun `핀 수정시 요청한 핀이 존재하는 경우 핀을 삭제할 수 있다`(): Unit = runBlocking {
        // given
        val user = User.create()
        val request = DeletePin.Request(
            id = MockPinDataAccessImpl.VALID_PIN_ID,
        )

        // when
        // then
        assertDoesNotThrow {
            DeletePinImpl(
                properties = properties,
                pinsDataAccess = pinsDataAccess,
                storageClient = storageClient,
            )
                .execute(user, request)
        }
    }

    @Test
    fun `핀 삭제 요청시 요청한 핀이 존재하지 않는 경우 예외가 발생한다`(): Unit = runBlocking {
        // given
        val user = User.create()
        val request = DeletePin.Request(
            id = MockPinDataAccessImpl.INVALID_PIN_ID,
        )

        // when
        val result = assertThrows<RuntimeException> {
            DeletePinImpl(
                properties = properties,
                pinsDataAccess = pinsDataAccess,
                storageClient = storageClient,
            )
                .execute(user, request)
        }

        // then
        assertThat(result.message).isEqualTo("there is no pin(${request.id.encode()})")
    }

    @Test
    fun `정상적인 핀 삭제 요청이 DB에 영향을 주지 못한 경우 예외가 발생한다`(): Unit = runBlocking {
        // given
        val user = User.create()
        val request = DeletePin.Request(
            id = MockPinDataAccessImpl.ID_FOR_NOTHING_AFFECTED,
        )

        // when
        val result = assertThrows<RuntimeException> {
            DeletePinImpl(
                properties = properties,
                pinsDataAccess = pinsDataAccess,
                storageClient = storageClient,
            )
                .execute(user, request)
        }

        // then
        assertThat(result.message).isEqualTo("nothing affected from update useCase")
    }
}