package ru.marina.githubrepositoriesobservernew.detail

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import ru.marina.githubrepositoriesobservernew.info.RepositoriesInfoModel

class RepositoryDetailUseCase @Inject constructor(
    private val repository: RepositoryInfoRepository,
    private val mapper: RepositoriesInfoModelMapper,
    private val mapperContent: RepositoryContentMapper
) {

    suspend fun getInfoRepository(
        token: String,
        name: String,
        owner: String
    ): RepositoriesInfoModel {

        return mapper.invoke(
            repository.getRepositoryInfo(
                token = token,
                repo = name,
                owner = owner
            )
        )
    }

    suspend fun getRepositoryContext(
        token: String,
        name: String,
        owner: String
    ): RepositoryContentModel {
        return mapperContent.invoke(
            repository.getRepositoryContent(
                token = token,
                repo = name,
                owner = owner
            )
        )
    }
}

@Module
@InstallIn(SingletonComponent::class)
class RepositoryDetailUseCaseProvider {

    @Provides
    fun getProviderRetrofit(
        repository: RepositoryInfoRepository,
        mapper: RepositoriesInfoModelMapper,
        mapperContent: RepositoryContentMapper
    ): RepositoryDetailUseCase {
        return RepositoryDetailUseCase(
            repository, mapper, mapperContent
        )
    }
}