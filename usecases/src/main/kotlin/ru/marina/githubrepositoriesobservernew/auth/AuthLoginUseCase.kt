package ru.marina.githubrepositoriesobservernew.auth

import javax.inject.Inject

class AuthLoginUseCase @Inject constructor(
    private val authRepository: AuthUserLoginRepository
) {
    suspend fun authLoginUser(token: String): String {
        return authRepository.authLoginUser(token)
    }

}