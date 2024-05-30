package ru.marina.githubrepositoriesobservernew.info

import javax.inject.Inject
import ru.marina.githubrepositoriesobservernew.detail.RepositoriesModel

class RepositoryListUseCase @Inject constructor(
    private val repository: RepositoriesListRepository,
    private val mapper: RepositoriesModelMapper
) {

    suspend fun getRepositoriesList(token: String): List<RepositoriesModel> {
        val listModel = repository.getRepositoriesList(token)
        val listMapper = mutableListOf<RepositoriesModel>()
        for (element in listModel) {
            listMapper.add(mapper.invoke(element))
        }

        return listMapper

    }
}