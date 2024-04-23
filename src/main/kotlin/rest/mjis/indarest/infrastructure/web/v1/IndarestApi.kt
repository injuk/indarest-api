package rest.mjis.indarest.infrastructure.web.v1

import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import rest.mjis.indarest.application.configurations.ApplicationProperties
import rest.mjis.indarest.application.controllers.IndarestController
import rest.mjis.indarest.application.controllers.dto.PinDto
import rest.mjis.indarest.application.controllers.dto.PinSummaryDto
import rest.mjis.indarest.application.controllers.dto.request.CreatePinRequest
import rest.mjis.indarest.application.controllers.dto.request.CreateUploadUrlRequest
import rest.mjis.indarest.application.utils.IdConverter.decode
import rest.mjis.indarest.application.utils.IdConverter.encode
import rest.mjis.indarest.domain.ListResponses
import rest.mjis.indarest.domain.UpdatePart
import rest.mjis.indarest.domain.User
import rest.mjis.indarest.domain.useCases.*
import rest.mjis.indarest.infrastructure.web.extensions.PinExtension.toPublic
import rest.mjis.indarest.infrastructure.web.extensions.PinSummaryExtension.toPublic
import java.net.URI

@Validated
@RequestMapping("/api/v1")
@RestController
class IndarestApi(
    private val properties: ApplicationProperties,

    private val createPinUseCase: CreatePin,
    private val listPinsUseCase: ListPins,
    private val getPinUseCase: GetPin,
    private val updatePinUseCase: UpdatePin,
    private val deletePinUseCase: DeletePin,
    private val createUploadUrlUseCase: CreateUploadUrl,
) : IndarestController {
    companion object {
        private fun createSystemUser(): User = User.create()
    }

    @RequestMapping(
        method = [RequestMethod.POST],
        value = ["/pins"],
        produces = ["application/json"]
    )
    override suspend fun createPin(
        @RequestBody request: CreatePinRequest,
    ): ResponseEntity<Unit> {
        val (pinId) = createSystemUser()
            .invoke(createPinUseCase)
            .with(
                CreatePin.Request(
                    name = request.name,
                    description = request.description,
                    resourceUrl = request.resourceUrl,
                )
            )
            .execute()

        val location = "/${properties.path.pins}/${pinId.encode()}"

        return ResponseEntity.created(URI.create(location)).build()
    }

    @RequestMapping(
        method = [RequestMethod.GET],
        value = ["/pins"],
        produces = ["application/json"]
    )
    override suspend fun listPins(
        @RequestParam(value = "size") size: Int?,
        @RequestParam(value = "cursor") cursor: String?,
    ): ResponseEntity<ListResponses<PinSummaryDto>> {
        val (results, nextCursor) = createSystemUser()
            .invoke(listPinsUseCase)
            .with(
                ListPins.Request(
                    size = size,
                    cursor = cursor,
                )
            )
            .execute()

        return ResponseEntity.ok(
            ListResponses(
                results = results.map { it.toPublic() },
                cursor = nextCursor,
            )
        )
    }

    @RequestMapping(
        method = [RequestMethod.GET],
        value = ["/pins/{id}"],
        produces = ["application/json"]
    )
    override suspend fun getPin(
        @PathVariable("id") id: String,
    ): ResponseEntity<PinDto> {
        val result = createSystemUser()
            .invoke(getPinUseCase)
            .with(
                GetPin.Request(id = id.decode())
            )
            .execute()

        return ResponseEntity.ok(result.toPublic())
    }

    @RequestMapping(
        method = [RequestMethod.PATCH],
        value = ["/pins/{id}"],
        produces = ["application/json"]
    )
    override suspend fun updatePin(
        @PathVariable("id") id: String,
        @RequestBody request: Map<String, Any>,
    ): ResponseEntity<Unit> {
        createSystemUser()
            .invoke(updatePinUseCase)
            .with(
                UpdatePin.Request(
                    id = id.decode(),
                    name = request.toUpdatePart("name") { it as String? },
                    description = request.toUpdatePart("description") { it as String? },
                )
            )
            .execute()

        return ResponseEntity.noContent().build()
    }

    private fun <T> Map<String, Any>.toUpdatePart(name: String, convert: (Any?) -> T): UpdatePart<T> {
        return if (this.containsKey(name)) {
            UpdatePart.from(convert(this[name]))
        } else {
            UpdatePart.empty()
        }
    }

    @RequestMapping(
        method = [RequestMethod.DELETE],
        value = ["/pins/{id}"],
        produces = ["application/json"]
    )
    override suspend fun deletePin(
        @PathVariable("id") id: String,
    ): ResponseEntity<Unit> {
        TODO("Not yet implemented")
    }

    @RequestMapping(
        method = [RequestMethod.POST],
        value = ["/upload-url"],
        produces = ["application/json"]
    )
    override suspend fun createUploadUrl(
        @RequestBody request: CreateUploadUrlRequest,
    ): ResponseEntity<CreateUploadUrl.Response> {
        TODO("Not yet implemented")
    }
}