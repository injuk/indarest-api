package rest.mjis.indarest.application.controllers.dto

import rest.mjis.indarest.domain.models.ActionContext
import rest.mjis.indarest.domain.models.ImageResource
import rest.mjis.indarest.domain.models.Pin

data class PinDto(
    val id: String,
    val name: String?,
    val description: String?,
    val resource: ImageResource?,
    val thumbnail: ImageResource?,
    val created: ActionContext,
    val updated: Pin.Updated,
)
