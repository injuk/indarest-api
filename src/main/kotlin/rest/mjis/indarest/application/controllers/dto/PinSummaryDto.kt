package rest.mjis.indarest.application.controllers.dto

import rest.mjis.indarest.domain.models.ActionContext
import rest.mjis.indarest.domain.models.ImageResource

data class PinSummaryDto(
    val id: String,
    val name: String?,
    val resource: ImageResource?,
    val thumbnail: ImageResource?,
    val created: ActionContext,
)