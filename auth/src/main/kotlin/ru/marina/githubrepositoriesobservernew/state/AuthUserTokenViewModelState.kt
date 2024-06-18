package ru.marina.githubrepositoriesobservernew.state

sealed class AuthUserTokenViewModelState {

    data object Idle: AuthUserTokenViewModelState()

    data object Loading : AuthUserTokenViewModelState()

    data class Error(val message: String) : AuthUserTokenViewModelState()

    // успех //TODO нужен ли токен?
    class Success(val token: String) : AuthUserTokenViewModelState()
}