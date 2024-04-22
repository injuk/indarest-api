package rest.mjis.indarest.application.useCases.commands

import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import rest.mjis.indarest.application.gateways.dataAccesses.PinsDataAccess
import rest.mjis.indarest.application.useCases.MockPinDataAccessImpl
import rest.mjis.indarest.application.utils.IdConverter.encode
import rest.mjis.indarest.domain.UpdatePart
import rest.mjis.indarest.domain.User
import rest.mjis.indarest.domain.useCases.UpdatePin

class UpdatePinImplTest {
    private val pinsDataAccess: PinsDataAccess = MockPinDataAccessImpl()

    @Test
    fun `핀 수정시 요청한 핀이 존재하는 경우 핀을 수정할 수 있다`(): Unit = runBlocking {
        // given
        val user = User.create()
        val request = UpdatePin.Request(
            id = MockPinDataAccessImpl.VALID_PIN_ID,
            name = UpdatePart.from(null),
            description = UpdatePart.from(null),
        )

        // when
        // then
        assertDoesNotThrow {
            UpdatePinImpl(pinsDataAccess)
                .execute(user, request)
        }
    }

    @Test
    fun `핀 수정 요청시 요청 데이터가 모두 정의되지 않은 경우 예외가 발생한다`(): Unit = runBlocking {
        // given
        val user = User.create()
        val request = UpdatePin.Request(
            id = 1L,
            name = UpdatePart.empty(),
            description = UpdatePart.empty(),
        )

        // when
        val result = assertThrows<RuntimeException> {
            UpdatePinImpl(pinsDataAccess)
                .execute(user, request)
        }

        // then
        assertThat(result.message).isEqualTo("at least one property must be provided")
    }

    @Test
    fun `핀 수정 요청시 요청한 핀이 존재하지 않는 경우 예외가 발생한다`(): Unit = runBlocking {
        // given
        val user = User.create()
        val request = UpdatePin.Request(
            id = MockPinDataAccessImpl.INVALID_PIN_ID,
            name = UpdatePart.from(null),
            description = UpdatePart.from(null),
        )

        // when
        val result = assertThrows<RuntimeException> {
            UpdatePinImpl(
                pinsDataAccess = pinsDataAccess,
            ).execute(user, request)
        }

        // then
        assertThat(result.message).isEqualTo("there is no pin(${request.id.encode()})")
    }

    @Test
    fun `정상적인 핀 수정 요청이 DB에 영향을 주지 못한 경우 예외가 발생한다`(): Unit = runBlocking {
        // given
        val user = User.create()
        val request = UpdatePin.Request(
            id = MockPinDataAccessImpl.ID_FOR_NOTHING_AFFECTED,
            name = UpdatePart.from(null),
            description = UpdatePart.from(null),
        )

        // when
        val result = assertThrows<RuntimeException> {
            UpdatePinImpl(
                pinsDataAccess = pinsDataAccess,
            ).execute(user, request)
        }

        // then
        assertThat(result.message).isEqualTo("nothing affected from update useCase")
    }
}