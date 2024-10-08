package com.example.data.local.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.data.local.room.entities.VacancyEntity

@Dao
interface VacancyDao {

    @Query("SELECT * FROM vacancyentity")
    fun getAllFavouriteVacancies(): LiveData<List<VacancyEntity>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vacancyEntity: VacancyEntity)

    @Query("SELECT * FROM vacancyentity where id=:id")
    fun getItemById(id: Int): VacancyEntity?

    @Query("delete from vacancyentity where id=:id")
    fun deleteById(id: String)

    @Query("SELECT EXISTS(SELECT 1 FROM vacancyentity WHERE id = :itemId LIMIT 1)")
    fun isItemExists(itemId: String): Boolean

    @Query("SELECT COUNT(*) FROM vacancyentity")
    fun getVacanciesCountOnSaved(): LiveData<Int>
}