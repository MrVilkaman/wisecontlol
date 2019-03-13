package io.someapp.wisecontlol.ui.navigation

import io.someapp.wisecontlol.ui.screen.main.MainScreen
import ru.terrakok.cicerone.Router


class WiseRouter : Router() {
    fun startAppScreen() {
        newRootChain(MainScreen)
    }
}

