package nl.yaz.eurailtech

import kotlinx.coroutines.Dispatchers
import nl.yaz.eurailtech.providers.CoroutineContextProvider
import kotlin.coroutines.CoroutineContext

class TestCoroutineProvider : CoroutineContextProvider {
    override val IO: CoroutineContext by lazy { Dispatchers.Unconfined }
    override val NETWORK: CoroutineContext by lazy { Dispatchers.Unconfined }
    override val UI: CoroutineContext by lazy { Dispatchers.Unconfined }
}