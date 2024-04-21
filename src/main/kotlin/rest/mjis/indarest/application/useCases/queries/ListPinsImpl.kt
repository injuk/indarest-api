package rest.mjis.indarest.application.useCases.queries

import rest.mjis.indarest.application.core.annotations.Query
import rest.mjis.indarest.application.gateways.dataAccesses.PinsDataAccess
import rest.mjis.indarest.application.utils.CursorHelper
import rest.mjis.indarest.application.utils.IdConverter.decode
import rest.mjis.indarest.application.utils.IdConverter.encode
import rest.mjis.indarest.domain.ListResponses
import rest.mjis.indarest.domain.SearchCondition
import rest.mjis.indarest.domain.User
import rest.mjis.indarest.domain.models.Pin
import rest.mjis.indarest.domain.useCases.ListPins

@Query
class ListPinsImpl(
    private val pinsDataAccess: PinsDataAccess,
) : ListPins {
    companion object {
        private const val DEFAULT_PINS_LIMIT = 20
    }

    override suspend fun execute(user: User, request: ListPins.Request): ListResponses<Pin.Summary> {
        val condition = SearchCondition(
            size = request.size ?: DEFAULT_PINS_LIMIT,
            cursor = request.cursor?.decode(),
        )

        return CursorHelper.fetch(condition) {
            pinsDataAccess.findBy(it)
        }
            .build {
                it.id.encode()
            }
    }
}