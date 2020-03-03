package com.spoonart.datasource

import com.google.gson.Gson
import com.spoonart.datasource.model.Animal
import java.io.File
import java.io.FileOutputStream

class JsonFileGenerator {

    fun saveAsFile(animals: List<Animal>) {
        val jsonString = toJsonString(animals)
        val fileName = "sample.json"
        val fullPath = "/Users/moka/Documents/DataSource/app/build/$fileName"
        val file = File(fullPath)
        if(file.exists()){
            return
        }
        val outPutStream = FileOutputStream(file)
        val bytes = jsonString.toByteArray()
        outPutStream.write(bytes)
        outPutStream.close()
    }

    private fun <T> toJsonString(data: T) = Gson().toJson(data)

}