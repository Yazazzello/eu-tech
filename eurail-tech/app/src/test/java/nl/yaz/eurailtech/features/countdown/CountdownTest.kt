package nl.yaz.eurailtech.features.countdown

import io.mockk.coVerifyOrder
import io.mockk.confirmVerified
import io.mockk.excludeRecords
import io.mockk.mockk
import io.mockk.verifyOrder
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import nl.yaz.eurailtech.di.FeatureScopes
import nl.yaz.eurailtech.di.appModule
import nl.yaz.eurailtech.di.featuresModule
import nl.yaz.eurailtech.di.networkModule
import nl.yaz.eurailtech.features.maps.MapContract
import nl.yaz.eurailtech.testModule
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.parameter.parametersOf
import org.koin.test.AutoCloseKoinTest
import kotlin.test.assertNotNull

class CountdownTest : AutoCloseKoinTest() {

    private lateinit var presenter: CountdownContract.Presenter
    private lateinit var contractView: CountdownContract.View

    @Before
    fun init() {
        contractView = mockk(relaxed = true)
        startKoin { modules(listOf(appModule, networkModule, featuresModule, testModule)) }
        val scope = getKoin().getOrCreateScope(FeatureScopes.COUNTDOWN.name, FeatureScopes.COUNTDOWN.qualifier())
        presenter = scope.get(parameters = { parametersOf(contractView) })
    }

    @Test
    fun `presenter should not be null`() {
        assertNotNull(presenter)
    }

    @Test
    fun `happy flow`() {
        runBlocking {
            presenter.onStarted()
            delay(1200)
        }
        coVerifyOrder {
            contractView.publishCounter(10)
            contractView.publishCounter(9)
            contractView.publishCounter(8)
        }
        excludeRecords { contractView.toString() }
        confirmVerified(contractView)
    }
}