package io.someapp.wisecontlol.ui.screen.taskinfo

import android.os.Bundle
import android.view.View
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import io.someapp.wisecontlol.R
import io.someapp.wisecontlol.di.FragmentScope
import io.someapp.wisecontlol.ui.core.BaseFragment
import kotlinx.android.synthetic.main.screen_taskinfo_view_edit.*
import ru.terrakok.cicerone.android.support.SupportAppScreen


@FragmentScope
class TaskInfoFragment : BaseFragment<TaskInfoPresenter>(), TaskInfoView {

    @InjectPresenter
    override lateinit var presenter: TaskInfoPresenter

    @ProvidePresenter
    override fun providePresenter(): TaskInfoPresenter = super.providePresenter()

    override fun getLayoutId() = R.layout.screen_taskinfo_view_edit

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

//        name_edit.

//        close.setOnClickListener {
//            presenter.onBackPressed()
//        }
    }
}

object TaskInfoScreen : SupportAppScreen() {
    override fun getFragment() = TaskInfoFragment()
}

interface TaskInfoView : MvpView