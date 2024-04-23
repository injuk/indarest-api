package rest.mjis.indarest.application.useCases.commands

import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import rest.mjis.indarest.application.configurations.ApplicationProperties
import rest.mjis.indarest.application.gateways.clients.StorageClient
import rest.mjis.indarest.application.useCases.MockApplicationPropertiesHolder
import rest.mjis.indarest.application.useCases.MockStorageClassImpl
import rest.mjis.indarest.domain.User
import rest.mjis.indarest.domain.models.ResourceType
import rest.mjis.indarest.domain.useCases.CreateUploadUrl

class CreateUploadUrlImplTest {
    private val properties: ApplicationProperties = MockApplicationPropertiesHolder.getInstance()

    private val storageClient: StorageClient = MockStorageClassImpl()

    @Test
    fun `핀에 대한 업로드 URL 생성 요청시 적절한 prefix를 갖는 URL이 반환된다`(): Unit = runBlocking {
        // given
        val user = User.create()
        val request = CreateUploadUrl.Request(
            type = ResourceType.PIN,
            fileName = "my-file.jpeg",
        )

        // when
        val result = CreateUploadUrlImpl(
            properties = properties,
            storageClient = storageClient,
        ).execute(user, request)

        // then
        assertThat(result.url.startsWith("pins")).isTrue()
        assertThat(result.url.endsWith(request.fileName)).isTrue()
    }

    @Test
    fun `프로필에 대한 업로드 URL 생성 요청시 적절한 prefix를 갖는 URL이 반환된다`(): Unit = runBlocking {
        // given
        val user = User.create()
        val request = CreateUploadUrl.Request(
            type = ResourceType.PROFILE,
            fileName = "my-file.jpeg",
        )

        // when
        val result = CreateUploadUrlImpl(
            properties = properties,
            storageClient = storageClient,
        ).execute(user, request)

        // then
        assertThat(result.url.startsWith("profiles")).isTrue()
        assertThat(result.url.endsWith(request.fileName)).isTrue()
    }
}