package com.example.wishlist

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ListItem(val title: String, val description: String) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}