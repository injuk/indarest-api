package rest.mjis.indarest.application.gateways.dataAccesses.dto

data class AffectedRows(
    val count: Int,
) {
    fun isNotAffected(): Boolean = count == 0
}
