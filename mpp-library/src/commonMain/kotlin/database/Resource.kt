package database

import model.AnimalRes

class ResourceDatabaseImpl : ResourceDatabase {

    private val animals by lazy {
        generateAnimals()
    }

    override fun getAnimalBy(startIndex: Int, limit: Int): List<AnimalRes> {
        val newAnimals = arrayListOf<AnimalRes>()
        var endOfIndex = startIndex + limit - 1
        if (endOfIndex >= animals.lastIndex) {
            endOfIndex = animals.lastIndex
        }

        for (i in startIndex..endOfIndex) {
            newAnimals.add(animals[i])
        }
        return newAnimals
    }


    companion object {

        private fun generateAnimals(): List<AnimalRes> {
            val animals = arrayListOf<AnimalRes>()
            for (i in 1..1000) {
                animals.add(
                    AnimalRes(
                        name = "Animal-$i",
                        animalCode = "anm-$i-hay"
                    )
                )
            }
            return animals
        }

    }
}

interface ResourceDatabase {
    companion object{

        const val LIMIT = 25

        val database by lazy {
            ResourceDatabaseImpl()
        }
    }

    fun getAnimalBy(startIndex: Int, limit: Int = LIMIT): List<AnimalRes>
}