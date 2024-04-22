package rest.mjis.indarest.domain.models

import java.time.OffsetDateTime

data class Pin(
    val id: Long,
    val name: String?,
    val description: String?,
    val resource: ImageResource?,
    val thumbnail: ImageResource?,
    val created: ActionContext,
    val updated: Updated,
) {
    fun getImageResourceUrls(): List<String> = listOfNotNull(resource?.url, thumbnail?.url)

    data class Summary(
        val id: Long,
        val name: String?,
        val resource: ImageResource?,
        val thumbnail: ImageResource?,
        val created: ActionContext,
    )

    data class Updated(
        val at: OffsetDateTime,
    )
}
