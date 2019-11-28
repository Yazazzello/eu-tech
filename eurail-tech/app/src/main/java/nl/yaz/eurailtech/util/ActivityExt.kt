package nl.yaz.eurailtech.util

import android.app.Activity
import androidx.appcompat.app.AlertDialog
import com.github.florent37.runtimepermission.PermissionResult

fun Activity.showPermissionDeniedDialog(message: String, permissionResult: PermissionResult) {
    AlertDialog.Builder(this)
        .setMessage(message)
        .setPositiveButton("yes") { _, _ ->
            permissionResult.askAgain()
        }
        .setNegativeButton("no") { dialog, _ ->
            dialog.dismiss()
        }
        .show()
}
