package nl.yaz.eurailtech.features.web

import io.mockk.coEvery
import io.mockk.coVerifyOrder
import io.mockk.confirmVerified
import io.mockk.excludeRecords
import io.mockk.mockk
import io.mockk.verifyOrder
import nl.yaz.eurailtech.Mocked
import nl.yaz.eurailtech.di.FeatureScopes
import nl.yaz.eurailtech.di.appModule
import nl.yaz.eurailtech.di.featuresModule
import nl.yaz.eurailtech.di.networkModule
import nl.yaz.eurailtech.features.maps.MapContract
import nl.yaz.eurailtech.features.webview.WebViewContract
import nl.yaz.eurailtech.testModule
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.parameter.parametersOf
import org.koin.test.AutoCloseKoinTest
import kotlin.test.assertNotNull

class WebTest : AutoCloseKoinTest() {

    private lateinit var presenter: WebViewContract.Presenter
    private lateinit var contractView: WebViewContract.View

    @Before
    fun init() {
        contractView = mockk(relaxed = true)
        startKoin { modules(listOf(appModule, networkModule, featuresModule, testModule)) }
        val scope = getKoin().getOrCreateScope(FeatureScopes.WEBVIEW.name, FeatureScopes.WEBVIEW.qualifier())
        presenter = scope.get(parameters = { parametersOf(contractView) })
    }

    @Test
    fun `presenter should not be null`() {
        assertNotNull(presenter)
    }

    @Test
    fun `happy flow`() {
        coEvery {
            Mocked.mockedEurailRepository.loadGetInspiredPage()
        } returns "text with <div> and div"
        presenter.onCreated()
        coVerifyOrder {
            contractView.flipProgress(true)
            contractView.loadPage(any(), "text with <div> and div")
            contractView.setText("1")
            contractView.flipProgress(false)
        }
        excludeRecords { contractView.toString() }
        confirmVerified(contractView)
    }
}