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

    override suspend fun execute(user: User, request: CreatePin.Request): CreatePin.Response {
        require(request.resourceUrl.startsWith(pinStorageEndpoint)) { "resource url has invalid storage endpoint" }

        val isObjectExists = storageClient.isExists(request.resourceUrl.getObjectKey())
        if (!isObjectExists) {
            throw RuntimeException("resource(${request.resourceUrl.getObjectKey()}) not found")
        }

        val pinId = pinsDataAccess.insert(user, request)

        // TODO: fire-and-forget 방식으로 썸네일 생성 후 리턴하도록 고도화 필요

        return CreatePin.Response(pinId)
    }

    private fun String.getObjectKey(): String = replace(pinStorageEndpoint, properties.path.pins)
}