package rest.mjis.indarest.domain.useCases

import rest.mjis.indarest.domain.UpdatePart
import java.time.OffsetDateTime

interface UpdatePin : UseCase<UpdatePin.Request, Unit> {
    override val name: String
        get() = UpdatePin::class.java.name

    data class Request(
        val id: Long,
        val name: UpdatePart<String?> = UpdatePart.empty(),
        val description: UpdatePart<String?> = UpdatePart.empty(),
    )
}