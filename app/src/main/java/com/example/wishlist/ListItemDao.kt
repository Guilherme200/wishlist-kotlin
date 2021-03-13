package com.example.wishlist

import androidx.room.*
import kotlin.collections.List

@Dao
interface ListItemDao {
    @Query("select * from ListItem where id = :id")
    fun get(id: Long): ListItem

    @Query("select * from ListItem order by id DESC")
    fun getAll(): List<ListItem>

    @Insert
    fun insert(listItem: ListItem): Long

    @Delete
    fun delete(listItem: ListItem)

    @Update
    fun update(listItem: ListItem)
}
