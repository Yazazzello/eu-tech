package nl.yaz.eurailtech.di

import nl.yaz.eurailtech.features.countdown.CountdownContract
import nl.yaz.eurailtech.features.countdown.CountdownPresenterImpl
import nl.yaz.eurailtech.features.maps.MapContract
import nl.yaz.eurailtech.features.maps.MapPresenterImpl
import nl.yaz.eurailtech.features.storage.LocalStorageContract
import nl.yaz.eurailtech.features.storage.LocalStoragePresenterImpl
import nl.yaz.eurailtech.features.webview.WebViewContract
import nl.yaz.eurailtech.features.webview.WebViewPresenterImpl
import org.koin.dsl.module

val featuresModule = module {
    scope(FeatureScopes.MAP.qualifier()) {
        scoped<MapContract.Presenter> { (view: MapContract.View) ->
            MapPresenterImpl(dispatcher = get(), contractView = view)
        }
    }

    scope(FeatureScopes.COUNTDOWN.qualifier()) {
        scoped<CountdownContract.Presenter> { (view: CountdownContract.View) ->
            CountdownPresenterImpl(dispatcher = get(), contractView = view)
        }
    }

    scope(FeatureScopes.STORAGE.qualifier()) {
        scoped<LocalStorageContract.Presenter> { (view: LocalStorageContract.View) ->
            LocalStoragePresenterImpl(storage = get(), dispatcher = get(), contractView = view)
        }
    }

    scope(FeatureScopes.WEBVIEW.qualifier()) {
        scoped<WebViewContract.Presenter> { (view: WebViewContract.View) ->
            WebViewPresenterImpl(eurailRepository = get(), dispatcher = get(), contractView = view)
        }
    }
}