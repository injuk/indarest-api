package rest.mjis.indarest.application.controllers.dto.request

data class CreatePin(
    val name: String?,
    val description: String?,
    val resourceUrl: String,
)