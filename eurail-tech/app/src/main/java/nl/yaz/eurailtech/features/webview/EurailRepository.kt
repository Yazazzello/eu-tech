package nl.yaz.eurailtech.features.webview

import nl.yaz.eurailtech.network.AnyEndpointService

interface EurailRepository {
    suspend fun loadGetInspiredPage(): String
}

private const val GET_INSPIRED_URL = "https://www.eurail.com/en/get-inspired"

class EurailRepositoryImpl(private val service: AnyEndpointService): EurailRepository {

    override suspend fun loadGetInspiredPage(): String {
        return service.getUrlAsync(GET_INSPIRED_URL).await().string()
    }
}