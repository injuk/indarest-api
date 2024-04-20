package rest.mjis.indarest.application.useCases.commands

import rest.mjis.indarest.application.core.annotations.Command
import rest.mjis.indarest.domain.User
import rest.mjis.indarest.domain.useCases.CreatePin

@Command
class CreatePinImpl : CreatePin {
    override suspend fun execute(user: User, data: CreatePin.Request): CreatePin.Response {
        TODO("Not yet implemented")
    }
}