package nl.yaz.eurailtech.mvp

interface BasePresenter<T> {
    var view: T?

    fun onDestroyed()

    fun onStopped()

    fun onStarted()

    fun onCreated()
}