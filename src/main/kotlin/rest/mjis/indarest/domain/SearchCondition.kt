package rest.mjis.indarest.domain

data class SearchCondition<T>(
    val size: Int,
    val cursor: T?,
)
