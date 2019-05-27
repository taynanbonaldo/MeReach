package com.meseems.mereach.database

import androidx.room.*

@Dao
abstract class BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(entity : T) : Long

    @Delete
    abstract fun delete(entity : T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAll(entities: List<T>) : List<Long>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract fun updateAll(entities: List<T>) : Int


}