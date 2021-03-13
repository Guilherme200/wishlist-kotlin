package com.example.wishlist

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(ListItem::class), version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun ListItemDao(): ListItemDao
}