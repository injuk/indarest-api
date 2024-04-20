package rest.mjis.indarest.domain

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import rest.mjis.indarest.domain.models.UserInfo
import rest.mjis.indarest.domain.models.UserProfile
import rest.mjis.indarest.domain.useCases.UseCase
import java.util.UUID

class User(
    val info: UserInfo,
) {
    companion object {
        private const val SYSTEM_USER_ID = 1L
        private const val SYSTEM_USER_NAME = "SYSTEM"
        private const val SYSTEM_USER_EMAIL = "system@mjis.rest"
        private const val SYSTEM_USER_PROFILE = "http://localhost:9000/indarest-resources/profiles/system/profile.png"

        fun create(): User = User(
            UserInfo(
                id = SYSTEM_USER_ID,
                name = SYSTEM_USER_NAME,
                email = SYSTEM_USER_EMAIL,
                profile = UserProfile(SYSTEM_USER_PROFILE)
            )
        )
    }

    val traceId: String
        get() = UUID.randomUUID().toString()

    fun <Request, Response> invoke(useCase: UseCase<Request, Response>): UseCaseHolder<Request, Response> =
        UseCaseHolder(
            user = this,
            useCase = useCase,
        )

    class UseCaseHolder<Request, Response>(
        private val user: User,
        private val useCase: UseCase<Request, Response>,
    ) {
        private var request: Request? = null
        private val logger: Logger = LoggerFactory.getLogger(UseCase::class.java)

        fun with(data: Request): UseCaseHolder<Request, Response> = this.apply { request = data }

        suspend fun execute(): Response {
            checkNotNull(request) { "user request cannot be null" }

            logger.info("user(${user.traceId}) executes useCase(${useCase.name})")

            val result = useCase.execute(user, request!!)

            logger.info("user(${user.traceId}) ends useCase(${useCase.name})")

            return result
        }
    }
}