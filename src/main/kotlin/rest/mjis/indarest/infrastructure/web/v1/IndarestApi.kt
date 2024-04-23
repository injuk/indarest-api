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
import rest.mjis.indarest.application.utils.IdConverter.encode
import rest.mjis.indarest.domain.ListResponses
import rest.mjis.indarest.domain.User
import rest.mjis.indarest.domain.useCases.*
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

    @RequestMapping(
        method = [RequestMethod.POST],
        value = ["/pins"],
        produces = ["application/json"]
    )
    override suspend fun createPin(
        @RequestBody request: CreatePinRequest,
    ): ResponseEntity<Unit> {
        val (pinId) = User.create()
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
        TODO("Not yet implemented")
    }

    @RequestMapping(
        method = [RequestMethod.GET],
        value = ["/pins/{id}"],
        produces = ["application/json"]
    )
    override suspend fun getPin(
        @PathVariable("id") id: String,
    ): ResponseEntity<PinDto> {
        TODO("Not yet implemented")
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
        TODO("Not yet implemented")
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