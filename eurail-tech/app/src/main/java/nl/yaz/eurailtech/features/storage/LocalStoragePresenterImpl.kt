package nl.yaz.eurailtech.features.storage

import nl.yaz.eurailtech.mvp.ParentPresenter
import nl.yaz.eurailtech.providers.CoroutineContextProvider
import nl.yaz.eurailtech.shared.IStorage


private const val KEY_SAVED_TEXT = "saved_text"

class LocalStoragePresenterImpl(
    private val storage: IStorage,
    dispatcher: CoroutineContextProvider,
    contractView: LocalStorageContract.View
) : ParentPresenter<LocalStorageContract.View, LocalStorageContract.Presenter>(dispatcher, contractView),
    LocalStorageContract.Presenter {
    
    override fun onStarted() {
        super.onStarted()
        startCo {
            val savedText = storage.getValue(KEY_SAVED_TEXT, "", String::class)
            touchUI {
                displayText(savedText)
            }
        }
    }

    override fun saveText(textToSave: String) {
        startCo {
            storage.putValue(KEY_SAVED_TEXT, textToSave, String::class)
            touchUI {
                displayText(textToSave)
            }
        }
    }
}