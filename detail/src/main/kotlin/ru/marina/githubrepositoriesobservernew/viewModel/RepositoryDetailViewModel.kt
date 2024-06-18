package ru.marina.githubrepositoriesobservernew.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import java.nio.charset.StandardCharsets
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.marina.githubrepositoriesobservernew.KeyValueStorageApi
import ru.marina.githubrepositoriesobservernew.detail.RepositoryDetailUseCase
import ru.marina.githubrepositoriesobservernew.state.RepositoryInfoItem
import ru.marina.githubrepositoriesobservernew.state.RepositoryInfoViewModelState

@OptIn(ExperimentalEncodingApi::class)
class RepositoryDetailViewModel(
    private val owner: String,
    private val name: String,
    private val useCase: RepositoryDetailUseCase,
    private val databaseSaveToken: KeyValueStorageApi,
) : ViewModel() {

    private val _viewStateFlow: MutableStateFlow<RepositoryInfoViewModelState> =
        MutableStateFlow(RepositoryInfoViewModelState.Loading)
    val viewStateFlow: StateFlow<RepositoryInfoViewModelState> = _viewStateFlow.asStateFlow()

    fun updateRepositoryInfo() {
        viewModelScope.launch(Dispatchers.IO + CoroutineExceptionHandler { _, throwable ->
            viewModelScope.launch {
                _viewStateFlow.emit(RepositoryInfoViewModelState.Error(throwable.localizedMessage!!))
            }
        }) {
            val itemList: MutableList<RepositoryInfoItem> = mutableListOf()
            _viewStateFlow.emit(RepositoryInfoViewModelState.Loading)

            val model = useCase.getInfoRepository(databaseSaveToken.getToken(), name, owner)

            val content: String? = try {
                useCase.getRepositoryContext(databaseSaveToken.getToken(), name, owner).content?.toMarkdown()
            } catch (e: Throwable) {
                Log.d("checkResult", "$e")
                null
            }
            itemList.add(RepositoryInfoItem.Link(model.htmlUrl))

            if (model.license?.isNotBlank() == true) {
                itemList.add(RepositoryInfoItem.License(model.license))
            }

            itemList.add(
                RepositoryInfoItem.Statistic(
                    model.stars,
                    model.forks,
                    model.watchers
                )
            )

            if (content?.isNotBlank() == true) {
                itemList.add(RepositoryInfoItem.Description(content))
            } else {
                itemList.add(RepositoryInfoItem.EmptyDescription)
            }

            _viewStateFlow.emit(RepositoryInfoViewModelState.Success(itemList))
        }
    }

    private fun String.toMarkdown(): String {
        val listString = this.split("\n")
        var result = ""
        for (line in listString) {
            result += String(Base64.decode(line), StandardCharsets.UTF_8)
        }
        return result
    }
}