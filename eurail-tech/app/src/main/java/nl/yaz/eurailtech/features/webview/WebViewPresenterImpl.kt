package nl.yaz.eurailtech.features.webview

import nl.yaz.eurailtech.BuildConfig
import nl.yaz.eurailtech.mvp.ParentPresenter
import nl.yaz.eurailtech.providers.CoroutineContextProvider
import nl.yaz.eurailtech.util.OccurrenceCounter
import timber.log.Timber


class WebViewPresenterImpl(
    private val eurailRepository: EurailRepository,
    dispatcher: CoroutineContextProvider,
    contractView: WebViewContract.View
) : ParentPresenter<WebViewContract.View, WebViewContract.Presenter>(dispatcher, contractView),
    WebViewContract.Presenter {

    override fun onCreated() {
        super.onCreated()
        startCo {
            touchUI {
                flipProgress(true)
            }
            val htmlBody = eurailRepository.loadGetInspiredPage()
            val divCounter = OccurrenceCounter.count("<div>", htmlBody)
            Timber.d("div counter $divCounter")

            touchUI {
                loadPage(BuildConfig.BASE_URL, htmlBody)
                setText(divCounter.toString())
                flipProgress(false)
            }
        }
    }

    override fun onException(exc: Throwable): Boolean {
        view?.flipProgress(false)
        return super.onException(exc)
    }
}