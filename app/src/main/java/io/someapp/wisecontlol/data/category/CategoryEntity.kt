package io.someapp.wisecontlol.data.category

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
class CategoryEntity {

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    var title: String = ""

}
