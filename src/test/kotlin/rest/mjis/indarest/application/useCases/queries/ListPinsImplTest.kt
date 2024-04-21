package rest.mjis.indarest.application.useCases.queries

import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import rest.mjis.indarest.application.gateways.dataAccesses.PinsDataAccess
import rest.mjis.indarest.domain.AffectedRows
import rest.mjis.indarest.domain.SearchCondition
import rest.mjis.indarest.domain.User
import rest.mjis.indarest.domain.models.ActionContext
import rest.mjis.indarest.domain.models.Pin
import rest.mjis.indarest.domain.models.PinResource
import rest.mjis.indarest.domain.useCases.CreatePin
import rest.mjis.indarest.domain.useCases.ListPins
import rest.mjis.indarest.domain.useCases.UpdatePin
import java.time.LocalDateTime

class ListPinsImplTest {
    private val dateTime = LocalDateTime.now()

    private fun createPinSummary(id: Int): Pin.Summary {
        val pinName = "pin(${id})"
        return Pin.Summary(
            id = id.toLong(),
            name = pinName,
            resource = PinResource(
                url = "http://localhost:9000/indarest-resources/pins/$pinName.png",
            ),
            created = ActionContext.from(dateTime, 1L),
        )
    }

    private val pinsDataAccess: PinsDataAccess = object : PinsDataAccess {
        private val repository: List<Pin.Summary> = (1..100).reversed().map {
            return@map createPinSummary(it)
        }

        override suspend fun insert(user: User, data: CreatePin.Request): Long {
            TODO("Not yet implemented")
        }

        override suspend fun findBy(conditions: SearchCondition<Long>): List<Pin.Summary> {
            val (size, cursor) = conditions

            val startIdx = if (cursor != null) {
                repository.indexOfFirst { it.id == cursor }
            } else {
                0
            }
            val endIdx = (startIdx + size).let {
                if (it > repository.size) {
                    repository.size
                } else {
                    it
                }
            }

            return repository.subList(startIdx, endIdx)
        }

        override suspend fun findOne(id: Long): Pin? {
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
    fun `핀 목록 조회시 다음 목록이 있는 경우 null이 아닌 커서가 반환된다`(): Unit = runBlocking {
        // given
        val user = User.create()
        val request = ListPins.Request(
            size = 1,
            cursor = null,
        )
        val expectedNextCursor = "BZEQrq6R4Gk0EYWOV7Kjpl5y" // 99

        // when
        val (results, cursor) = ListPinsImpl(
            pinsDataAccess = pinsDataAccess,
        ).execute(user, request)

        // then
        assertThat(results).isEqualTo(
            listOf(
                createPinSummary(id = 100)
            )
        )
        assertThat(cursor).isEqualTo(expectedNextCursor)
    }

    @Test
    fun `핀 목록 조회시 size가 0으로 전달된 경우 예외가 발생한다`(): Unit = runBlocking {
        // given
        val user = User.create()
        val request = ListPins.Request(
            size = 0,
            cursor = null,
        )

        // when
        val result = assertThrows<RuntimeException> {
            ListPinsImpl(
                pinsDataAccess = pinsDataAccess,
            ).execute(user, request)
        }

        // then
        assertThat(result.message).isEqualTo("size cannot be 0")
    }

    @Test
    fun `핀 목록 조회시 size가 null로 전달된 경우 size는 기본 값인 20으로 적용된다`(): Unit = runBlocking {
        // given
        val user = User.create()
        val request = ListPins.Request(
            size = null,
            cursor = null,
        )
        val expectedSize = 20
        val expectedNextCursor = "6z0BbJ3ZVYX5BLMw2q4en8oj" // 80

        // when
        val (results, cursor) = ListPinsImpl(
            pinsDataAccess = pinsDataAccess,
        ).execute(user, request)

        // then
        assertThat(results.size).isEqualTo(expectedSize)
        assertThat(results).isEqualTo(
            (81..100).reversed().map { createPinSummary(id = it) }
        )
        assertThat(cursor).isEqualTo(expectedNextCursor)
    }
}