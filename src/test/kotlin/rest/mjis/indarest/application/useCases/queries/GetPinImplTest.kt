package rest.mjis.indarest.application.useCases.queries

import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import rest.mjis.indarest.application.gateways.dataAccesses.PinsDataAccess
import rest.mjis.indarest.application.utils.IdConverter.encode
import rest.mjis.indarest.domain.AffectedRows
import rest.mjis.indarest.domain.SearchCondition
import rest.mjis.indarest.domain.User
import rest.mjis.indarest.domain.models.ActionContext
import rest.mjis.indarest.domain.models.Pin
import rest.mjis.indarest.domain.models.PinResource
import rest.mjis.indarest.domain.useCases.CreatePin
import rest.mjis.indarest.domain.useCases.GetPin
import rest.mjis.indarest.domain.useCases.UpdatePin
import java.time.LocalDateTime
import java.time.OffsetDateTime

class GetPinImplTest {
    private val dateTime = LocalDateTime.now()

    private fun createPin(id: Int): Pin {
        val pinName = "pin(${id})"
        return Pin(
            id = id.toLong(),
            name = pinName,
            description = "$pinName 설명",
            resource = PinResource(
                url = "http://localhost:9000/indarest-resources/pins/$pinName.png",
            ),
            created = ActionContext.from(dateTime, 1L),
            updated = Pin.Updated(dateTime.atOffset(OffsetDateTime.now().offset)),
        )
    }

    private val pinsDataAccess: PinsDataAccess = object : PinsDataAccess {
        private val repository: List<Pin> = (1..100).reversed().map {
            return@map createPin(it)
        }

        override suspend fun insert(user: User, data: CreatePin.Request): Long {
            TODO("Not yet implemented")
        }

        override suspend fun findBy(conditions: SearchCondition<Long>): List<Pin.Summary> {
            TODO("Not yet implemented")
        }

        override suspend fun findOne(id: Long): Pin? {
            return repository.find { it.id == id }
        }

        override suspend fun update(id: Long, data: UpdatePin.Request): AffectedRows {
            TODO("Not yet implemented")
        }

        override suspend fun delete(id: Long): AffectedRows {
            TODO("Not yet implemented")
        }
    }

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
        assertThat(result).isEqualTo(createPin(pinId))
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