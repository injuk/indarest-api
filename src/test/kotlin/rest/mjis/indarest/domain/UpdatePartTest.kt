package rest.mjis.indarest.domain

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test

class UpdatePartTest {
    @Test
    fun `UpdatePart는 팩토리 메소드를 통해 생성할 수 있다`() {
        // given
        val data = "test"

        // when
        val definedPart = UpdatePart.from(data)
        val undefinedPart = UpdatePart.empty<String>()

        // then
        assertThat(definedPart.isDefined).isEqualTo(true)
        assertThat(definedPart.value).isEqualTo(data)

        assertThat(undefinedPart.isDefined).isEqualTo(false)
        assertThat(undefinedPart.value).isEqualTo(null)
    }
}