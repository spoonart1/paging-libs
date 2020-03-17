package com.spoonart.datasource.source

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.spoonart.datasource.model.Animal
import database.ResourceDatabase
import model.AnimalRes


class AnimalDataSourceFactory :
    DataSource.Factory<Int, AnimalRes>() {

    private var animalDataSourceData = MutableLiveData<AnimalDataSource>()

    private val dataSource by lazy{
        AnimalDataSource(ResourceDatabase.database)
    }

    override fun create(): DataSource<Int, AnimalRes> {

        animalDataSourceData.postValue(dataSource)
        return dataSource
    }

    fun animalDataSourceLiveData() = animalDataSourceData

    fun invalidate(){
        dataSource.invalidate()
    }

}