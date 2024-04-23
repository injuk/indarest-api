package rest.mjis.indarest.application.gateways.dataAccesses.dto

data class SearchCondition<T>(
    val size: Int,
    val cursor: T?,
)
