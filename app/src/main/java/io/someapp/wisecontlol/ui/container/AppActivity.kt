package io.someapp.wisecontlol.ui.container

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import io.someapp.wisecontlol.R
import io.someapp.wisecontlol.ui.navigation.WiseAppNavigator
import io.someapp.wisecontlol.ui.navigation.WiseRouter
import io.someapp.wisecontlol.ui.utils.BackButtonListener
import ru.terrakok.cicerone.NavigatorHolder
import javax.inject.Inject


class AppActivity : AppCompatActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var supportFragmentInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    @Inject
    lateinit var router: WiseRouter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app)

        if (savedInstanceState == null) {
            router.startAppScreen()
        }
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = supportFragmentInjector


    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(WiseAppNavigator(this, R.id.container))
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.container)

        val isHandleByFragment = fragment is BackButtonListener && fragment.onBackPressed()
        if (!isHandleByFragment) {
            super.onBackPressed()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}