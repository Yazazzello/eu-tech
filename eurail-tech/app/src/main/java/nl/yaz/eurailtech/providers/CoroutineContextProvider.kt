@file:Suppress("PropertyName")

package nl.yaz.eurailtech.providers

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newFixedThreadPoolContext
import kotlin.coroutines.CoroutineContext

interface CoroutineContextProvider {
    val IO: CoroutineContext
    val NETWORK: CoroutineContext
    val UI: CoroutineContext
}

class CorContextProviderImpl : CoroutineContextProvider {
    override val IO: CoroutineContext by lazy { Dispatchers.IO }
    override val NETWORK: CoroutineContext by lazy { newFixedThreadPoolContext(4, "network") }
    override val UI: CoroutineContext by lazy { Dispatchers.Main }
}