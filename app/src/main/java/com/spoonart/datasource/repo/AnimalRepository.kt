package com.spoonart.datasource.repo

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.spoonart.datasource.source.AnimalDataSourceFactory
import com.spoonart.datasource.source.AnimalDatabase
import model.AnimalRes
import java.util.concurrent.Executor

class AnimalRepository {

    private fun getPageConfig(sizeLoadHint: Int = AnimalDatabase.LIMIT): PagedList.Config {
        return PagedList.Config.Builder()
            //true only for observing the object from Room
            .setEnablePlaceholders(false)
            //initial size to load data at the first load
            .setInitialLoadSizeHint(sizeLoadHint)
            //pagination size to load
            .setPageSize(AnimalDatabase.LIMIT)
            .build()
    }

    fun getLivePagedList(
        executor: Executor,
        dataFactory: AnimalDataSourceFactory
    ): LiveData<PagedList<AnimalRes>> {
        return LivePagedListBuilder(dataFactory, getPageConfig())
            .setFetchExecutor(executor)
            .build()
    }

}