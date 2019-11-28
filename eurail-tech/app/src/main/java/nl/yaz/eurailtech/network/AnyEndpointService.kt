package nl.yaz.eurailtech.network

import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Url

interface AnyEndpointService {

    @GET
    fun getUrlAsync(@Url url: String): Deferred<ResponseBody>
}