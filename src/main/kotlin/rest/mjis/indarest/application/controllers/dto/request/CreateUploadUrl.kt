package rest.mjis.indarest.application.controllers.dto.request

data class CreateUploadUrl(
    val type: String,
    val fileName: String,
)