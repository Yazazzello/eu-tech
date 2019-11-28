package nl.yaz.eurailtech

import android.app.Application
import android.content.Context
import io.mockk.mockk
import kotlinx.coroutines.CoroutineDispatcher
import nl.yaz.eurailtech.Mocked.Companion.mockedContext
import nl.yaz.eurailtech.Mocked.Companion.mockedEurailRepository
import nl.yaz.eurailtech.Mocked.Companion.mockedStorage
import nl.yaz.eurailtech.features.webview.EurailRepository
import nl.yaz.eurailtech.providers.CoroutineContextProvider
import nl.yaz.eurailtech.shared.IStorage
import org.koin.dsl.module


class Mocked {
    companion object {
        val mockedContext = mockk<Application>(relaxed = true)
        val mockedStorage = mockk<IStorage>(relaxed = true)
        val mockedEurailRepository = mockk<EurailRepository>(relaxed = true)
    }
}

val testModule = module {
    single<Context>(override = true) { mockedContext }
    single(override = true) { mockedStorage }
    single(override = true) { mockedEurailRepository }
    single<CoroutineContextProvider>(override = true) { TestCoroutineProvider() }
}