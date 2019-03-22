package io.someapp.wisecontlol.ui.utils.datedialog


import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import io.someapp.wisecontlol.R

internal class DateTimePagerAdapter : PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        var resId = 0

        when (position) {
            0 -> resId = R.id.dateTimeDatePicker
            1 -> resId = R.id.dateTimeTimePicker
        }

        return container.findViewById(resId)
    }

    override fun getCount(): Int = 2

    override fun isViewFromObject(view: View, `object`: Any): Boolean = view === `object` as View

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) = Unit
}