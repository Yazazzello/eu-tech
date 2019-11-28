package nl.yaz.eurailtech.mvp

import androidx.annotation.CallSuper
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.whenResumed
import androidx.lifecycle.whenStarted
import com.github.florent37.runtimepermission.kotlin.PermissionException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import nl.yaz.eurailtech.BuildConfig
import nl.yaz.eurailtech.R
import nl.yaz.eurailtech.providers.CoroutineContextProvider
import timber.log.Timber
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import kotlin.coroutines.CoroutineContext

var showDebugError = BuildConfig.DEBUG

abstract class ParentPresenter<V : BaseView<P>, out P : BasePresenter<V>>(
    protected val dispatcher: CoroutineContextProvider,
    contractView: V
) : BasePresenter<V> {

    final override var view: V? = null
    private val lifeCycleObserver = PrLifeCycleObserver()
    private var job = SupervisorJob()
    private val exceptionHandler = CoroutineExceptionHandler { context, exc ->
        Timber.tag("CoroutineException")
        Timber.e(exc, "${this.javaClass.simpleName} exception caught in: \n $context")
        CoroutineScope(dispatcher.UI).launch {
            processException(exc)
        }
    }

    protected val presenterScope = CoroutineScope(job + exceptionHandler)

    init {
        view = contractView
        if (view is LifecycleOwner) {
            Timber.tag(this.javaClass.simpleName)
            Timber.d("adding observer to lifecycle [$view]")
            (view as LifecycleOwner).lifecycle.addObserver(lifeCycleObserver)
        } else {
            Timber.w(">>>> $view is not lifecycle owner, you should stop presenter manually")
        }
    }

    private fun processException(exc: Throwable) {
        if (onException(exc)) return

        when (exc) {
            is PermissionException -> {// forever denied
                view?.showAlert(
                    messageRes = R.string.error_grant_permissions_in_settings,
                    onPositive = { exc.goToSettings() })
            }
            is SocketTimeoutException, is UnknownHostException -> {
                view?.showAlert(messageRes = R.string.error_network_connection_issue)
            }
            else -> {
                Timber.e(exc, "exception was not handled")
                if (showDebugError) {
                    view?.showAlert("DebugMessage:: ${exc.message}")
                }
            }
        }
    }

    /**
     * returns true if exception was handled
     */
    protected open fun onException(exc: Throwable): Boolean = false

    open suspend fun touchUI(block: suspend V.() -> Unit) {
        withContext(dispatcher.UI) {
            (view as? LifecycleOwner)?.whenStarted {
                view?.block()
            } ?: view?.block()
        }
    }

    open fun startCo(
        context: CoroutineContext = dispatcher.IO,
        block: suspend CoroutineScope.() -> Unit
    ): Job {
        return presenterScope.launch(context) {
            block(this)
        }
    }

    @CallSuper
    override fun onDestroyed() {
        (view as? LifecycleOwner)?.lifecycle?.removeObserver(lifeCycleObserver)
        view = null
        presenterScope.coroutineContext.cancelChildren()
        presenterScope.coroutineContext.cancel()
    }

    @CallSuper
    override fun onStopped() {
        //implement if required
    }

    @CallSuper
    override fun onCreated() {
        //implement if required
    }

    @CallSuper
    override fun onStarted() {
        //implement if required
    }

    inner class PrLifeCycleObserver : LifecycleObserver {

        @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
        fun onCreate() {
            Timber.tag(this@ParentPresenter.javaClass.simpleName)
            Timber.d("lifecycle owner $view called with ON_CREATE")
            onCreated()
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_START)
        fun onStart() {
            Timber.tag(this@ParentPresenter.javaClass.simpleName)
            Timber.d("lifecycle owner $view called with ON_START")
            onStarted()
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
        fun onStop() {
            Timber.tag(this@ParentPresenter.javaClass.simpleName)
            Timber.d("lifecycle owner $view called with ON_STOP")
            onStopped()
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        fun onDestroy() {
            Timber.tag(this@ParentPresenter.javaClass.simpleName)
            Timber.d("lifecycle owner $view called with ON_DESTROY")
            onDestroyed()
        }
    }
}

