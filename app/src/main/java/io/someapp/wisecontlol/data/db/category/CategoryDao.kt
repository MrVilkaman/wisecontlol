package io.someapp.wisecontlol.data.db.category

import androidx.room.*
import io.someapp.wisecontlol.data.category.CategoryEntity


@Dao
interface CategoryDao {

    @Query("SELECT * FROM CategoryEntity")
    fun getAll(): List<CategoryEntity>

    @Query("SELECT * FROM CategoryEntity WHERE id = :id")
    fun getById(id: Long): CategoryEntity?

    @Insert
    fun insert(employee: CategoryEntity)

    @Update
    fun update(employee: CategoryEntity)

    @Delete
    fun delete(employee: CategoryEntity)

    @Query("SELECT * FROM CategoryEntity WHERE title = :title")
    fun getByName(title: String): CategoryEntity?
}