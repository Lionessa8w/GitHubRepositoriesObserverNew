package ru.marina.githubrepositoriesobservernew.auth

class AuthErrorCodeMapper {

    private val errorCodeMapper = ErrorCodeMapper()

    fun getErrorCode(exception: Throwable): Int? {
        return errorCodeMapper.getErrorCode(exception)
    }

}