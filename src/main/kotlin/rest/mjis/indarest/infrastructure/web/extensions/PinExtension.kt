package rest.mjis.indarest.infrastructure.web.extensions

import rest.mjis.indarest.application.controllers.dto.PinDto
import rest.mjis.indarest.application.utils.IdConverter.encode
import rest.mjis.indarest.domain.models.ActionContext
import rest.mjis.indarest.domain.models.ImageResource
import rest.mjis.indarest.domain.models.Pin

object PinExtension {
    internal fun Pin.toPublic(): PinDto = PinDto(
        id = id.encode(),
        name = name,
        description = description,
        resource = resource?.let { ImageResource(it.url) },
        thumbnail = thumbnail?.let { ImageResource(it.url) },
        created = ActionContext(
            at = created.at,
            by = ActionContext.By(
                id = created.by.id,
            )
        ),
        updated = Pin.Updated(updated.at)
    )
}