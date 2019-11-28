package nl.yaz.eurailtech.ui.storage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_local_storage.view.*
import nl.yaz.eurailtech.R
import nl.yaz.eurailtech.features.storage.LocalStorageContract
import nl.yaz.eurailtech.ui.EurailBaseFragment
import nl.yaz.eurailtech.util.getPresenter

class LocalStorageFragment : EurailBaseFragment(), LocalStorageContract.View {

    companion object {
        fun newInstance() = LocalStorageFragment()
    }

    override val presenter: LocalStorageContract.Presenter = getPresenter(this)

    private lateinit var editText: EditText
    private lateinit var savedText: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_local_storage, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        editText = view.edit_text
        savedText = view.saved_text
        view.button_save.setOnClickListener {
            presenter.saveText(editText.text.toString())
        }

    }

    override fun displayText(textToDisplay: String) {
        savedText.text = textToDisplay
    }
}