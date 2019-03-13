package io.someapp.wisecontlol.data.tasks

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
class TaskEntity {

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    var title: String = ""

}
