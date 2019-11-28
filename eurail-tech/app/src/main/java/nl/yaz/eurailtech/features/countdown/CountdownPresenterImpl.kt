package nl.yaz.eurailtech.features.countdown

import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import nl.yaz.eurailtech.mvp.ParentPresenter
import nl.yaz.eurailtech.providers.CoroutineContextProvider
import timber.log.Timber
import java.util.concurrent.atomic.AtomicInteger


const val MAX_VALUE_INT = 10

private const val DELAY_MS = 500L

private const val LOW_VALUE_INT = 0

class CountdownPresenterImpl(
    dispatcher: CoroutineContextProvider,
    contractView: CountdownContract.View
) : ParentPresenter<CountdownContract.View, CountdownContract.Presenter>(dispatcher, contractView),
    CountdownContract.Presenter {

    private val counter: AtomicInteger = AtomicInteger(MAX_VALUE_INT)
    private var countDownJob: Job? = null

    override fun getCounterValue() = counter.get()

    override fun setCounterValue(savedValue: Int) {
        counter.set(savedValue)
    }

    override fun onStarted() {
        super.onStarted()
        countDownJob = startCo {
            while (presenterScope.isActive) {

                if (counter.get() < LOW_VALUE_INT) {
                    counter.set(MAX_VALUE_INT)
                }
                val progress = counter.getAndDecrement()
                touchUI {
                    publishCounter(progress)
                }
                delay(DELAY_MS)
            }
        }
    }

    override fun onStopped() {
        super.onStopped()
        countDownJob?.cancel() //to stop job when UI is stopped (on background case)
    }

}