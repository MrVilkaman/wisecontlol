package io.someapp.wisecontlol.ui.screen.main

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.view.View
import androidx.core.content.FileProvider
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.input
import com.afollestad.materialdialogs.list.customListAdapter
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.github.florent37.runtimepermission.kotlin.askPermission
import io.someapp.wisecontlol.BuildConfig
import io.someapp.wisecontlol.R
import io.someapp.wisecontlol.data.category.CategoryEntity
import io.someapp.wisecontlol.di.FragmentScope
import io.someapp.wisecontlol.ui.core.BaseFragment
import io.someapp.wisecontlol.ui.core.ItemListener
import kotlinx.android.synthetic.main.screen_main_view.*
import ru.terrakok.cicerone.android.support.SupportAppScreen
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream


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

        categories.setOnClickListener {
            presenter.onClickCategories()
        }
        add_category.setOnClickListener {
            showAddCategoryDialog()
        }

        email.setOnClickListener {
            askPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE) {
                exportDB()
            }.onDeclined { e ->
                e.goToSettings()
            }
        }
    }

    private fun exportDB() {

        val sd = Environment.getExternalStorageDirectory()
        val data = Environment.getDataDirectory()
        val currentDBPath = "/data/io.someapp.wisecontlol/databases/database"
        val backupDBPath = "database"
        val currentDB = File(data, currentDBPath)
        val backupDB = File(sd, backupDBPath)

        FileInputStream(currentDB).channel.use { source->
            FileOutputStream(backupDB).channel.use { destination->
                destination.transferFrom(source, 0, source.size())
                showToast("DB Exported!")

                val i = Intent(Intent.ACTION_SEND)
                i.putExtra(Intent.EXTRA_TEXT, "Back Up wisecontlol")
                i.type = "application/octet-stream"

                val uriForFile =
                    FileProvider.getUriForFile(context!!, BuildConfig.APPLICATION_ID + ".provider", backupDB);
                i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                i.putExtra(Intent.EXTRA_STREAM, uriForFile)
                startActivity(Intent.createChooser(i, "Send e-mail"))
            }
        }
    }

    private fun showAddCategoryDialog() {
        MaterialDialog(context!!).show {
            input(hintRes = R.string.added_category_hint, allowEmpty = false) { _, text ->
                presenter.tryAddCategory(text.toString())
            }
            positiveButton(R.string.submit)
        }
    }

    override fun showCategoriesChooseDialog(list: List<CategoryEntity>) {
        val adapter = CategoryAdapter(list)

        MaterialDialog(context!!).show {
            val materialDialog = this
            customListAdapter(adapter)
            positiveButton(R.string.close)

            adapter.onClick = object : ItemListener<CategoryEntity> {
                override fun click(value: CategoryEntity) {
                    presenter.onClickCategory(value)
                    materialDialog.dismiss()
                }
            }
        }
    }
}

object MainScreen : SupportAppScreen() {
    override fun getFragment() = MainFragment()
}

@StateStrategyType(value = SkipStrategy::class)
interface MainView : MvpView {

    fun showToast(textId: Int)

    fun showCategoriesChooseDialog(list: List<CategoryEntity>)
}