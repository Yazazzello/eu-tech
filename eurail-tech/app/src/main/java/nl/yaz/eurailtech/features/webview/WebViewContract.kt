package nl.yaz.eurailtech.features.webview

import nl.yaz.eurailtech.di.FeatureScopes
import nl.yaz.eurailtech.mvp.BasePresenter
import nl.yaz.eurailtech.mvp.BaseView

interface WebViewContract {
    interface View : BaseView<Presenter> {
        override fun getViewScopeId() = FeatureScopes.WEBVIEW
        fun setText(text: String)
        fun loadPage(baseUrl: String, body: String)
        fun flipProgress(showProgress: Boolean)
    }

    interface Presenter : BasePresenter<View>
}