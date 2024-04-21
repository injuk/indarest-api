package rest.mjis.indarest.infrastructure.database

import kotlinx.coroutines.reactive.awaitFirst
import org.jooq.DSLContext
import org.jooq.impl.DSL.noCondition
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import rest.mjis.indarest.application.gateways.dataAccesses.PinsDataAccess
import rest.mjis.indarest.domain.AffectedRows
import rest.mjis.indarest.domain.SearchCondition
import rest.mjis.indarest.domain.User
import rest.mjis.indarest.domain.models.ActionContext
import rest.mjis.indarest.domain.models.Pin
import rest.mjis.indarest.domain.models.PinResource
import rest.mjis.indarest.domain.useCases.CreatePin
import rest.mjis.indarest.domain.useCases.UpdatePin
import rest.mjis.indarest.infrastructure.database.jooq.tables.references.PINS
import java.time.OffsetDateTime

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

    override suspend fun findBy(conditions: SearchCondition<Long>): List<Pin.Summary> {
        val responses = dsl.run {
            val selectStep = selectFrom(PINS)
                .where(noCondition())
                .apply {
                    if (conditions.cursor != null) {
                        and(PINS.ID.le(conditions.cursor))
                    }
                }
                .orderBy(PINS.ID.desc())
                .limit(conditions.size)

            return@run Flux.from(selectStep)
        }.collectList().awaitFirst()

        return responses.map {
            Pin.Summary(
                id = it.get(PINS.ID)!!,
                name = it.get(PINS.NAME),
                resource = PinResource(
                    url = it.get(PINS.RESOURCE_URL)!!,
                ),
                created = ActionContext.from(
                    at = it.get(PINS.CREATED_AT)!!,
                    creator = it.get(PINS.CREATED_BY_ID)!!,
                )
            )
        }
    }

    override suspend fun findOne(id: Long): Pin? = dsl.run {
        selectFrom(PINS)
            .where(PINS.ID.eq(id))
    }.awaitFirst()?.let {
        Pin(
            id = it.get(PINS.ID)!!,
            name = it.get(PINS.NAME),
            description = it.get(PINS.DESCRIPTION),
            resource = PinResource(
                url = it.get(PINS.RESOURCE_URL)!!,
            ),
            created = ActionContext.from(
                at = it.get(PINS.CREATED_AT)!!,
                creator = it.get(PINS.CREATED_BY_ID)!!,
            ),
            updated = Pin.Updated(
                at = it.get(PINS.UPDATED_AT)!!.atOffset(OffsetDateTime.now().offset),
            )
        )
    }

    override suspend fun update(id: Long, data: UpdatePin.Request): AffectedRows {
        TODO("Not yet implemented")
    }

    override suspend fun delete(id: Long): AffectedRows {
        TODO("Not yet implemented")
    }
}