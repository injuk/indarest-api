package rest.mjis.indarest.domain.models

import java.time.LocalDateTime
import java.time.OffsetDateTime

data class ActionContext(
    val at: OffsetDateTime,
    val by: By,
) {
    companion object {
        fun from(at: LocalDateTime, creator: Long): ActionContext = ActionContext(
            at = at.atOffset(OffsetDateTime.now().offset),
            by = By(creator),
        )
    }

    data class By(
        val id: Long,
    )
}
