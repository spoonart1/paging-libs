package com.spoonart.datasource

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.spoonart.datasource.model.Animal
import com.spoonart.datasource.repo.AnimalRepository
import com.spoonart.datasource.source.AnimalDataSource
import com.spoonart.datasource.source.AnimalDataSourceFactory
import model.AnimalRes
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class MainViewModel : ViewModel() {

    private val executor: Executor

    private val repository = AnimalRepository()
    private val networkState: LiveData<AnimalDataSource.State>
    private val initialLoading: LiveData<AnimalDataSource.State>
    private val animalPagedLiveData: LiveData<PagedList<AnimalRes>>

    private val dataFactory:AnimalDataSourceFactory

    init {
        executor = Executors.newFixedThreadPool(5)
        dataFactory = AnimalDataSourceFactory()

        networkState =
            Transformations.switchMap(dataFactory.animalDataSourceLiveData()) { dataSource -> dataSource.getNetworkState() }

        initialLoading =
            Transformations.switchMap(dataFactory.animalDataSourceLiveData()) { dataSource -> dataSource.getInitialLoading() }

        animalPagedLiveData = repository.getLivePagedList(executor, dataFactory)
    }

    fun networkState() = networkState

    fun getAnimals() = animalPagedLiveData

    fun getInitialLoading() = initialLoading

    fun invalidate(){
        dataFactory.invalidate()
    }

}