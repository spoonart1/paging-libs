package com.spoonart.datasource.repo

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.spoonart.datasource.model.Animal
import com.spoonart.datasource.source.AnimalDataSource
import com.spoonart.datasource.source.AnimalDataSourceFactory
import java.util.concurrent.Executor

class AnimalRepository {

    private fun getPageConfig(sizeLoadHint: Int = AnimalDataSource.LIMIT): PagedList.Config {
        return PagedList.Config.Builder()
            //true only for observing the object from Room
            .setEnablePlaceholders(false)
            //initial size to load data at the first load
            .setInitialLoadSizeHint(sizeLoadHint)
            //pagination size to load
            .setPageSize(AnimalDataSource.LIMIT)
            .build()
    }

    fun getLivePagedList(
        executor: Executor,
        dataFactory: AnimalDataSourceFactory
    ): LiveData<PagedList<Animal>> {
        return LivePagedListBuilder(dataFactory, getPageConfig())
            .setFetchExecutor(executor)
            .build()
    }

}