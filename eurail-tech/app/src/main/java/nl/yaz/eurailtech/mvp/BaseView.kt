package nl.yaz.eurailtech.mvp

import nl.yaz.eurailtech.di.FeatureScopes

interface BaseView<out T : BasePresenter<*>> {
    val presenter: T
    
    fun getViewScopeId(): FeatureScopes

    fun showAlert(message: String? = null, messageRes: Int? = null, onPositive: () -> Unit = {}, onNegative: (() -> Unit)? = null)
}