package com.example.advancedandroid.utils

import android.os.AsyncTask

class AsyncTaskPipeline<Params, Result>(private val pipeline: TaskPipeline<Params, Result>): AsyncTask<Params, Unit, Result>() {
    override fun doInBackground(vararg params: Params): Result {
        return pipeline.execute(params[0])
    }
}

class TaskPipeline<Params, Result>
    (private val function: (Params) -> Result) {

    fun execute(params: Params): Result = function.invoke(params)
    fun<NextResult> addTask(nextFunction: (Result) -> NextResult): TaskPipeline<Params, NextResult> {
        val f: (Params)->NextResult = {
            nextFunction.invoke(execute(it))
        }
        return TaskPipeline(f)
    }
}