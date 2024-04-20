package rest.mjis.indarest.infrastructure.database

import kotlinx.coroutines.reactive.awaitFirst
import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import rest.mjis.indarest.application.gateways.dataAccesses.PinsDataAccess
import rest.mjis.indarest.domain.AffectedRows
import rest.mjis.indarest.domain.PinsSearch
import rest.mjis.indarest.domain.User
import rest.mjis.indarest.domain.models.Pin
import rest.mjis.indarest.domain.useCases.CreatePin
import rest.mjis.indarest.domain.useCases.UpdatePin
import rest.mjis.indarest.infrastructure.database.jooq.tables.references.PINS

@Repository
class PinsDataAccessImpl(
    private val dsl: DSLContext,
) : PinsDataAccess {
    override suspend fun insert(user: User, data: CreatePin.Request): Long = dsl.run {
        insertInto(PINS)
            .set(PINS.NAME, data.name)
            .set(PINS.DESCRIPTION, data.description)
            .set(PINS.RESOURCE_URL, data.resourceUrl)
            .set(PINS.CREATED_BY_ID, user.info.id)
            .returningResult(PINS.ID)
    }.awaitFirst().get(PINS.ID)!!

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