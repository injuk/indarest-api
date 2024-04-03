package rest.mjis.indarest.domain.models

data class UserInfo(
    val id: Long,
    val name: String,
    val email: String,
    val profile: UserProfile,
)
