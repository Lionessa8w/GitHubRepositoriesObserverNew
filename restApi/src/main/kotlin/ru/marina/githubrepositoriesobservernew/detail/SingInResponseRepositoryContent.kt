package ru.marina.githubrepositoriesobservernew.detail

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class SingInResponseRepositoryContent (
    @SerialName("content")
    val content: String?
)