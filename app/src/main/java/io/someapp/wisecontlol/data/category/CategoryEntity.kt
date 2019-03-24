package io.someapp.wisecontlol.data.category

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
class CategoryEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "categoryId")
    var id: Long = 0

    @ColumnInfo(name = "categoryTitle")
    var title: String = ""

}
