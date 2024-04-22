package rest.mjis.indarest.domain

data class AffectedRows(
    val count: Int,
) {
    fun isNotAffected(): Boolean = count == 0
}
