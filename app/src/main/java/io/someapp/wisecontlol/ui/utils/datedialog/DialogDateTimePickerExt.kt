package io.someapp.wisecontlol.ui.utils.datedialog



import androidx.viewpager.widget.ViewPager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.google.android.material.tabs.TabLayout
import io.someapp.wisecontlol.R
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

typealias DateTimeCallback = ((dialog: MaterialDialog, dateTime: LocalDateTime) -> Unit)?

fun MaterialDialog.dateTimePicker(
        selectedDateTime: LocalDateTime? = null,
        minDate: LocalDate? = null,
        maxDate: LocalDate? = null,
        minTime: LocalTime? = null,
        maxTime: LocalTime? = null,
        callback: DateTimeCallback = null
) {
    config.run {

    }

    customView(R.layout.md_datetime_picker_base_pager, noVerticalPadding = true)

    val viewPager = getPager()
    viewPager.adapter = DateTimePagerAdapter()

    getTabLayout().setupWithViewPager(viewPager)
}

private fun MaterialDialog.getPager() = findViewById<ViewPager>(R.id.dateTimePager)!!
private fun MaterialDialog.getTabLayout() = findViewById<TabLayout>(R.id.dateTimeTabs)!!

internal fun ViewPager.onPageSelected(selection: (Int) -> Unit) {
    addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
        override fun onPageSelected(position: Int) = selection(position)

        override fun onPageScrollStateChanged(state: Int) = Unit

        override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
        ) = Unit
    })
}