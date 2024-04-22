package rest.mjis.indarest.application.gateways.clients

interface StorageClient {
    suspend fun isExists(key: String): Boolean
    suspend fun deleteAll(keys: List<String>)
}