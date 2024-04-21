package rest.mjis.indarest.application.utils

import rest.mjis.indarest.domain.ListResponses
import rest.mjis.indarest.domain.SearchCondition

internal object CursorHelper {
    private const val CHECKER = 1

    suspend fun <Element, Cursor> fetch(
        condition: SearchCondition<Cursor>,
        fetchBy: suspend (SearchCondition<Cursor>) -> List<Element>,
    ): CursorBuilder<Element> {
        val results = fetchBy(
            SearchCondition(
                size = condition.size + CHECKER,
                cursor = condition.cursor,
            )
        )

        return CursorBuilder(condition.size, results)
    }

    class CursorBuilder<Element>(
        private val currSize: Int,
        private val elements: List<Element>,
    ) {
        fun build(block: (Element) -> String): ListResponses<Element> {
            return if (elements.size > currSize) {
                ListResponses(
                    cursor = block(elements.last()),
                    results = elements.dropLast(1),
                )
            } else {
                ListResponses(
                    cursor = null,
                    results = elements,
                )
            }
        }
    }
}