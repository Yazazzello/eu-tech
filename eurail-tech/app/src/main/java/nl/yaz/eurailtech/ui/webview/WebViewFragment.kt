package nl.yaz.eurailtech.ui.webview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.TextView
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.fragment_webview.view.*
import nl.yaz.eurailtech.R
import nl.yaz.eurailtech.features.webview.WebViewContract
import nl.yaz.eurailtech.ui.EurailBaseFragment
import nl.yaz.eurailtech.util.getPresenter

class WebViewFragment : EurailBaseFragment(), WebViewContract.View {

    override val presenter: WebViewContract.Presenter = getPresenter(this)

    private lateinit var textCounter: TextView
    private lateinit var webView: WebView
    private lateinit var progressBar: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_webview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textCounter = view.text_counter
        webView = view.webview
        progressBar = view.progressBar
    }

    override fun setText(text: String) {
        textCounter.text = resources.getString(R.string.div_counter_template, text)
    }

    override fun loadPage(baseUrl: String, body: String) {
        webView.loadDataWithBaseURL(baseUrl, body, null, "utf-8", null)
    }

    override fun flipProgress(showProgress: Boolean) {
        progressBar.isVisible = showProgress
    }
}
