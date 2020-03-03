package com.spoonart.datasource.source

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.spoonart.datasource.model.Animal


class AnimalDataSourceFactory :
    DataSource.Factory<Int, Animal>() {

    private var animalDataSourceData = MutableLiveData<AnimalDataSource>()

    override fun create(): DataSource<Int, Animal> {
        val dataSource = AnimalDataSource(AnimalDatabase.database)
        animalDataSourceData.postValue(dataSource)
        return dataSource
    }

    fun animalDataSourceLiveData() = animalDataSourceData

}