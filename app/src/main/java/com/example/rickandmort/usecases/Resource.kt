package com.example.rickandmort.usecases

data class Resource<out T>(val status: Status, val data: T?, val errorMessage: String?) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(
                status = Status.SUCCESS,
                data = data,
                errorMessage = null
            )
        }

        fun <T> error(data: T? = null, message: String): Resource<T> {
            return Resource(
                status = Status.ERROR,
                data = data,
                errorMessage = message
            )
        }

        fun <T> loading(data: T? = null): Resource<T> {
            return Resource(
                status = Status.LOADING,
                data = data,
                errorMessage = null
            )
        }
    }
}

enum class Status {
    SUCCESS, ERROR, LOADING
}