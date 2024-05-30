package ru.marina.githubrepositoriesobservernew.state

sealed class AuthUserTokenViewModelState {

    data object Idle: AuthUserTokenViewModelState()

    // загрузка логина
    data object Loading : AuthUserTokenViewModelState()

    // ошибка логина
    data class Error(val message: String) : AuthUserTokenViewModelState()

    // успех

    class Success(val token: String) : AuthUserTokenViewModelState()
}