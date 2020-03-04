package com.spoonart.datasource.source

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.spoonart.datasource.model.Animal

class AnimalDataSource(
    private val database: AnimalDatabase
) : PageKeyedDataSource<Int, Animal>() {

    companion object {
        const val LIMIT = 25
    }

    private val initialLoading = MutableLiveData<State>()
    private val networkState = MutableLiveData<State>()


    fun getInitialLoading() = initialLoading

    fun getNetworkState(): MutableLiveData<State> = networkState

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Animal>
    ) {

        initialLoading.postValue(State.LOADING)
        networkState.postValue(State.LOADING)

        val animals = database.getAnimalBy(startIndex = 0)
        val startIndex = LIMIT


        Thread.sleep(2000)
        callback.onResult(animals, startIndex, 1)
        initialLoading.postValue(State.FINISH)
        networkState.postValue(State.FINISH)

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Animal>) {
        networkState.postValue(State.LOADING)

        val startIndex = params.key + LIMIT
        val animals = database.getAnimalBy(startIndex)

        Thread.sleep(2000)
        callback.onResult(animals, startIndex)
        networkState.postValue(State.FINISH)

    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Animal>) {
        //ignored
    }

    enum class State {
        LOADING,
        FINISH
    }
}

