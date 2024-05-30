package ru.marina.githubrepositoriesobservernew.info

import javax.inject.Inject
import ru.marina.githubrepositoriesobservernew.detail.RepositoriesModel

class RepositoriesModelMapper @Inject constructor() {
    operator fun invoke(model: SingInResponseRepositoryInfoEntity): RepositoriesModel {
        return RepositoriesModel(
            name = model.name ?: "",
            owner = model.owner?.login ?: "", // TODO:
            description = model.description ?: "",
            login = model.language ?: "",
        )
    }

}