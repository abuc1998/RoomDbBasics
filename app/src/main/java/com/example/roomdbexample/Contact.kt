package com.example.roomdbexample

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contacts")
data class Contact(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val firstName: String,
    val lastName: String,
    val phoneNumber: String
)

