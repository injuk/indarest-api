package rest.mjis.indarest.domain.models

import java.time.OffsetDateTime

data class ActionContext(
    val at: OffsetDateTime,
    val by: UserInfo,
)
