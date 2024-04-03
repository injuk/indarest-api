package rest.mjis.indarest.domain

data class ListResponses<T>(
    val results: List<T>,
    val cursor: String?,
)
