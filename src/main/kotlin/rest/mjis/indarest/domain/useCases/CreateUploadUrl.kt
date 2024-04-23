package rest.mjis.indarest.domain.useCases

import rest.mjis.indarest.domain.models.ResourceType

interface CreateUploadUrl : UseCase<CreateUploadUrl.Request, CreateUploadUrl.Response> {
    override val name: String
        get() = CreateUploadUrl::class.java.name

    data class Request(
        val type: ResourceType,
        val fileName: String,
    )

    data class Response(
        val url: String,
    )
}