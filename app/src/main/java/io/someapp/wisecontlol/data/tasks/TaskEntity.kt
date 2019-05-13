package io.someapp.wisecontlol.data.tasks

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import io.someapp.wisecontlol.data.category.CategoryEntity
import io.someapp.wisecontlol.data.db.category.CategoryDao.Companion.EMPTY_CATEGORY
import io.someapp.wisecontlol.data.db.convertor.DateConverter
import io.someapp.wisecontlol.data.db.convertor.RememberEntityConverter
import java.util.*


@Entity
class TaskEntity {

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    var title: String = ""

    var description: String = ""

    var isDone: Boolean = false

    @TypeConverters(DateConverter::class)
    var startDate: Date? = null

    var categoryId: Long? = EMPTY_CATEGORY

    @TypeConverters(RememberEntityConverter::class)
    var remembers: RememberEntity? = null
}

data class TaskFullEntity(
    var task: TaskEntity,
    var category: CategoryEntity? = null
)
