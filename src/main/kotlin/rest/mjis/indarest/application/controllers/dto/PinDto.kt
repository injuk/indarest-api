package rest.mjis.indarest.application.controllers.dto

import rest.mjis.indarest.domain.models.ActionContext
import rest.mjis.indarest.domain.models.ImageResource
import rest.mjis.indarest.domain.models.Pin

data class PinDto(
    val id: String,
    val name: String? = null,
    val description: String? = null,
    val resource: ImageResource? = null,
    val thumbnail: ImageResource? = null,
    val created: ActionContext,
    val updated: Pin.Updated,
)
