package nl.yaz.eurailtech.features.countdown

import nl.yaz.eurailtech.di.FeatureScopes
import nl.yaz.eurailtech.mvp.BasePresenter
import nl.yaz.eurailtech.mvp.BaseView

interface CountdownContract {
    interface View : BaseView<Presenter> {
        override fun getViewScopeId() = FeatureScopes.COUNTDOWN
        fun publishCounter(progress: Int)
    }

    interface Presenter : BasePresenter<View> {
        fun setCounterValue(savedValue: Int)
        fun getCounterValue(): Int
    }
}