package rest.mjis.indarest.domain.models

enum class ResourceType(val value: String) {
    PIN("PIN"),
    PROFILE("PROFILE");

    companion object {
        fun from(value: String): ResourceType? = entries.find { it.value == value }
    }
}