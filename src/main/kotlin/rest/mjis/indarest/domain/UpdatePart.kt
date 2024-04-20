package rest.mjis.indarest.domain

data class UpdatePart<T>(
    val value: T?,
    val isDefined: Boolean,
) {
    companion object {
        fun <ST> from(value: ST): UpdatePart<ST> {
            return UpdatePart(value, true)
        }

        fun <ST> empty(): UpdatePart<ST> {
            return UpdatePart(null, false)
        }
    }
}