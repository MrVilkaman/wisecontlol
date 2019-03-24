package io.someapp.wisecontlol.domain


import io.someapp.wisecontlol.data.category.CategoryEntity
import io.someapp.wisecontlol.data.db.WiseDatabase
import io.someapp.wisecontlol.ui.utils.withIO
import javax.inject.Inject

interface CategoryInteractor {
    suspend fun addCategory(name: String)
    suspend fun getAllCategories(): List<CategoryEntity>

}

class CategoryInteractorImpl @Inject constructor(
    private val db: WiseDatabase
) : CategoryInteractor {
    private val dao by lazy { db.categoryDao() }

    override suspend fun getAllCategories(): List<CategoryEntity> =withIO {
        dao.getAll()
    }

    override suspend fun addCategory(name: String) = withIO {

        if (dao.getByName(name) != null) {
            throw IllegalArgumentException()
        }

        dao.insert(CategoryEntity().apply {
            title = name
        })
    }
}