package nl.yaz.eurailtech.shared

import android.app.Activity
import android.content.Context
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import com.github.florent37.runtimepermission.PermissionResult
import com.github.florent37.runtimepermission.RuntimePermission
import com.github.florent37.runtimepermission.callbacks.PermissionListener
import com.github.florent37.runtimepermission.kotlin.PermissionException
import nl.yaz.eurailtech.util.showPermissionDeniedDialog
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

interface PermissionHelperView {
    val contextHolder: Context

    /**
     * @param permissionsToAsk example: Manifest.permission.WRITE_EXTERNAL_STORAGE
     */
    suspend fun requirePermissions(vararg permissionsToAsk: String) = suspendCoroutine<Boolean> { continuation ->
        RuntimePermission
                .askPermission(contextHolder as FragmentActivity, *permissionsToAsk)
                .ask(object : PermissionListener {
                    override fun onAccepted(permissionResult: PermissionResult, accepted: List<String>) {
                        continuation.resume(true)
                    }

                    override fun onDenied(permissionResult: PermissionResult, denied: List<String>, foreverDenied: List<String>) {

                        if(permissionResult.hasForeverDenied()) {
                            continuation.resumeWithException(PermissionException(permissionResult))
                        } else {
                            (contextHolder as? Activity)?.showPermissionDeniedDialog("Please, grant the permission", permissionResult)
                        }
                    }
                })
    }
}