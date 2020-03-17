package com.spoonart.datasource.source

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import database.ResourceDatabase
import model.AnimalRes

class AnimalDataSource(
    private val database: ResourceDatabase
) : PageKeyedDataSource<Int, AnimalRes>() {

    private val initialLoading = MutableLiveData<State>()
    private val networkState = MutableLiveData<State>()

    fun getInitialLoading() = initialLoading

    fun getNetworkState(): MutableLiveData<State> = networkState

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, AnimalRes>
    ) {

        initialLoading.postValue(State.LOADING)
        networkState.postValue(State.LOADING)

        val animals = database.getAnimalBy(startIndex = 0)
        val nextIndex = AnimalDatabase.LIMIT + 1


        Thread.sleep(2000)
        callback.onResult(animals, nextIndex, 0)
        initialLoading.postValue(State.FINISH)
        networkState.postValue(State.FINISH)

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, AnimalRes>) {
        networkState.postValue(State.LOADING)

        println("key: ${params.key}")
        val startIndex = params.key + AnimalDatabase.LIMIT
        val animals = database.getAnimalBy(startIndex)

        Thread.sleep(2000)
        callback.onResult(animals, startIndex)
        networkState.postValue(State.FINISH)

    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, AnimalRes>) {
        //ignored
    }

    enum class State {
        LOADING,
        FINISH
    }
}

