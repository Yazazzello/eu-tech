package nl.yaz.eurailtech.features.localstorage

import io.mockk.clearAllMocks
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
import nl.yaz.eurailtech.features.storage.LocalStorageContract
import nl.yaz.eurailtech.testModule
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.parameter.parametersOf
import org.koin.test.AutoCloseKoinTest
import kotlin.test.assertNotNull

class LocalStorageTest : AutoCloseKoinTest() {

    private lateinit var presenter: LocalStorageContract.Presenter
    private lateinit var contractView: LocalStorageContract.View

    @Before
    fun init() {
        contractView = mockk(relaxed = true)
        startKoin { modules(listOf(appModule, networkModule, featuresModule, testModule)) }
        val scope = getKoin().getOrCreateScope(FeatureScopes.STORAGE.name, FeatureScopes.STORAGE.qualifier())
        presenter = scope.get(parameters = { parametersOf(contractView) })
    }

    @Test
    fun `presenter should not be null`() {
        assertNotNull(presenter)
    }

    @Test
    fun `happy flow read text`() {
        clearAllMocks()
        coEvery {
            Mocked.mockedStorage.getValue("saved_text", "", String::class)
        } returns "foo text"
        presenter.onStarted()
        coVerifyOrder {
            contractView.displayText("foo text")
        }
        excludeRecords { contractView.toString() }
        confirmVerified(contractView)
    }

    @Test
    fun `happy flow save text`() {
        clearAllMocks()
        presenter.saveText("new foo")
        coVerifyOrder {
            contractView.displayText("new foo")
        }
        excludeRecords { contractView.toString() }
        confirmVerified(contractView)
    }
}