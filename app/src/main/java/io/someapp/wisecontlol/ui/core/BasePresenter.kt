package io.someapp.wisecontlol.ui.core

import androidx.annotation.StringRes
import com.arellomobile.mvp.MvpPresenter
import com.arellomobile.mvp.MvpView
import io.someapp.wisecontlol.ui.navigation.WiseRouter
import io.someapp.wisecontlol.ui.utils.BackButtonListener
import kotlinx.coroutines.*
import ru.reglek.checkpoint.ui.utils.TextFormatter
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

abstract class BasePresenter<V : MvpView> : MvpPresenter<V>(),
    BackButtonListener,
    CoroutineScope {

    @Inject
    lateinit var router: WiseRouter

    @Inject
    lateinit var textFormatter: TextFormatter

    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main


    override fun onDestroy() {
        super.onDestroy()
        coroutineContext.cancelChildren()
    }

    fun loadWithProgress(
        body: suspend () -> Unit,
        onThrowable: (Exception) -> Unit = {},
        onProgress: (Boolean) -> Unit = {}
    ): Job {
        return launch {
            onProgress.invoke(true)
            try {
                body.invoke()
            } catch (exception: Exception) {
                onThrowable.invoke(exception)
            } finally {
                onProgress.invoke(false)
            }
        }
    }

    fun getString(@StringRes resId: Int, vararg arg: Any): String = textFormatter.getString(resId, *arg)

    override fun onBackPressed(): Boolean {
        router.exit()
        return true
    }
}