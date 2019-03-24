package io.someapp.wisecontlol.data.db.category

import androidx.room.*
import io.someapp.wisecontlol.data.category.CategoryEntity


@Dao
interface CategoryDao {
    companion object {
        const val EMPTY_CATEGORY = 1L
    }


    @Query("SELECT * FROM CategoryEntity")
    fun getAll(): List<CategoryEntity>

    @Query("SELECT * FROM CategoryEntity WHERE categoryId = :id")
    fun getById(id: Long): CategoryEntity?

    @Insert
    fun insert(employee: CategoryEntity)

    @Update
    fun update(employee: CategoryEntity)

    @Delete
    fun delete(employee: CategoryEntity)

    @Query("SELECT * FROM CategoryEntity WHERE categoryTitle = :title")
    fun getByName(title: String): CategoryEntity?
}