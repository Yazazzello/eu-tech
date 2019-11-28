package nl.yaz.eurailtech

import io.mockk.mockk
import nl.yaz.eurailtech.di.appModule
import nl.yaz.eurailtech.di.featuresModule
import nl.yaz.eurailtech.di.networkModule
import nl.yaz.eurailtech.features.countdown.CountdownContract
import nl.yaz.eurailtech.features.maps.MapContract
import nl.yaz.eurailtech.features.storage.LocalStorageContract
import nl.yaz.eurailtech.features.webview.WebViewContract
import org.junit.Test
import org.koin.android.ext.koin.androidContext
import org.koin.core.logger.Level
import org.koin.core.parameter.parametersOf
import org.koin.dsl.koinApplication
import org.koin.test.AutoCloseKoinTest
import org.koin.test.check.checkModules

class CheckKoinTest : AutoCloseKoinTest()  {

    @Test
    fun checkModules() {
        val mapView = mockk<MapContract.View>()
        val countdownView = mockk<CountdownContract.View>()
        val localStorageView = mockk<LocalStorageContract.View>()
        val webView = mockk<WebViewContract.View>()
        val koinApplication = koinApplication {
            printLogger(Level.DEBUG)
            androidContext(Mocked.mockedContext)
            modules(listOf(appModule, networkModule, featuresModule, testModule))
        }.koin

        koinApplication.checkModules {
            create<MapContract.Presenter> { parametersOf(mapView) }
            create<CountdownContract.Presenter> { parametersOf(countdownView) }
            create<LocalStorageContract.Presenter> { parametersOf(localStorageView) }
            create<WebViewContract.Presenter> { parametersOf(webView) }
        }
    }
}