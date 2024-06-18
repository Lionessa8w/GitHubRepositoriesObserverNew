package ru.marina.githubrepositoriesobservernew.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.noties.markwon.Markwon
import java.lang.IllegalStateException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.marina.githubrepositoriesobservernew.detail.databinding.ItemActiveLinkBinding
import ru.marina.githubrepositoriesobservernew.detail.databinding.ItemDescriptionBinding
import ru.marina.githubrepositoriesobservernew.detail.databinding.ItemEmptyDescriptionBinding
import ru.marina.githubrepositoriesobservernew.detail.databinding.ItemLicenseBinding
import ru.marina.githubrepositoriesobservernew.detail.databinding.ItemStarForkWatcherBinding
import ru.marina.githubrepositoriesobservernew.state.RepositoryInfoItem

private const val LINK = 0
private const val STATISTIC = 1
private const val LICENSE = 2
private const val DESCRIPTION = 3
private const val EMPTY_DESCRIPTION = 4

class RepositoryDetailAdapter(
    private val repositoryInfoItem: List<RepositoryInfoItem>
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private var detailJob: Job? = null

    class RepositoryActiveLinkHolder(
        bindingLink: ItemActiveLinkBinding
    ) : RecyclerView.ViewHolder(bindingLink.root) {
        val linkText: TextView = bindingLink.linkActive
    }

    class RepositoryLicenseHolder(bindingLicense: ItemLicenseBinding) :
        RecyclerView.ViewHolder(bindingLicense.root) {
        val licenseKey: TextView = bindingLicense.licenseKey
    }

    class RepositoryStarForkWatcherHolder(bindingStarForkWatcher: ItemStarForkWatcherBinding) :
        RecyclerView.ViewHolder(bindingStarForkWatcher.root) {
        val numberStars: TextView = bindingStarForkWatcher.starsNumber
        val numberForks: TextView = bindingStarForkWatcher.forksNumber
        val numberWatchers: TextView = bindingStarForkWatcher.watchersNumber
    }

    class RepositoryDescriptionHolder(bindingDescription: ItemDescriptionBinding) :
        RecyclerView.ViewHolder(bindingDescription.root) {
        val descriptionText: TextView = bindingDescription.descriptionText
    }

    class RepositoryEmptyDescription(binding: ItemEmptyDescriptionBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    override fun getItemViewType(position: Int): Int {
        val item = repositoryInfoItem[position]
        return when (item) {
            is RepositoryInfoItem.Description -> DESCRIPTION
            is RepositoryInfoItem.License -> LICENSE
            is RepositoryInfoItem.Link -> LINK
            is RepositoryInfoItem.Statistic -> STATISTIC
            RepositoryInfoItem.EmptyDescription -> EMPTY_DESCRIPTION
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            LINK -> {
                val binding = ItemActiveLinkBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                RepositoryActiveLinkHolder(binding)
            }

            STATISTIC -> {
                val binding = ItemStarForkWatcherBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                RepositoryStarForkWatcherHolder(binding)
            }

            LICENSE -> {
                val binding = ItemLicenseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                RepositoryLicenseHolder(binding)
            }

            DESCRIPTION -> {
                val binding = ItemDescriptionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                RepositoryDescriptionHolder(binding)
            }

            EMPTY_DESCRIPTION -> {
                val binding = ItemEmptyDescriptionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                RepositoryEmptyDescription(binding)
            }

            else -> throw IllegalStateException("")
        }
    }

    override fun getItemCount(): Int = repositoryInfoItem.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is RepositoryActiveLinkHolder -> {
                val item = repositoryInfoItem[position] as? RepositoryInfoItem.Link ?: return
                holder.linkText.text = item.link
            }

            is RepositoryLicenseHolder -> {
                val item = repositoryInfoItem[position] as? RepositoryInfoItem.License ?: return
                holder.licenseKey.text = item.nameLicense
            }

            is RepositoryDescriptionHolder -> {
                detailJob?.cancel()
                val item = repositoryInfoItem[position] as? RepositoryInfoItem.Description ?: return
                val markwon = Markwon.create(holder.descriptionText.context)
                detailJob = scope.launch(Dispatchers.Default) {
                    val markdown = markwon.toMarkdown(item.description ?: "")
                    withContext(Dispatchers.Main) {
                        holder.descriptionText.text = markdown
                    }
                }
            }

            is RepositoryStarForkWatcherHolder -> {
                val item = repositoryInfoItem[position] as? RepositoryInfoItem.Statistic ?: return
                holder.numberForks.text = item.fork
                holder.numberStars.text = item.star
                holder.numberWatchers.text = item.watchers
            }
        }
    }

    fun dispose() {
        scope.cancel()
        detailJob?.cancel()
        detailJob = null
    }
}