package nl.yaz.eurailtech

import android.app.Application
import com.mapbox.mapboxsdk.Mapbox
import leakcanary.AppWatcher
import nl.yaz.eurailtech.di.appModule
import nl.yaz.eurailtech.di.featuresModule
import nl.yaz.eurailtech.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

var koinLoggerLevel = if(BuildConfig.DEBUG) Level.DEBUG else Level.ERROR

class EurailApp : Application() {

    override fun onCreate() {
        super.onCreate()
        if(BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        Mapbox.getInstance(applicationContext, getString(R.string.mapbox_access_token))
        AppWatcher.config = AppWatcher.config.copy(watchFragmentViews = false, enabled = true)

        startKoin {

            androidLogger(koinLoggerLevel)

            // use the Android context given there
            androidContext(this@EurailApp)

            // modules
            modules(listOf(appModule, networkModule, featuresModule))
        }
    }
}