package ru.marina.githubrepositoriesobservernew.info

import javax.inject.Inject
import ru.marina.githubrepositoriesobservernew.detail.RepositoriesModel

class RepositoryListUseCase @Inject constructor(
    private val repository: RepositoriesListRepository,
    private val mapper: RepositoriesModelMapper
) {

    suspend fun getRepositoriesList(token: String): List<RepositoriesModel> {
        return repository.getRepositoriesList(token).map { mapper(it) }
    }
}