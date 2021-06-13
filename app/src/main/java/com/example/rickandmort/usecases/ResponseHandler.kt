package com.example.rickandmort.usecases

import retrofit2.HttpException

class ResponseHandler {

    fun<T> handleException(e: Exception,data: T? = null): Resource<T> {
        return when(e) {
            is HttpException -> Resource.error(data,"Http exception")
            else -> Resource.error(data,"Unknown error!")
        }
    }

    fun<T> handleSuccess(data: T?) : Resource<T> {
        return Resource.success(data)
    }

    fun<T> handleDefaultException(data: T? = null) : Resource<T> {
        return Resource.error(data,"Unknown error")
    }

    fun <T> handleLoading(data: T? = null): Resource<T> {
        return Resource.loading(data)
    }
}