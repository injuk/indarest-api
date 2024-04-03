package rest.mjis.indarest.domain.useCases

interface CreatePin : UseCase<CreatePin.Request, CreatePin.Response> {
    data class Request(
        val name: String?,
        val description: String?,
        val resourceUrl: String,
    )

    data class Response(
        val id: Long,
    )
}