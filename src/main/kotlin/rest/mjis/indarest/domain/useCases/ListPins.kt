package rest.mjis.indarest.domain.useCases

import rest.mjis.indarest.domain.ListResponses
import rest.mjis.indarest.domain.models.Pin

interface ListPins : UseCase<ListPins.Request, ListResponses<Pin.Summary>> {
    override val name: String
        get() = ListPins::class.java.name

    data class Request(
        val size: Int,
        val cursor: String,
    )
}