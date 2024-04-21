package rest.mjis.indarest.application.useCases.queries

import rest.mjis.indarest.application.core.annotations.Query
import rest.mjis.indarest.application.gateways.dataAccesses.PinsDataAccess
import rest.mjis.indarest.application.utils.IdConverter.encode
import rest.mjis.indarest.domain.User
import rest.mjis.indarest.domain.models.Pin
import rest.mjis.indarest.domain.useCases.GetPin

@Query
class GetPinImpl(
    private val pinsDataAccess: PinsDataAccess,
) : GetPin {
    override suspend fun execute(user: User, request: GetPin.Request): Pin =
        pinsDataAccess.findOne(request.id) ?: throw RuntimeException("there is no pin(${request.id.encode()})")
}