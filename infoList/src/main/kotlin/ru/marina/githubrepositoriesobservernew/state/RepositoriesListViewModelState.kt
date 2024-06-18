package ru.marina.githubrepositoriesobservernew.state

import ru.marina.githubrepositoriesobservernew.detail.RepositoriesModel

sealed class RepositoriesListViewModelState {

    data object Loading : RepositoriesListViewModelState()

    data class Error(val message: String) : RepositoriesListViewModelState()

    class Success(
        var repositoriesModelList: List<RepositoriesModel>
    ) : RepositoriesListViewModelState()
}