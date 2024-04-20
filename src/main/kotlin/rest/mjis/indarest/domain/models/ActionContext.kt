package rest.mjis.indarest.domain.models

import java.time.OffsetDateTime

data class ActionContext(
    val at: OffsetDateTime,
    val by: By,
) {
    data class By(
        val id: Long,
    )
}
