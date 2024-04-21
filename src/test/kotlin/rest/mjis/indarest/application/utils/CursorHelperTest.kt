package rest.mjis.indarest.application.utils

import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import rest.mjis.indarest.domain.ListResponses
import rest.mjis.indarest.domain.SearchCondition

class CursorHelperTest {
    private val repository = (0..100).toList()

    @Test
    fun `임의의 목록에 CursorHelper를 적용하면 조회 결과와 nextCursor를 계산할 수 있다`(): Unit = runBlocking {
        // given
        val condition = SearchCondition<Int>(
            size = 20,
            cursor = null,
        )

        // when
        val result = CursorHelper.fetch(condition) {
            val startIdx = it.cursor ?: 0
            repository.subList(startIdx, it.size)
        }
            .build {
                it.toString()
            }

        // then
        assertThat(result).isEqualTo(
            ListResponses(
                results = (0..19).toList(),
                cursor = "20",
            )
        )
    }

    @Test
    fun `임의의 목록에 CursorHelper를 적용하면 모든 요소를 순회할 수 있다`(): Unit = runBlocking {
        // given
        val size = 8
        var cursor: String? = null

        val foundElements = mutableListOf<Int>()
        val expectedLoopCount = (repository.size / size) + 1

        // when
        var count = 0
        do {
            val condition = SearchCondition(
                size = size,
                cursor = cursor?.toInt()
            )
            val (elements, nextCursor) = CursorHelper.fetch(condition) {
                val startIdx = it.cursor ?: 0
                val endIdx = (startIdx + it.size).let {
                    if (it > repository.size) {
                        repository.size
                    } else {
                        it
                    }
                }

                repository.subList(startIdx, endIdx)
            }
                .build {
                    it.toString()
                }

            foundElements.addAll(elements)
            cursor = nextCursor
            count++
        } while (cursor != null)


        // then
        assertThat(foundElements).isEqualTo(repository)
        assertThat(count).isEqualTo(expectedLoopCount)
    }
}