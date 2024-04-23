package rest.mjis.indarest.application.controllers

import org.springframework.http.ResponseEntity
import rest.mjis.indarest.application.controllers.dto.PinDto
import rest.mjis.indarest.application.controllers.dto.PinSummaryDto
import rest.mjis.indarest.application.controllers.dto.request.CreatePinRequest
import rest.mjis.indarest.domain.ListResponses
import rest.mjis.indarest.domain.useCases.CreateUploadUrl


interface IndarestController {
    suspend fun createPin(request: CreatePinRequest): ResponseEntity<Unit>
    suspend fun listPins(size: Int, cursor: String? = null): ResponseEntity<ListResponses<PinSummaryDto>>
    suspend fun getPin(id: String): ResponseEntity<PinDto>
    suspend fun updatePin(id: String, request: Map<String, Any>): ResponseEntity<Unit>
    suspend fun deletePin(id: String): ResponseEntity<Unit>
    suspend fun createUploadUrl(request: rest.mjis.indarest.application.controllers.dto.request.CreateUploadUrlRequest): ResponseEntity<CreateUploadUrl.Response>
}