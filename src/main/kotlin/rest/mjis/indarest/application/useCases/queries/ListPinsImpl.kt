package rest.mjis.indarest.application.useCases.queries

import rest.mjis.indarest.application.core.annotations.Query
import rest.mjis.indarest.domain.ListResponses
import rest.mjis.indarest.domain.User
import rest.mjis.indarest.domain.models.Pin
import rest.mjis.indarest.domain.useCases.ListPins

@Query
class ListPinsImpl : ListPins {
    override fun execute(user: User, data: ListPins.Request): ListResponses<Pin.Summary> {
        TODO("Not yet implemented")
    }
}