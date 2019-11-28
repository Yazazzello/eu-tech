package nl.yaz.eurailtech.ui.map

import android.content.Context
import android.graphics.BitmapFactory
import android.location.Location
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.Point
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.location.LocationComponent
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions
import com.mapbox.mapboxsdk.location.modes.CameraMode
import com.mapbox.mapboxsdk.location.modes.RenderMode
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage
import com.mapbox.mapboxsdk.style.layers.SymbolLayer
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource
import kotlinx.android.synthetic.main.fragment_map.view.*
import nl.yaz.eurailtech.R
import nl.yaz.eurailtech.features.maps.MapContract
import nl.yaz.eurailtech.ui.EurailBaseFragment
import nl.yaz.eurailtech.util.LocationHelper
import nl.yaz.eurailtech.util.getPresenter
import timber.log.Timber


private const val ICON_ID: String = "ICON"

private const val LAYER_ID = "layer-id"

private const val SOURCE_ID = "source-id"

class MapFragment : EurailBaseFragment(), MapContract.View {

    private lateinit var mapBoxMap: MapboxMap
    private lateinit var lastKnownLocation: Location
    override val presenter: MapContract.Presenter = getPresenter(this)

    override val contextHolder: Context by lazy { requireContext() }

    private lateinit var mapView: MapView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapView = view.mapView
        mapView.onCreate(savedInstanceState)
    }

    override fun onDestroyView() {
        mapView.onDestroy()
        super.onDestroyView()
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onStop() {
        mapView.onStop()
        super.onStop()
    }

    override fun initMap() {

        mapView.getMapAsync { map ->
            mapBoxMap = map
            map.setStyle(Style.MAPBOX_STREETS) { style ->
                initStyle(style)
            }
        }
    }

    private fun initStyle(style: Style) {
        enableLocation(style)
        mapBoxMap.cameraPosition = CameraPosition.Builder()
            .target(LatLng(lastKnownLocation))
            .zoom(11.0).build()
        style.addImage(ICON_ID, BitmapFactory.decodeResource(requireContext().resources, R.drawable.ic_marker))

        val randomLocation = LocationHelper.getLocationInLatLngRad(3500.0, lastKnownLocation)
        val fromFeature =
            FeatureCollection.fromFeature(Feature.fromGeometry(Point.fromLngLat(randomLocation.longitude, randomLocation.latitude)))
        val geoJsonSource = GeoJsonSource(SOURCE_ID, fromFeature)
        style.addSource(geoJsonSource)
        style.addLayer(
            SymbolLayer(
                LAYER_ID,
                SOURCE_ID
            ).withProperties(
                iconImage(ICON_ID)
            )
        )
        mapBoxMap.addOnMapClickListener {
            Timber.d("got click at $it")
            return@addOnMapClickListener handleClick(it)
        }
    }

    private fun enableLocation(loadedMapStyle: Style) {
        // Get an instance of the component
        val locationComponent: LocationComponent = mapBoxMap.locationComponent

        // Activate with options
        locationComponent.activateLocationComponent(
            LocationComponentActivationOptions.builder(requireContext(), loadedMapStyle).build()
        )

        // Enable to make component visible
        locationComponent.isLocationComponentEnabled = true

        // Set the component's camera mode
        locationComponent.cameraMode = CameraMode.TRACKING

        // Set the component's render mode
        locationComponent.renderMode = RenderMode.COMPASS
        lastKnownLocation = locationComponent.lastKnownLocation!!
    }

    private fun handleClick(latLng: LatLng): Boolean {
        val toScreenLocation = mapBoxMap.projection.toScreenLocation(latLng)
        val features: List<Feature> = mapBoxMap.queryRenderedFeatures(
            toScreenLocation, LAYER_ID
        )
        if (features.isNotEmpty()) {
            Timber.d("clicked at ${features.first()}")
            val distance = LatLng(lastKnownLocation).distanceTo(latLng).div(1000)
            Toast.makeText(requireContext(), getString(R.string.distance_template, distance), Toast.LENGTH_LONG)
                .show()
            return true
        }
        return false
    }
}
