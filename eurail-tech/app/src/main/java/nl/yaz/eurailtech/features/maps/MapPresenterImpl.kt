package nl.yaz.eurailtech.features.maps

import android.Manifest
import nl.yaz.eurailtech.mvp.ParentPresenter
import nl.yaz.eurailtech.providers.CoroutineContextProvider


class MapPresenterImpl(
    dispatcher: CoroutineContextProvider,
    contractView: MapContract.View
) : ParentPresenter<MapContract.View, MapContract.Presenter>(dispatcher, contractView),
    MapContract.Presenter {

    override fun onStarted() {
        super.onStarted()
        startCo {
            touchUI {
                requirePermissions(Manifest.permission.ACCESS_FINE_LOCATION)
                initMap()
            }
        }
    }
}