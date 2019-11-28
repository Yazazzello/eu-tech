package nl.yaz.eurailtech.features.storage

import nl.yaz.eurailtech.di.FeatureScopes
import nl.yaz.eurailtech.mvp.BasePresenter
import nl.yaz.eurailtech.mvp.BaseView

interface LocalStorageContract {
    interface View : BaseView<Presenter> {
        override fun getViewScopeId() = FeatureScopes.STORAGE
        fun displayText(textToDisplay: String)
    }

    interface Presenter : BasePresenter<View> {
        fun saveText(textToSave: String)
    }
}