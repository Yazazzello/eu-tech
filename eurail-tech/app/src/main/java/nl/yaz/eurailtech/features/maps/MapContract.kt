package nl.yaz.eurailtech.features.maps

import nl.yaz.eurailtech.di.FeatureScopes
import nl.yaz.eurailtech.mvp.BasePresenter
import nl.yaz.eurailtech.mvp.BaseView
import nl.yaz.eurailtech.shared.PermissionHelperView

interface MapContract {
    interface View : BaseView<Presenter>, PermissionHelperView {
        override fun getViewScopeId() = FeatureScopes.MAP
        fun initMap()
    }

    interface Presenter : BasePresenter<View>
}