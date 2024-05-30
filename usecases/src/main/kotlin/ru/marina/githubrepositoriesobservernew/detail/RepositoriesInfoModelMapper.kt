package ru.marina.githubrepositoriesobservernew.detail

import javax.inject.Inject
import ru.marina.githubrepositoriesobservernew.info.RepositoriesInfoModel

class RepositoriesInfoModelMapper @Inject constructor() {
    operator fun invoke(model: SingInResponseRepositoryInfo): RepositoriesInfoModel {
        return RepositoriesInfoModel(
            name = model.name,
            description = model.description,
            htmlUrl = model.htmlUrl,
            license = model.license?.name ?: "",
            forks = model.forks.toString(),
            watchers = model.watchers.toString(),
            stars = model.starts.toString()
        )
    }

}