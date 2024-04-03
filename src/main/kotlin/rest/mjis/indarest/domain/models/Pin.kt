package rest.mjis.indarest.domain.models

data class Pin(
    val id: Long,
    val name: String?,
    val description: String?,
    val resource: PinResource,
    val created: ActionContext,
) {
    data class Summary(
        val id: Long,
        val name: String?,
        val resource: PinResource,
        val created: ActionContext,
    )
}
