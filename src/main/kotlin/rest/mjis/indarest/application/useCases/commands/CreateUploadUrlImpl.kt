package rest.mjis.indarest.application.useCases.commands

import rest.mjis.indarest.application.configurations.ApplicationProperties
import rest.mjis.indarest.application.core.annotations.Command
import rest.mjis.indarest.application.gateways.clients.StorageClient
import rest.mjis.indarest.domain.User
import rest.mjis.indarest.domain.models.ResourceType
import rest.mjis.indarest.domain.useCases.CreateUploadUrl
import java.util.*

@Command
class CreateUploadUrlImpl(
    private val properties: ApplicationProperties,

    private val storageClient: StorageClient,
) : CreateUploadUrl {
    override suspend fun execute(user: User, request: CreateUploadUrl.Request): CreateUploadUrl.Response {
        val objectKey = when (request.type) {
            ResourceType.PIN -> properties.path.pins
            ResourceType.PROFILE -> properties.path.profiles
        }
            .let {
                "$it/${UUID.randomUUID()}/${request.fileName}"
            }

        val uploadUrl = storageClient.createUploadUrl(objectKey)
        val resourceUrl = "${properties.endpoint}/${properties.bucket}/$objectKey"

        return CreateUploadUrl.Response(
            uploadUrl = uploadUrl,
            resourceUrl = resourceUrl
        )
    }
}