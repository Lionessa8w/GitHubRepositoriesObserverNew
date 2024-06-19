package ru.marina.githubrepositoriesobservernew.state

sealed class AuthUserTokenViewModelState {

    data object Idle: AuthUserTokenViewModelState()

    data object Loading : AuthUserTokenViewModelState()

    data class ErrorEmptyToken(val message: String) : AuthUserTokenViewModelState()

    data class ErrorInternet(val message: String) : AuthUserTokenViewModelState()

    data class Error(val message: String) : AuthUserTokenViewModelState()


    data object Success : AuthUserTokenViewModelState()
}