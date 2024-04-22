package rest.mjis.indarest.application.useCases.commands

import rest.mjis.indarest.application.core.annotations.Command
import rest.mjis.indarest.application.gateways.dataAccesses.PinsDataAccess
import rest.mjis.indarest.application.utils.IdConverter.encode
import rest.mjis.indarest.domain.User
import rest.mjis.indarest.domain.useCases.UpdatePin

@Command
class UpdatePinImpl(
    private val pinsDataAccess: PinsDataAccess,
) : UpdatePin {
    override suspend fun execute(user: User, request: UpdatePin.Request) {
        require(request.name.isDefined or request.description.isDefined) { "at least one property must be provided" }

        pinsDataAccess.findOne(request.id) ?: throw RuntimeException("there is no pin(${request.id.encode()})")

        pinsDataAccess.update(
            id = request.id,
            data = request,
        ).also {
            println("it: $it")
            if (it.isNotAffected()) {
                throw RuntimeException("nothing affected from update useCase")
            }
        }
    }
}