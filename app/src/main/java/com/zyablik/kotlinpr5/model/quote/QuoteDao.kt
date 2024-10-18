package com.zyablik.kotlinpr5.model.quote

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface QuoteDao {
    @Query("select * from quotes")
    fun getAllQuotes(): LiveData<List<Quote>>

    @Insert
    fun insertQuote(vararg quote: Quote)

    @Query("select * from quotes where id = :id ")
    fun getQbyId(id: Int): Quote

    @Update
    fun updateQuote(quote: Quote)

    @Delete
    fun deleteQuote(quote: Quote)
}