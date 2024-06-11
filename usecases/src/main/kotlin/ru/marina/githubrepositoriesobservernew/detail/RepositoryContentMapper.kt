package ru.marina.githubrepositoriesobservernew.detail

import javax.inject.Inject

class RepositoryContentMapper @Inject constructor() {

    operator fun invoke(model: SingInResponseRepositoryContent): RepositoryContentModel {
        return RepositoryContentModel(
            content = model.content
        )
    }
}