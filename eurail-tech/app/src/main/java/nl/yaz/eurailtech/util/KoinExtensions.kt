package nl.yaz.eurailtech.util

import android.content.ComponentCallbacks
import androidx.lifecycle.LifecycleOwner
import nl.yaz.eurailtech.mvp.BasePresenter
import nl.yaz.eurailtech.mvp.BaseView

import org.koin.android.ext.android.getKoin
import org.koin.androidx.scope.bindScope
import org.koin.core.parameter.parametersOf
import org.koin.java.KoinJavaComponent.getKoin

/**
 * only for binding presenters to its views
 *
 * automatically create scope and binds it to lifecycle owner
 * presenter is created immediately and honors lifecycle callbacks
 * pass view as a parameter
 */
inline fun <reified T : BasePresenter<*>> ComponentCallbacks.getPresenter(view: BaseView<*>, vararg parameters: Any?): T {
    require(view is LifecycleOwner) { "$view is not a lifecycle owner" }
    (view as LifecycleOwner).let {
        val scope = getKoin().getOrCreateScope(view.getViewScopeId().name, view.getViewScopeId().qualifier())
        it.bindScope(scope)
        return scope.get<T>(null, parameters = { parametersOf(view, *parameters) })
    }
}