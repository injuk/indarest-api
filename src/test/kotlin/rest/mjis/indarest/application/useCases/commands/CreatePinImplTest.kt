package rest.mjis.indarest.application.useCases.commands

import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import rest.mjis.indarest.application.configurations.ApplicationProperties
import rest.mjis.indarest.application.gateways.clients.StorageClient
import rest.mjis.indarest.application.gateways.dataAccesses.PinsDataAccess
import rest.mjis.indarest.domain.AffectedRows
import rest.mjis.indarest.domain.SearchCondition
import rest.mjis.indarest.domain.User
import rest.mjis.indarest.domain.models.Pin
import rest.mjis.indarest.domain.useCases.CreatePin
import rest.mjis.indarest.domain.useCases.UpdatePin

@ActiveProfiles("local")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CreatePinImplTest {
    companion object {
        private const val VALID_ENDPOINT_PREFIX = "http://localhost:9000/indarest-resources"
        private const val VALID_OBJECT_KEY = "pins/valid-object-key.png"
        private const val VALID_RESOURCE_URL = "$VALID_ENDPOINT_PREFIX/$VALID_OBJECT_KEY"
        private const val CREATED_PIN_ID = 1L
    }

    @Autowired
    lateinit var properties: ApplicationProperties

    private val storageClient: StorageClient = object : StorageClient {
        override suspend fun isExists(key: String): Boolean {
            return key == VALID_OBJECT_KEY
        }
    }

    private val pinsDataAccess: PinsDataAccess = object : PinsDataAccess {
        override suspend fun insert(user: User, data: CreatePin.Request): Long {
            return CREATED_PIN_ID
        }

        override suspend fun findBy(conditions: SearchCondition<Long>): List<Pin.Summary> {
            TODO("Not yet implemented")
        }

        override suspend fun findOne(id: Long): Pin {
            TODO("Not yet implemented")
        }

        override suspend fun update(id: Long, data: UpdatePin.Request): AffectedRows {
            TODO("Not yet implemented")
        }

        override suspend fun delete(id: Long): AffectedRows {
            TODO("Not yet implemented")
        }
    }

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
        assertThat(result.id).isEqualTo(CREATED_PIN_ID)
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