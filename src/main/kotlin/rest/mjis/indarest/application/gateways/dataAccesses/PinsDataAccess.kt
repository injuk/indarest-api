package rest.mjis.indarest.application.gateways.dataAccesses

import rest.mjis.indarest.domain.AffectedRows
import rest.mjis.indarest.domain.SearchCondition
import rest.mjis.indarest.domain.User
import rest.mjis.indarest.domain.models.Pin
import rest.mjis.indarest.domain.useCases.CreatePin
import rest.mjis.indarest.domain.useCases.UpdatePin

interface PinsDataAccess {
    suspend fun insert(user: User, data: CreatePin.Request): Long
    suspend fun findBy(conditions: SearchCondition<Long>): List<Pin.Summary>
    suspend fun findOne(id: Long): Pin?
    suspend fun update(id: Long, data: UpdatePin.Request): AffectedRows
    suspend fun delete(id: Long): AffectedRows
}