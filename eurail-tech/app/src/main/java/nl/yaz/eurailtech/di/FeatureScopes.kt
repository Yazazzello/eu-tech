package nl.yaz.eurailtech.di

import androidx.annotation.Keep
import org.koin.core.qualifier.Qualifier
import org.koin.core.qualifier.named

@Keep
enum class FeatureScopes {
    MAP,
    COUNTDOWN,
    STORAGE,
    WEBVIEW;

    fun qualifier(): Qualifier {
        return named(this.name)
    }
}