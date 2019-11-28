package nl.yaz.eurailtech.di

import nl.yaz.eurailtech.BuildConfig
import nl.yaz.eurailtech.network.AnyEndpointService
import nl.yaz.eurailtech.network.NetworkHelper
import nl.yaz.eurailtech.network.createWebService
import org.koin.dsl.module

val networkModule = module {

    single<AnyEndpointService> {
        createWebService(
            okHttpClient = NetworkHelper.getHttpClient(), //usually this should be injected
            baseUrl = BuildConfig.BASE_URL //baseUrl doesn't really matter for our case
        )
    }
}