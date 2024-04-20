/*
 * This file is generated by jOOQ.
 */
package rest.mjis.indarest.infrastructure.database.jooq.keys


import org.jooq.UniqueKey
import org.jooq.impl.DSL
import org.jooq.impl.Internal

import rest.mjis.indarest.infrastructure.database.jooq.tables.BoardPinMappers
import rest.mjis.indarest.infrastructure.database.jooq.tables.Boards
import rest.mjis.indarest.infrastructure.database.jooq.tables.Pins
import rest.mjis.indarest.infrastructure.database.jooq.tables.Users
import rest.mjis.indarest.infrastructure.database.jooq.tables.records.BoardPinMappersRecord
import rest.mjis.indarest.infrastructure.database.jooq.tables.records.BoardsRecord
import rest.mjis.indarest.infrastructure.database.jooq.tables.records.PinsRecord
import rest.mjis.indarest.infrastructure.database.jooq.tables.records.UsersRecord



// -------------------------------------------------------------------------
// UNIQUE and PRIMARY KEY definitions
// -------------------------------------------------------------------------

val BOARD_PIN_MAPPERS_PKEY: UniqueKey<BoardPinMappersRecord> = Internal.createUniqueKey(BoardPinMappers.BOARD_PIN_MAPPERS, DSL.name("board_pin_mappers_pkey"), arrayOf(BoardPinMappers.BOARD_PIN_MAPPERS.ID), true)
val BOARD_PIN_MAPPERS_UQ_1: UniqueKey<BoardPinMappersRecord> = Internal.createUniqueKey(BoardPinMappers.BOARD_PIN_MAPPERS, DSL.name("board_pin_mappers_uq_1"), arrayOf(BoardPinMappers.BOARD_PIN_MAPPERS.BOARD_ID, BoardPinMappers.BOARD_PIN_MAPPERS.PIN_ID), true)
val BOARDS_PKEY: UniqueKey<BoardsRecord> = Internal.createUniqueKey(Boards.BOARDS, DSL.name("boards_pkey"), arrayOf(Boards.BOARDS.ID), true)
val PINS_PKEY: UniqueKey<PinsRecord> = Internal.createUniqueKey(Pins.PINS, DSL.name("pins_pkey"), arrayOf(Pins.PINS.ID), true)
val USERS_PKEY: UniqueKey<UsersRecord> = Internal.createUniqueKey(Users.USERS, DSL.name("users_pkey"), arrayOf(Users.USERS.ID), true)