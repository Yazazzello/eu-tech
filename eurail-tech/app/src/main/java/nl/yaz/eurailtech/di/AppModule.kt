package nl.yaz.eurailtech.di


import nl.yaz.eurailtech.features.webview.EurailRepository
import nl.yaz.eurailtech.features.webview.EurailRepositoryImpl
import nl.yaz.eurailtech.providers.CorContextProviderImpl
import nl.yaz.eurailtech.providers.CoroutineContextProvider
import nl.yaz.eurailtech.shared.IStorage
import nl.yaz.eurailtech.shared.PreferencesStorageImpl
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module


val appModule = module {

    single<CoroutineContextProvider> { CorContextProviderImpl() }

    single<IStorage> { PreferencesStorageImpl(androidApplication()) }

    single<EurailRepository> { EurailRepositoryImpl(service = get()) }
}
