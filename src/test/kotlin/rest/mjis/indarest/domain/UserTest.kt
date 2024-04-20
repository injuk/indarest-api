package rest.mjis.indarest.domain

import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import rest.mjis.indarest.domain.models.UserInfo
import rest.mjis.indarest.domain.models.UserProfile
import rest.mjis.indarest.domain.useCases.UseCase

class UserTest {
    companion object {
        private const val SYSTEM_USER_ID = 1L
        private const val SYSTEM_USER_NAME = "SYSTEM"
        private const val SYSTEM_USER_EMAIL = "system@mjis.rest"
        private const val SYSTEM_USER_PROFILE = "http://localhost:9000/indarest-resources/profiles/system/profile.png"
    }

    @Test
    fun `팩토리 메소드로 생성된 기본 인스턴스는 시스템 사용자이다`(): Unit {
        // given
        val defaultUser = User.create()

        // when
        val defaultUserInfo = defaultUser.info

        // then
        assertThat(defaultUserInfo).isEqualTo(UserInfo(
            id = SYSTEM_USER_ID,
            name = SYSTEM_USER_NAME,
            email = SYSTEM_USER_EMAIL,
            profile = UserProfile(SYSTEM_USER_PROFILE)
        ))
    }

    @Test
    fun `사용자 인스턴스를 활용하여 유즈케이스를 실행할 수 있다`(): Unit = runBlocking {
        // given
        val defaultUser = User.create()
        val addUseCase: UseCase<Pair<Int, Int>, Int> = object : UseCase<Pair<Int, Int>, Int> {
            override val name: String
                get() = "testUseCase"

            override suspend fun execute(user: User, data: Pair<Int, Int>): Int {
                val (operand1, operand2) = data
                return operand1 + operand2
            }
        }

        // when
        val result = defaultUser
            .invoke(addUseCase)
            .with(40 to 2)
            .execute()

        assertThat(result).isEqualTo(42)
    }
}