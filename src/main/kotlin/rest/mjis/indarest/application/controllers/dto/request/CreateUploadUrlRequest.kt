package rest.mjis.indarest.application.controllers.dto.request

data class CreateUploadUrlRequest(
    val type: String,
    val fileName: String,
)