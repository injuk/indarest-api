package rest.mjis.indarest.application.useCases.commands

import rest.mjis.indarest.application.configurations.ApplicationProperties
import rest.mjis.indarest.application.core.annotations.Command
import rest.mjis.indarest.application.gateways.clients.StorageClient
import rest.mjis.indarest.application.gateways.dataAccesses.PinsDataAccess
import rest.mjis.indarest.application.utils.IdConverter.encode
import rest.mjis.indarest.domain.User
import rest.mjis.indarest.domain.useCases.DeletePin

@Command
class DeletePinImpl(
    private val properties: ApplicationProperties,

    private val pinsDataAccess: PinsDataAccess,

    private val storageClient: StorageClient,
) : DeletePin {
    private val pinStorageEndpoint: String = listOf(
        properties.endpoint,
        properties.bucket,
        properties.path.pins,
    ).joinToString("/")

    override suspend fun execute(user: User, request: DeletePin.Request) {
        val pin = (pinsDataAccess.findOne(request.id)
            ?: throw RuntimeException("there is no pin(${request.id.encode()})"))
            .also {
                if (it.created.by.id != user.info.id) {
                    throw RuntimeException("cannot delete other's pin")
                }
            }


        pinsDataAccess.delete(
            id = request.id,
        ).also {
            println("it: $it")
            if (it.isNotAffected()) {
                throw RuntimeException("nothing affected from update useCase")
            }
        }

        pin.getImageResourceUrls().also { urls ->
            if (urls.isNotEmpty()) {
                storageClient.deleteAll(urls.map { it.getObjectKey() })
            }
        }
    }

    private fun String.getObjectKey(): String = replace(pinStorageEndpoint, properties.path.pins)
}