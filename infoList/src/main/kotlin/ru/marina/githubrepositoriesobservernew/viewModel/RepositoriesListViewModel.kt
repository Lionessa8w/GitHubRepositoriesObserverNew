package ru.marina.githubrepositoriesobservernew.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.marina.githubrepositoriesobservernew.KeyValueStorageApi
import ru.marina.githubrepositoriesobservernew.info.RepositoryListUseCase
import ru.marina.githubrepositoriesobservernew.state.RepositoriesListViewModelState

@HiltViewModel
class RepositoriesListViewModel @Inject constructor() : ViewModel() {

    @Inject
    lateinit var repositoryListUseCase: RepositoryListUseCase

    @Inject
    lateinit var databaseSaveToken: KeyValueStorageApi

    private val _viewStateFlow: MutableStateFlow<RepositoriesListViewModelState> =
        MutableStateFlow(RepositoriesListViewModelState.Loading)
    val viewStateFlow: StateFlow<RepositoriesListViewModelState> = _viewStateFlow.asStateFlow()

    private val _actionFlow = MutableSharedFlow<RepositoriesListViewModelAction>()
    val actionFlow = _actionFlow.asSharedFlow()

    fun updateRepositoriesList() {
        viewModelScope.launch(Dispatchers.IO + CoroutineExceptionHandler { _, throwable ->
            viewModelScope.launch {
                _viewStateFlow.emit(
                    RepositoriesListViewModelState.Error(throwable.localizedMessage ?: "")
                )
            }
        }) {
            _viewStateFlow.emit(RepositoriesListViewModelState.Loading)
            _viewStateFlow.emit(
                RepositoriesListViewModelState
                    .Success(
                        repositoriesModelList = repositoryListUseCase.getRepositoriesList(
                            databaseSaveToken.getToken()
                        )
                    )
            )
        }
    }

    fun clearTokenAndLogout() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                databaseSaveToken.clear()
                _actionFlow.emit(RepositoriesListViewModelAction.LogOut())
            } catch (e: Throwable) {
                Log.e("checkResult", "logoutToken: $e")
                return@launch
            }
        }
    }

}