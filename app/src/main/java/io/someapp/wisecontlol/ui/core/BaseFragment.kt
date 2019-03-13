package io.someapp.wisecontlol.ui.core

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.CallSuper
import androidx.annotation.StringRes
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.MvpView
import dagger.android.support.AndroidSupportInjection
import io.someapp.wisecontlol.ui.utils.BackButtonListener
import javax.inject.Inject
import javax.inject.Provider


abstract class BaseFragment<P : BasePresenter<*>> : MvpAppCompatFragment(), BackButtonListener, MvpView {

    abstract var presenter: P

    @Inject
    lateinit var presenterProvider: Provider<P>

    @CallSuper
    open fun providePresenter(): P = presenterProvider.get()

    override fun onAttach(activity: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(activity)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutId(), container, false)
    }

    abstract fun getLayoutId(): Int

    override fun onBackPressed(): Boolean {
        return presenter.onBackPressed()
    }

//    override val toolbar: IToolbar
//        get() {
//            val value = activity
//            return if (value is ToolbarContainer) {
//                value.toolbar
//            } else {
//                throw NotImplementedError(value.toString() + " должна реализовать ToolbarContainer!!")
//            }
//        }

    fun showToast(text: String) {
        context?.let {
            Toast.makeText(it, text, Toast.LENGTH_SHORT).show()
        }
    }

    fun showToast(@StringRes text: Int) {
        showToast(getString(text))
    }
}