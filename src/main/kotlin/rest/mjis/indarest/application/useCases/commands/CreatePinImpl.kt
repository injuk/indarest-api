package rest.mjis.indarest.application.useCases.commands

import rest.mjis.indarest.application.configurations.ApplicationProperties
import rest.mjis.indarest.application.core.annotations.Command
import rest.mjis.indarest.application.gateways.clients.StorageClient
import rest.mjis.indarest.application.gateways.dataAccesses.PinsDataAccess
import rest.mjis.indarest.domain.User
import rest.mjis.indarest.domain.useCases.CreatePin

@Command
class CreatePinImpl(
    private val properties: ApplicationProperties,

    private val pinsDataAccess: PinsDataAccess,

    private val storageClient: StorageClient,
) : CreatePin {
    private val pinStorageEndpoint: String = listOf(
        properties.endpoint,
        properties.bucket,
        properties.path.pins,
    ).joinToString("/")

    override suspend fun execute(user: User, data: CreatePin.Request): CreatePin.Response {
        require(data.resourceUrl.startsWith(pinStorageEndpoint)) { "resource url has invalid storage endpoint" }

        val isObjectExists = storageClient.isExists(data.resourceUrl.getObjectKey())
        if(!isObjectExists) {
            throw RuntimeException("resource(${data.resourceUrl.getObjectKey()}) not found")
        }

        val pinId = pinsDataAccess.insert(user, data)
        return CreatePin.Response(pinId)
    }

    private fun String.getObjectKey(): String = replace(pinStorageEndpoint, properties.path.pins)
}