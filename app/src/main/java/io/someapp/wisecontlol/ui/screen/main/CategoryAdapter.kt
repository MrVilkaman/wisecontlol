package io.someapp.wisecontlol.ui.screen.main

import android.view.View
import io.someapp.wisecontlol.R
import io.someapp.wisecontlol.data.category.CategoryEntity
import io.someapp.wisecontlol.ui.core.BaseVH
import io.someapp.wisecontlol.ui.core.SimpleBaseAdapter
import kotlinx.android.synthetic.main.layout_category_title_view.view.*


class CategoryAdapter(list: List<CategoryEntity>) : SimpleBaseAdapter<CategoryEntity, CategoryTitleVH>() {
    init {
        items = list
    }

    override fun getLayoutId(): Int = R.layout.layout_category_title_view

    override fun getHolder(view: View): CategoryTitleVH = CategoryTitleVH(view)
}

class CategoryTitleVH(view: View) : BaseVH<CategoryEntity>(view) {
    override fun bind(item: CategoryEntity, position: Int, payloads: Set<String>) {
        itemView.text.text = item.title
    }

}