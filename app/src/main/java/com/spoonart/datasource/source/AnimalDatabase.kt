package com.spoonart.datasource.source

import com.spoonart.datasource.model.Animal

class AnimalDatabaseImpl : AnimalDatabase {

    private val animals by lazy {
        generateAnimals()
    }

    override fun getAnimalBy(startIndex: Int, limit: Int): List<Animal> {
        val newAnimals = arrayListOf<Animal>()
        var endOfIndex = startIndex + limit
        if (endOfIndex >= animals.lastIndex) {
            endOfIndex = animals.lastIndex
        }

        for (i in startIndex..endOfIndex) {
            newAnimals.add(animals[i])
        }
        return newAnimals
    }


    companion object {

        private fun generateAnimals(): List<Animal> {
            val animals = arrayListOf<Animal>()
            for (i in 1..1000) {
                animals.add(
                    Animal(
                        name = "Animal-$i",
                        animalCode = "anm-$i"
                    )
                )
            }
            return animals
        }

    }
}

interface AnimalDatabase {
    companion object{
        val database by lazy {
            AnimalDatabaseImpl()
        }
    }

    fun getAnimalBy(startIndex: Int, limit: Int = AnimalDataSource.LIMIT): List<Animal>
}