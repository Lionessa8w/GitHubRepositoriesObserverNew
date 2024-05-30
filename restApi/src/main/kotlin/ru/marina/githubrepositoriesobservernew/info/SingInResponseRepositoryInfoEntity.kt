package ru.marina.githubrepositoriesobservernew.info

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class SingInResponseRepositoryInfoEntity (
    val name: String? = null,
    val description: String? = null,
    val language: String? = null,
    @SerialName("owner")
    val owner: Owner? = null
)

@Serializable
class Owner {
    val login: String? = null
}
