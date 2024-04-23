package rest.mjis.indarest.application.controllers.dto.request

data class CreatePinRequest(
    val name: String? = null,
    val description: String? = null,
    val resourceUrl: String,
)