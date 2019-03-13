package io.someapp.wisecontlol.ui.navigation

import androidx.fragment.app.FragmentActivity
import ru.terrakok.cicerone.android.support.SupportAppNavigator

class WiseAppNavigator(
    activity: FragmentActivity,
    containerId: Int
) : SupportAppNavigator(activity, containerId)
