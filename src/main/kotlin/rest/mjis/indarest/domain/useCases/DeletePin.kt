package rest.mjis.indarest.domain.useCases

interface DeletePin : UseCase<DeletePin.Request, Unit> {
    override val name: String
        get() = DeletePin::class.java.name

    data class Request(
        val id: Long,
    )
}