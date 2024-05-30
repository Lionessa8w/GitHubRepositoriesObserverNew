package ru.marina.githubrepositoriesobservernew.state

sealed class RepositoryInfoViewModelState {

    data object Loading : RepositoryInfoViewModelState()

    data class Error(val message: String?) : RepositoryInfoViewModelState()

    class Success(
        val itemList: List<RepositoryInfoItem>
    ) : RepositoryInfoViewModelState()
}
sealed interface RepositoryInfoItem{
    data class Link(val link: String?): RepositoryInfoItem
    data class Statistic(val star: String?, val fork: String?, val watchers: String?): RepositoryInfoItem
    data class License(val nameLicense: String?): RepositoryInfoItem
    data class Description(val description: String?): RepositoryInfoItem
    data object EmptyDescription: RepositoryInfoItem

}