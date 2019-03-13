package io.someapp.wisecontlol.ui.screen.main

import android.os.Bundle
import android.view.View
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import io.someapp.wisecontlol.R
import io.someapp.wisecontlol.di.FragmentScope
import io.someapp.wisecontlol.ui.core.BaseFragment
import kotlinx.android.synthetic.main.screen_main_view.*
import ru.terrakok.cicerone.android.support.SupportAppScreen


@FragmentScope
class MainFragment : BaseFragment<MainPresenter>(), MainView {

    @InjectPresenter
    override lateinit var presenter: MainPresenter

    @ProvidePresenter
    override fun providePresenter(): MainPresenter = super.providePresenter()

    override fun getLayoutId() = R.layout.screen_main_view

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        tasks.setOnClickListener {
            presenter.onClickTasks()
        }
        add_task.setOnClickListener {
            presenter.onClickAddTask()
        }
    }

    override fun success() {
        showToast("success")
    }
}

object MainScreen : SupportAppScreen() {
    override fun getFragment() = MainFragment()
}

interface MainView : MvpView {
    fun success()
}