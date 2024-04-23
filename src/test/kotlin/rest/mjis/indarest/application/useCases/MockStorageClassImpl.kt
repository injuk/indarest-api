package rest.mjis.indarest.application.useCases

import rest.mjis.indarest.application.gateways.clients.StorageClient

class MockStorageClassImpl : StorageClient {
    companion object {
        const val VALID_OBJECT_KEY = "pins/valid-object-key.png"
    }

    override suspend fun isExists(objectKey: String): Boolean {
        return objectKey == VALID_OBJECT_KEY
    }

    override suspend fun deleteAll(objectKeys: List<String>) {
        /* do nothing */
    }

    override suspend fun createUploadUrl(objectKey: String): String {
        return objectKey
    }
}