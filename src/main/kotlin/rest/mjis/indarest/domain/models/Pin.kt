package rest.mjis.indarest.domain.models

import java.time.OffsetDateTime

data class Pin(
    val id: Long,
    val name: String?,
    val description: String?,
    val resource: PinResource,
    val created: ActionContext,
    val updated: Updated,
) {
    data class Summary(
        val id: Long,
        val name: String?,
        val resource: PinResource,
        val created: ActionContext,
    )

    data class Updated(
        val at: OffsetDateTime,
    )
}
