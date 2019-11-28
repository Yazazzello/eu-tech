package nl.yaz.eurailtech.ui.countdown

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_countdown.view.*
import nl.yaz.eurailtech.R
import nl.yaz.eurailtech.features.countdown.CountdownContract
import nl.yaz.eurailtech.features.countdown.MAX_VALUE_INT
import nl.yaz.eurailtech.ui.EurailBaseFragment
import nl.yaz.eurailtech.util.getPresenter

private const val CURRENT_VALUE_KEY = "current_value"

class CountDownFragment : EurailBaseFragment(), CountdownContract.View {

    override val presenter: CountdownContract.Presenter = getPresenter(this)

    private lateinit var progressBar: ProgressBar
    private lateinit var progressText: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        savedInstanceState?.getInt(CURRENT_VALUE_KEY)?.let {
            presenter.setCounterValue(it)
        }
        return inflater.inflate(R.layout.fragment_countdown, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressText = view.text_progress
        progressBar = view.progressBar
        progressBar.max = MAX_VALUE_INT
    }

    override fun publishCounter(progress: Int) {
        progressText.text = getString(R.string.counter_template, progress.toString())
        progressBar.progress = progress
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(CURRENT_VALUE_KEY, presenter.getCounterValue())
    }
}
