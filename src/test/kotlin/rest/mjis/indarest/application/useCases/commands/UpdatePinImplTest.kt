package rest.mjis.indarest.application.useCases.commands

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import rest.mjis.indarest.domain.UpdatePart
import rest.mjis.indarest.domain.User
import rest.mjis.indarest.domain.useCases.UpdatePin

@ActiveProfiles("local")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UpdatePinImplTest {

    @Autowired
    lateinit var useCase: UpdatePin

    @Test
    fun temp(): Unit = runBlocking {
        // given
        val user = User.create()
        val request = UpdatePin.Request(
            id = 5L,
            name = UpdatePart.from(null),
            description = UpdatePart.from(null),
        )

        // when
        useCase.execute(user, request)
    }
}