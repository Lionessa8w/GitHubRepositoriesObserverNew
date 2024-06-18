package ru.marina.githubrepositoriesobservernew.viewModel

import javax.inject.Inject
import ru.marina.githubrepositoriesobservernew.KeyValueStorageApi
import ru.marina.githubrepositoriesobservernew.detail.RepositoryDetailUseCase

class RepositoryDetailViewModelFactoryProvider @Inject constructor(
    private val useCase: RepositoryDetailUseCase,
    private val databaseSaveToken: KeyValueStorageApi,
) {
    fun getRepositoryDetailViewModelFactory(
        name: String,
        owner: String,
    ): RepositoryDetailViewModelFactory {
        return RepositoryDetailViewModelFactory(
            name, owner, useCase, databaseSaveToken
        )
    }
}