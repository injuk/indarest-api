package rest.mjis.indarest.application.utils

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import rest.mjis.indarest.application.utils.IdConverter.decode
import rest.mjis.indarest.application.utils.IdConverter.encode

class IdConverterTest {

    @Test
    fun `임의의 Long형 값을 인코딩한 후 디코딩할 경우 원본과 같은 값이 반환된다`() {
        // given
        val origin = 1L

        // when
        val encoded = origin.encode()
        val result = encoded.decode()

        // then
        assertThat(origin).isEqualTo(result)
    }

    @Test
    fun `이미 인코딩된 임의의 값을 디코딩한 후 인코딩할 경우 원본과 같은 값이 반환된다`() {
        // given
        val origin = "Z8DpqgRej7XwRWKlQnb29w3O"

        // when
        val decoded = origin.decode()
        val result = decoded.encode()

        // then
        assertThat(origin).isEqualTo(result)
    }

    @Test
    fun `디코딩할 수 없는 문자열을 디코딩 시도할 경우 예외가 발생한다`() {
        // given
        val origin = "lorem ipsum"

        // when
        val result = assertThrows<RuntimeException> {
            origin.decode()
        }

        // then
        assertThat(result.message).isEqualTo("hashId fails to decode")
    }
}