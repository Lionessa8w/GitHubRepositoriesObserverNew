package ru.marina.githubrepositoriesobservernew.detail

import javax.inject.Inject
import ru.marina.githubrepositoriesobservernew.info.RepositoriesInfoModel

class RepositoryInfoUseCase @Inject constructor(
    private val repository: RepositoryInfoRepository,
    private val mapper: RepositoriesInfoModelMapper,
    private val mapperContent: RepositoryContentMapper
) {

    suspend fun getInfoRepository(token: String, name: String, owner: String): RepositoriesInfoModel {

        return mapper.invoke(repository.getRepositoryInfo(token = token, repo = name, owner = owner))

    }

    suspend fun getRepositoryContext(token: String, name: String, owner: String): RepositoryContentModel {
        return mapperContent.invoke(repository.getRepositoryContent(token = token, repo = name, owner = owner))
    }
}