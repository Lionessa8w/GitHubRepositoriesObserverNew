package ru.marina.githubrepositoriesobservernew.auth

import javax.inject.Inject
import javax.inject.Singleton
import ru.marina.githubrepositoriesobservernew.detail.Const

@Singleton
class AuthUserLoginRepository @Inject constructor(
    private var userLoginProviderRetrofit: UserLoginProviderRetrofit
) {

    suspend fun authLoginUser(token: String): String {
        return userLoginProviderRetrofit.getUserLoginProviderRetrofit()
            .getUserLogin("${Const.START_POINT} $token")
            .login
    }
}