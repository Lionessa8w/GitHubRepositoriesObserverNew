package ru.marina.githubrepositoriesobservernew.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import java.net.UnknownHostException
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.marina.githubrepositoriesobservernew.KeyValueStorageApi
import ru.marina.githubrepositoriesobservernew.auth.AuthLoginUseCase
import ru.marina.githubrepositoriesobservernew.state.AuthUserTokenViewModelState

const val TAG = "AuthViewModel"

@HiltViewModel
class AuthViewModel @Inject constructor() : ViewModel() {

    @Inject
    lateinit var databaseSaveToken: KeyValueStorageApi

    @Inject
    lateinit var authLoginUseCase: AuthLoginUseCase

    private var authJob: Job? = null


    private val _viewStateFlow: MutableStateFlow<AuthUserTokenViewModelState> =
        MutableStateFlow(AuthUserTokenViewModelState.Idle)
    val viewStateFlow: StateFlow<AuthUserTokenViewModelState> = _viewStateFlow.asStateFlow()

    fun tryAuth(token: String) {
        authJob?.cancel()
        authJob = viewModelScope.launch(Dispatchers.IO) {
            try {
                _viewStateFlow.emit(AuthUserTokenViewModelState.Loading)
                authLoginUseCase.authLoginUser(token)
                Log.d(TAG, "tryAuth: токен прошел")
                databaseSaveToken.setToken(token)

                Log.d(TAG, "tryAuth: токен загружен в бд")
                _viewStateFlow.emit(AuthUserTokenViewModelState.Success)

            } catch (e: UnknownHostException) {
                _viewStateFlow.emit(AuthUserTokenViewModelState.ErrorInternet(e.message.toString()))

            } catch (e: Throwable) {
                Log.d("checkResult", "tryAuth: $e")
                _viewStateFlow.emit(AuthUserTokenViewModelState.Error(e.message.toString()))
            }

        }
    }

    override fun onCleared() {
        authJob?.cancel()
        super.onCleared()
    }
    fun clearErrorState(){
        viewModelScope.launch {
            _viewStateFlow.emit(AuthUserTokenViewModelState.Idle)
        }
    }

    fun validate(inputToken: String): Boolean {
        return if (inputToken.isNotBlank()) {
            true
        } else {
            viewModelScope.launch {
                _viewStateFlow.emit(AuthUserTokenViewModelState.ErrorEmptyToken(""))
            }
            false
        }
    }
}