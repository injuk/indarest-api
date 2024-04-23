package rest.mjis.indarest.application.gateways.clients

interface StorageClient {
    suspend fun isExists(objectKey: String): Boolean
    suspend fun deleteAll(objectKeys: List<String>)
    suspend fun createUploadUrl(objectKey: String): String
}