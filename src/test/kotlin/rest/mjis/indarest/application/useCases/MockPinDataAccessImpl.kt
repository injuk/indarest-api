package rest.mjis.indarest.application.useCases

import rest.mjis.indarest.application.gateways.dataAccesses.PinsDataAccess
import rest.mjis.indarest.domain.AffectedRows
import rest.mjis.indarest.domain.SearchCondition
import rest.mjis.indarest.domain.User
import rest.mjis.indarest.domain.models.ActionContext
import rest.mjis.indarest.domain.models.ImageResource
import rest.mjis.indarest.domain.models.Pin
import rest.mjis.indarest.domain.useCases.CreatePin
import rest.mjis.indarest.domain.useCases.UpdatePin
import java.time.LocalDateTime
import java.time.OffsetDateTime

class MockPinDataAccessImpl : PinsDataAccess {
    private val pinRepository: List<Pin> = (1..100).reversed().map {
        return@map createPin(it)
    }
    private val pinSummaryRepository: List<Pin.Summary> = (1..100).reversed().map {
        return@map createPinSummary(it)
    }

    companion object {
        const val VALID_PIN_ID = 1L
        const val INVALID_PIN_ID = -1L
        const val ID_FOR_NOTHING_AFFECTED = 10L
        private val dateTime = LocalDateTime.now()

        fun createPin(id: Int): Pin {
            val pinName = "pin(${id})"
            return Pin(
                id = id.toLong(),
                name = pinName,
                description = "$pinName 설명",
                resource = ImageResource(
                    url = "http://localhost:9000/indarest-resources/pins/$pinName.png",
                ),
                thumbnail = null,
                created = ActionContext.from(dateTime, 1L),
                updated = Pin.Updated(dateTime.atOffset(OffsetDateTime.now().offset)),
            )
        }

        fun createPinSummary(id: Int): Pin.Summary {
            val pinName = "pin(${id})"
            return Pin.Summary(
                id = id.toLong(),
                name = pinName,
                resource = ImageResource(
                    url = "http://localhost:9000/indarest-resources/pins/$pinName.png",
                ),
                thumbnail = null,
                created = ActionContext.from(dateTime, 1L),
            )
        }
    }

    override suspend fun insert(user: User, data: CreatePin.Request): Long {
        return VALID_PIN_ID
    }

    override suspend fun findBy(conditions: SearchCondition<Long>): List<Pin.Summary> {
        val (size, cursor) = conditions

        val startIdx = if (cursor != null) {
            pinSummaryRepository.indexOfFirst { it.id == cursor }
        } else {
            0
        }
        val endIdx = (startIdx + size).let {
            if (it > pinSummaryRepository.size) {
                pinSummaryRepository.size
            } else {
                it
            }
        }

        return pinSummaryRepository.subList(startIdx, endIdx)
    }

    override suspend fun findOne(id: Long): Pin? {
        return pinRepository.find { it.id == id }
    }

    override suspend fun update(id: Long, data: UpdatePin.Request): AffectedRows {
        return if (id == ID_FOR_NOTHING_AFFECTED) {
            return AffectedRows(0)
        } else {
            return AffectedRows(1)
        }
    }

    override suspend fun delete(id: Long): AffectedRows {
        TODO("Not yet implemented")
    }
}