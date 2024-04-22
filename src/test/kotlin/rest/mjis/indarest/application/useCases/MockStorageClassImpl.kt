package rest.mjis.indarest.application.useCases

import rest.mjis.indarest.application.gateways.clients.StorageClient

class MockStorageClassImpl : StorageClient {
    companion object {
        const val VALID_OBJECT_KEY = "pins/valid-object-key.png"
    }

    override suspend fun isExists(key: String): Boolean {
        return key == VALID_OBJECT_KEY
    }

    override suspend fun deleteAll(keys: List<String>) {
        TODO("Not yet implemented")
    }
}