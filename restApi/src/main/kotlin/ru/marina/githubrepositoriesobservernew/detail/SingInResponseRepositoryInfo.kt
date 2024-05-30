package ru.marina.githubrepositoriesobservernew.detail

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class SingInResponseRepositoryInfo  (
    val name: String?,
    val description: String?,
    @SerialName("html_url")
    val htmlUrl: String?,
    @SerialName("license")
    val license: License?,
    @SerialName("forks_count")
    val forks: Int?,
    @SerialName("watchers_count")
    val watchers: Int?,
    @SerialName("stargazers_count")
    val starts: Int?

)

@Serializable
class License(
    val name: String?
)