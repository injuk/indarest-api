package rest.mjis.indarest.application.useCases.commands

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import rest.mjis.indarest.domain.User
import rest.mjis.indarest.domain.useCases.CreatePin

@ActiveProfiles("local")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CreatePinImplTest {
    @Autowired
    lateinit var useCase: CreatePin

    @Test
    fun temp(): Unit = runBlocking {
        val invalidRequest = CreatePin.Request(
            name = "안뇽",
            description = null,
            resourceUrl = "http://localhost:9000/indarest-resources/pins/001/puppy.png",
        )
        val temp = useCase.execute(User.create(), invalidRequest)

        println(temp)
    }
}