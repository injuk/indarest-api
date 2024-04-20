package rest.mjis.indarest.domain.useCases

import rest.mjis.indarest.domain.models.Pin

interface GetPin : UseCase<GetPin.Request, Pin> {
    override val name: String
        get() = GetPin::class.java.name

    data class Request(
        val id: Long,
    )
}