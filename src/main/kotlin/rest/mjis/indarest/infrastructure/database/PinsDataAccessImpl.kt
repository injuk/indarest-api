package rest.mjis.indarest.infrastructure.database

import org.springframework.stereotype.Repository
import rest.mjis.indarest.application.gateways.dataAccesses.PinsDataAccess
import rest.mjis.indarest.domain.AffectedRows
import rest.mjis.indarest.domain.PinsSearch
import rest.mjis.indarest.domain.User
import rest.mjis.indarest.domain.models.Pin
import rest.mjis.indarest.domain.useCases.CreatePin
import rest.mjis.indarest.domain.useCases.UpdatePin

@Repository
class PinsDataAccessImpl : PinsDataAccess {
    override suspend fun insert(user: User, data: CreatePin.Request): Long {
        TODO("Not yet implemented")
    }

    override suspend fun findBy(search: PinsSearch): List<Pin.Summary> {
        TODO("Not yet implemented")
    }

    override suspend fun findOne(id: Long): Pin {
        TODO("Not yet implemented")
    }

    override suspend fun update(id: Long, data: UpdatePin.Request): AffectedRows {
        TODO("Not yet implemented")
    }

    override suspend fun delete(id: Long): AffectedRows {
        TODO("Not yet implemented")
    }
}