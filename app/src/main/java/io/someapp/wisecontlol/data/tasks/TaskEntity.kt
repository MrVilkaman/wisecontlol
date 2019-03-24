package io.someapp.wisecontlol.data.tasks

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(
//    foreignKeys = [
//        ForeignKey(
//            entity = CategoryEntity::class,
//            parentColumns = ["id"],
//            childColumns = ["categoryId"]
//        )
//    ]

)
class TaskEntity {

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    var title: String = ""

    var description: String = ""

//    var categoryId: Int = 0

}
