package rest.mjis.indarest.domain.useCases

interface CreatePin : UseCase<CreatePin.Request, CreatePin.Response> {
    override val name: String
        get() = CreatePin::class.java.name

    data class Request(
        val name: String?,
        val description: String?,
        val resourceUrl: String,
    )

    data class Response(
        val id: Long,
    )
}