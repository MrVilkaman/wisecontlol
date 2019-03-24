package io.someapp.wisecontlol.ui.screen.smssender

import android.telephony.SmsManager
import com.arellomobile.mvp.InjectViewState
import io.someapp.wisecontlol.R
import io.someapp.wisecontlol.di.FragmentScope
import io.someapp.wisecontlol.di.SomeId
import io.someapp.wisecontlol.domain.TaskInteractor
import io.someapp.wisecontlol.ui.core.BasePresenter
import kotlinx.coroutines.launch
import javax.inject.Inject


@InjectViewState
@FragmentScope
class SmsSenderPresenter @Inject constructor(
    @SomeId private val tastId: Long,
    private val taskInteractor: TaskInteractor
) : BasePresenter<SmsSenderView>() {


    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        launch {
            val task = taskInteractor.getTask(tastId)
            viewState.updateUI(task)
        }
    }

    fun sendSms(address: String, message: String) {
        try {
            val smsManager = SmsManager.getDefault()
            smsManager.sendTextMessage(address, null, message, null, null)
            viewState.showToast(R.string.send_success)
            router.exit()
        } catch (ex: Exception) {
            viewState.showToast(R.string.send_error)
        }

    }
}