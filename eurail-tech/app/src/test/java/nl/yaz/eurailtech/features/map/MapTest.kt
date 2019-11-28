package nl.yaz.eurailtech.features.map

import io.mockk.coVerifyOrder
import io.mockk.confirmVerified
import io.mockk.excludeRecords
import io.mockk.mockk
import io.mockk.verifyOrder
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

class MapTest : AutoCloseKoinTest() {

    private lateinit var presenter: MapContract.Presenter
    private lateinit var contractView: MapContract.View

    @Before
    fun init() {
        contractView = mockk(relaxed = true)
        startKoin { modules(listOf(appModule, networkModule, featuresModule, testModule)) }
        val scope = getKoin().getOrCreateScope(FeatureScopes.MAP.name, FeatureScopes.MAP.qualifier())
        presenter = scope.get(parameters = { parametersOf(contractView) })
    }

    @Test
    fun `presenter should not be null`() {
        assertNotNull(presenter)
    }

    @Test
    fun `happy flow`() {
        presenter.onStarted()
        coVerifyOrder {
            contractView.requirePermissions("android.permission.ACCESS_FINE_LOCATION")
            contractView.initMap()
        }
        excludeRecords { contractView.toString() }
        confirmVerified(contractView)
    }
}