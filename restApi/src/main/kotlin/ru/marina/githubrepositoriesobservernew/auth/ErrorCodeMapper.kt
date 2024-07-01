package ru.marina.githubrepositoriesobservernew.auth

import retrofit2.HttpException

class ErrorCodeMapper {

    fun getErrorCode(exception: Throwable): Int? {
        return (exception as? HttpException)?.code()
    }
}