package rest.mjis.indarest.application.utils

import org.hashids.Hashids

internal object IdConverter {
    private const val SALT = "Indarest API"
    private const val LENGTH = 24

    private val converter = Hashids(SALT, LENGTH)

    internal fun Long.encode(): String = convert(this)

    private fun convert(plain: Long?): String =
        plain?.let { converter.encode(it) } ?: throw RuntimeException("hashId fails to encode")

    internal fun String.decode(): Long = convert(this)

    private fun convert(cipher: String?): Long =
        cipher?.let { converter.decode(it).firstOrNull() } ?: throw RuntimeException("hashId fails to decode")
}