package rest.mjis.indarest.application.useCases.queries

import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import rest.mjis.indarest.application.gateways.dataAccesses.PinsDataAccess
import rest.mjis.indarest.application.useCases.MockPinDataAccessImpl
import rest.mjis.indarest.application.utils.IdConverter.encode
import rest.mjis.indarest.domain.User
import rest.mjis.indarest.domain.useCases.GetPin

class GetPinImplTest {
    private val pinsDataAccess: PinsDataAccess = MockPinDataAccessImpl()

    @Test
    fun `핀 상세 조회시 요청한 핀이 존재하는 경우 해당 핀의 정보를 확인할 수 있다`(): Unit = runBlocking {
        // given
        val pinId = 1
        val user = User.create()
        val request = GetPin.Request(id = pinId.toLong())

        // when
        val result = GetPinImpl(
            pinsDataAccess = pinsDataAccess,
        ).execute(user, request)
        println(result)

        // then
        assertThat(result).isEqualTo(MockPinDataAccessImpl.createPin(pinId))
    }

    @Test
    fun `핀 상세 조회시 요청한 핀이 존재하지 않는 경우 예외가 발생한다`(): Unit = runBlocking {
        // given
        val invalidPinId = 999
        val user = User.create()
        val request = GetPin.Request(id = invalidPinId.toLong())

        // when
        val result = assertThrows<RuntimeException> {
            GetPinImpl(
                pinsDataAccess = pinsDataAccess,
            ).execute(user, request)
        }

        // then
        assertThat(result.message).isEqualTo("there is no pin(${request.id.encode()})")
    }
}