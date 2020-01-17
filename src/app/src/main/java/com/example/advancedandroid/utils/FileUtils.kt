package com.example.advancedandroid.utils

import java.io.File
import java.lang.Exception

object FileUtils {
    private fun getAllFilesRecursion(type: String, path: String): List<String> {
        try {
            var result = mutableListOf<String>()
            val rootFolder = File(path)
            val files = rootFolder.listFiles()
            for (file in files) {
                if (file.isDirectory)
                    result.addAll(
                        getAllFilesRecursion(
                            type,
                            file.absolutePath
                        )
                    )
                else if (file.name.endsWith(type))
                    result.add(file.absolutePath)
            }
            return result
        } catch (e: Exception) {
            return mutableListOf()
        }
    }

    fun getAllFiles(type: String, rootPath: String = "/storage/"): List<String> {
        return getAllFilesRecursion(
            type,
            rootPath
        )
    }
}