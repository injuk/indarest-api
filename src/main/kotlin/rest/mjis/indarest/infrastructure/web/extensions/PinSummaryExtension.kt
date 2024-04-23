package rest.mjis.indarest.infrastructure.web.extensions

import rest.mjis.indarest.application.controllers.dto.PinSummaryDto
import rest.mjis.indarest.application.utils.IdConverter.encode
import rest.mjis.indarest.domain.models.ActionContext
import rest.mjis.indarest.domain.models.ImageResource
import rest.mjis.indarest.domain.models.Pin

object PinSummaryExtension {
    internal fun Pin.Summary.toPublic(): PinSummaryDto = PinSummaryDto(
        id = id.encode(),
        name = name,
        resource = resource?.let { ImageResource(it.url) },
        thumbnail = thumbnail?.let { ImageResource(it.url) },
        created = ActionContext(
            at = created.at,
            by = ActionContext.By(
                id = created.by.id,
            )
        ),
    )
}