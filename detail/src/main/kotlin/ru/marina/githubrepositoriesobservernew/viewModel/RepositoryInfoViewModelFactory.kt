package ru.marina.githubrepositoriesobservernew.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.marina.githubrepositoriesobservernew.KeyValueStorageApi
import ru.marina.githubrepositoriesobservernew.detail.RepositoryInfoUseCase

class RepositoryInfoViewModelFactory(
    private val name: String,
    private val owner: String,
    private val useCase: RepositoryInfoUseCase,
    private val databaseSaveToken: KeyValueStorageApi,
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RepositoryInfoViewModel(owner, name, useCase, databaseSaveToken) as T
    }
}