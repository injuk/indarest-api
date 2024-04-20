/*
 * This file is generated by jOOQ.
 */
package rest.mjis.indarest.infrastructure.database.jooq.tables


import java.time.LocalDateTime

import kotlin.collections.Collection

import org.jooq.Condition
import org.jooq.Field
import org.jooq.ForeignKey
import org.jooq.Identity
import org.jooq.InverseForeignKey
import org.jooq.Name
import org.jooq.PlainSQL
import org.jooq.QueryPart
import org.jooq.Record
import org.jooq.SQL
import org.jooq.Schema
import org.jooq.Select
import org.jooq.Stringly
import org.jooq.Table
import org.jooq.TableField
import org.jooq.TableOptions
import org.jooq.UniqueKey
import org.jooq.impl.DSL
import org.jooq.impl.SQLDataType
import org.jooq.impl.TableImpl

import rest.mjis.indarest.infrastructure.database.jooq.Mjis
import rest.mjis.indarest.infrastructure.database.jooq.keys.PINS_PKEY
import rest.mjis.indarest.infrastructure.database.jooq.tables.records.PinsRecord


/**
 * This class is generated by jOOQ.
 */
@Suppress("UNCHECKED_CAST")
open class Pins(
    alias: Name,
    path: Table<out Record>?,
    childPath: ForeignKey<out Record, PinsRecord>?,
    parentPath: InverseForeignKey<out Record, PinsRecord>?,
    aliased: Table<PinsRecord>?,
    parameters: Array<Field<*>?>?,
    where: Condition?
): TableImpl<PinsRecord>(
    alias,
    Mjis.MJIS,
    path,
    childPath,
    parentPath,
    aliased,
    parameters,
    DSL.comment(""),
    TableOptions.table(),
    where,
) {
    companion object {

        /**
         * The reference instance of <code>mjis.pins</code>
         */
        val PINS: Pins = Pins()
    }

    /**
     * The class holding records for this type
     */
    override fun getRecordType(): Class<PinsRecord> = PinsRecord::class.java

    /**
     * The column <code>mjis.pins.id</code>.
     */
    val ID: TableField<PinsRecord, Long?> = createField(DSL.name("id"), SQLDataType.BIGINT.nullable(false).identity(true), this, "")

    /**
     * The column <code>mjis.pins.name</code>.
     */
    val NAME: TableField<PinsRecord, String?> = createField(DSL.name("name"), SQLDataType.VARCHAR(256).defaultValue(DSL.field(DSL.raw("NULL::character varying"), SQLDataType.VARCHAR)), this, "")

    /**
     * The column <code>mjis.pins.description</code>.
     */
    val DESCRIPTION: TableField<PinsRecord, String?> = createField(DSL.name("description"), SQLDataType.VARCHAR(1024).defaultValue(DSL.field(DSL.raw("NULL::character varying"), SQLDataType.VARCHAR)), this, "")

    /**
     * The column <code>mjis.pins.resource_url</code>.
     */
    val RESOURCE_URL: TableField<PinsRecord, String?> = createField(DSL.name("resource_url"), SQLDataType.VARCHAR(1024).defaultValue(DSL.field(DSL.raw("NULL::character varying"), SQLDataType.VARCHAR)), this, "")

    /**
     * The column <code>mjis.pins.created_at</code>.
     */
    val CREATED_AT: TableField<PinsRecord, LocalDateTime?> = createField(DSL.name("created_at"), SQLDataType.LOCALDATETIME(6).nullable(false).defaultValue(DSL.field(DSL.raw("CURRENT_TIMESTAMP"), SQLDataType.LOCALDATETIME)), this, "")

    /**
     * The column <code>mjis.pins.created_by_id</code>.
     */
    val CREATED_BY_ID: TableField<PinsRecord, Long?> = createField(DSL.name("created_by_id"), SQLDataType.BIGINT.nullable(false), this, "")

    /**
     * The column <code>mjis.pins.updated_at</code>.
     */
    val UPDATED_AT: TableField<PinsRecord, LocalDateTime?> = createField(DSL.name("updated_at"), SQLDataType.LOCALDATETIME(6).nullable(false).defaultValue(DSL.field(DSL.raw("CURRENT_TIMESTAMP"), SQLDataType.LOCALDATETIME)), this, "")

    private constructor(alias: Name, aliased: Table<PinsRecord>?): this(alias, null, null, null, aliased, null, null)
    private constructor(alias: Name, aliased: Table<PinsRecord>?, parameters: Array<Field<*>?>?): this(alias, null, null, null, aliased, parameters, null)
    private constructor(alias: Name, aliased: Table<PinsRecord>?, where: Condition?): this(alias, null, null, null, aliased, null, where)

    /**
     * Create an aliased <code>mjis.pins</code> table reference
     */
    constructor(alias: String): this(DSL.name(alias))

    /**
     * Create an aliased <code>mjis.pins</code> table reference
     */
    constructor(alias: Name): this(alias, null)

    /**
     * Create a <code>mjis.pins</code> table reference
     */
    constructor(): this(DSL.name("pins"), null)
    override fun getSchema(): Schema? = if (aliased()) null else Mjis.MJIS
    override fun getIdentity(): Identity<PinsRecord, Long?> = super.getIdentity() as Identity<PinsRecord, Long?>
    override fun getPrimaryKey(): UniqueKey<PinsRecord> = PINS_PKEY
    override fun `as`(alias: String): Pins = Pins(DSL.name(alias), this)
    override fun `as`(alias: Name): Pins = Pins(alias, this)
    override fun `as`(alias: Table<*>): Pins = Pins(alias.qualifiedName, this)

    /**
     * Rename this table
     */
    override fun rename(name: String): Pins = Pins(DSL.name(name), null)

    /**
     * Rename this table
     */
    override fun rename(name: Name): Pins = Pins(name, null)

    /**
     * Rename this table
     */
    override fun rename(name: Table<*>): Pins = Pins(name.qualifiedName, null)

    /**
     * Create an inline derived table from this table
     */
    override fun where(condition: Condition?): Pins = Pins(qualifiedName, if (aliased()) this else null, condition)

    /**
     * Create an inline derived table from this table
     */
    override fun where(conditions: Collection<Condition>): Pins = where(DSL.and(conditions))

    /**
     * Create an inline derived table from this table
     */
    override fun where(vararg conditions: Condition?): Pins = where(DSL.and(*conditions))

    /**
     * Create an inline derived table from this table
     */
    override fun where(condition: Field<Boolean?>?): Pins = where(DSL.condition(condition))

    /**
     * Create an inline derived table from this table
     */
    @PlainSQL override fun where(condition: SQL): Pins = where(DSL.condition(condition))

    /**
     * Create an inline derived table from this table
     */
    @PlainSQL override fun where(@Stringly.SQL condition: String): Pins = where(DSL.condition(condition))

    /**
     * Create an inline derived table from this table
     */
    @PlainSQL override fun where(@Stringly.SQL condition: String, vararg binds: Any?): Pins = where(DSL.condition(condition, *binds))

    /**
     * Create an inline derived table from this table
     */
    @PlainSQL override fun where(@Stringly.SQL condition: String, vararg parts: QueryPart): Pins = where(DSL.condition(condition, *parts))

    /**
     * Create an inline derived table from this table
     */
    override fun whereExists(select: Select<*>): Pins = where(DSL.exists(select))

    /**
     * Create an inline derived table from this table
     */
    override fun whereNotExists(select: Select<*>): Pins = where(DSL.notExists(select))
}
