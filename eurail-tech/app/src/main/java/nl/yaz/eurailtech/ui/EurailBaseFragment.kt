package nl.yaz.eurailtech.ui

import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import timber.log.Timber

open class EurailBaseFragment : Fragment() {

    fun showAlert(message: String? = null, messageRes: Int? = null, onPositive: () -> Unit = {}, onNegative: (() -> Unit)? = null) {
        val messageToDisplay = if (messageRes != null) {
            requireContext().getString(messageRes)
        } else message ?: "Error was not specified"

        val dialogBuilder = AlertDialog.Builder(requireContext())
            .setTitle(android.R.string.dialog_alert_title)
            .setMessage(messageToDisplay)
            .setPositiveButton(android.R.string.ok) { _, _ ->
                onPositive()
            }
        if (onNegative != null) {
            dialogBuilder.setNegativeButton(android.R.string.cancel) { _, _ ->
                onNegative()
            }
        }
        dialogBuilder.create().show()
    }
}