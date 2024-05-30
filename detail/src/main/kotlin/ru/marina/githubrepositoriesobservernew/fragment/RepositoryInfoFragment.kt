package ru.marina.githubrepositoriesobservernew.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import java.lang.IllegalStateException
import javax.inject.Inject
import kotlinx.coroutines.launch
import ru.marina.githubrepositoriesobservernew.KeyValueStorageApi
import ru.marina.githubrepositoriesobservernew.NavigatorViewProvider
import ru.marina.githubrepositoriesobservernew.detail.R
import ru.marina.githubrepositoriesobservernew.detail.RepositoryInfoUseCase
import ru.marina.githubrepositoriesobservernew.detail.databinding.FragmentDetailInfoBinding
import ru.marina.githubrepositoriesobservernew.recycler.RepositoryDetailAdapter
import ru.marina.githubrepositoriesobservernew.state.RepositoryInfoViewModelState
import ru.marina.githubrepositoriesobservernew.viewModel.RepositoryInfoViewModel
import ru.marina.githubrepositoriesobservernew.viewModel.RepositoryInfoViewModelFactory

private const val ARG_NAME_KEY_ID = "ARG_NAME_KEY_ID"
private const val ARG_OWNER_KEY_ID = "ARG_OWNER_KEY_ID"

@AndroidEntryPoint
class RepositoryInfoFragment @Inject constructor() : Fragment() {

    private var binding: FragmentDetailInfoBinding? = null
    private var viewModel: RepositoryInfoViewModel? = null

    @Inject
    lateinit var useCase: RepositoryInfoUseCase
    @Inject
    lateinit var databaseSaveToken: KeyValueStorageApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(
            this,
            RepositoryInfoViewModelFactory(
                name = arguments?.getString(ARG_NAME_KEY_ID) ?: throw IllegalStateException(""),
                owner = arguments?.getString(ARG_OWNER_KEY_ID) ?: throw IllegalStateException(""),
                useCase = useCase,
                databaseSaveToken = databaseSaveToken
            )
        )[RepositoryInfoViewModel::class.java]

        if (savedInstanceState == null) {
            viewModel?.updateRepositoryInfo()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentDetailInfoBinding.inflate(inflater, container, false)
        this.binding = binding
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = binding ?: return
// name передается не тот
        binding.nameRepository.text=arguments?.getString(ARG_NAME_KEY_ID)

        binding.logOutButton.setOnClickListener {
            val rootContainerId = (activity as? NavigatorViewProvider)?.getViewId() ?: return@setOnClickListener

            val fragmentAuth = (activity as? NavigatorViewProvider)?.getAuthUserFragment() ?: return@setOnClickListener

            requireActivity()
                .supportFragmentManager.beginTransaction()
                .replace(rootContainerId, fragmentAuth)
                .addToBackStack(null)
                .commit()
        }
        binding.arrowBack.setOnClickListener {
            val rootContainerId = (activity as? NavigatorViewProvider)?.getViewId() ?: return@setOnClickListener
            val fragmentList = (activity as? NavigatorViewProvider)?.getRepositoriesListFragment() ?: return@setOnClickListener

            requireActivity()
                .supportFragmentManager.beginTransaction()
                .replace(rootContainerId, fragmentList)
                .addToBackStack(null)
                .commit()
        }

        binding.recyclerViewInfo.layoutManager = LinearLayoutManager(context)
        lifecycleScope.launch {
            viewModel?.viewStateFlow?.collect { state ->
                when (state) {
                    is RepositoryInfoViewModelState.Error -> {
                        showOrHideErrorContainer(true)
                        showOrHideGifLoading(false)
                    }

                    RepositoryInfoViewModelState.Loading -> {
                        showOrHideErrorContainer(false)
                        showOrHideGifLoading(true)
                    }

                    is RepositoryInfoViewModelState.Success -> {
                        showOrHideErrorContainer(false)
                        showOrHideGifLoading(false)
                        binding.recyclerViewInfo.adapter = RepositoryDetailAdapter(state.itemList)
                    }
                }

            }

        }
    }

    private fun showOrHideErrorContainer(isShow: Boolean) {
        binding?.containerError?.isVisible = isShow
    }

    private fun showOrHideGifLoading(isShow: Boolean) {
        val image = binding?.imageViewLoading
        binding?.containerLoading?.isVisible = isShow
        Glide.with(this)
            .load(R.drawable.gif_louding)
            .into(image!!)
    }

    override fun onDestroy() {
        (binding?.recyclerViewInfo?.adapter as? RepositoryDetailAdapter)?.dispose()
        binding = null
        super.onDestroy()
    }

    companion object {
        fun newInstance(name: String, owner: String): RepositoryInfoFragment {
            val args = Bundle()
            args.putString(ARG_NAME_KEY_ID, name)
            args.putString(ARG_OWNER_KEY_ID, owner)
            val fragment = RepositoryInfoFragment()
            fragment.arguments = args
            return fragment
        }
    }
}