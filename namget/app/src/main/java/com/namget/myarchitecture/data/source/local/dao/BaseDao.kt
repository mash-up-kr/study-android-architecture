package com.namget.myarchitecture.data.source.local.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import io.reactivex.Completable

/**
 * Created by Namget on 2019.10.25.
 */
interface BaseDao<T> {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(obj: T): Completable

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertList(vararg obj: T): Completable

    @Delete
    fun delete(obj: T): Completable

    @Update(onConflict = OnConflictStrategy.ABORT)
    fun update(obj: T): Completable

}