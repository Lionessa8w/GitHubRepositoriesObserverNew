package ru.marina.githubrepositoriesobservernew.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.launch
import ru.marina.githubrepositoriesobservernew.detail.R
import ru.marina.githubrepositoriesobservernew.detail.databinding.FragmentDetailInfoBinding
import ru.marina.githubrepositoriesobservernew.getNavigatorFragment
import ru.marina.githubrepositoriesobservernew.recycler.RepositoryDetailAdapter
import ru.marina.githubrepositoriesobservernew.state.RepositoryInfoViewModelState
import ru.marina.githubrepositoriesobservernew.viewModel.RepositoryDetailViewModel
import ru.marina.githubrepositoriesobservernew.viewModel.RepositoryDetailViewModelAction
import ru.marina.githubrepositoriesobservernew.viewModel.RepositoryDetailViewModelFactoryProvider


@AndroidEntryPoint
class RepositoryDetailFragment @Inject constructor() : Fragment() {

    private var binding: FragmentDetailInfoBinding? = null
    private var viewModel: RepositoryDetailViewModel? = null

    private val args: RepositoryDetailFragmentArgs by navArgs()

    @Inject
    lateinit var repositoryDetailViewModelFactoryProvider: RepositoryDetailViewModelFactoryProvider


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(
            this,
            repositoryDetailViewModelFactoryProvider.getRepositoryDetailViewModelFactory(
                name = args.name,
                owner = args.owner
            )
        )[RepositoryDetailViewModel::class.java]

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

        binding.logOutButton.setOnClickListener {
            viewModel?.clearTokenAndLogout()
        }

        binding.nameRepository.text = args.name
        binding.recyclerViewInfo.layoutManager = LinearLayoutManager(context)

        binding.arrowBack.setOnClickListener {
            activity?.onBackPressed()
        }

        Glide.with(this)
            .load(R.drawable.gif_loading)
            .into(binding.imageViewLoading)

        updateButtonError()

        lifecycleScope.launch {
            viewModel?.actionFlow?.collect {
                when (it) {
                    is RepositoryDetailViewModelAction.LogOut -> {
                        getNavigatorFragment()
                            ?.navigationRepositoriesListFragmentToAuthUserFragment()
                    }
                }
            }
        }

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

    private fun updateButtonError(){
        val binding = binding ?: return
        val buttonError= binding.buttonError
        buttonError.setOnClickListener {
            viewModel?.updateRepositoryInfo()
        }
    }

    private fun showOrHideErrorContainer(isShow: Boolean) {
        binding?.containerError?.isVisible = isShow
    }

    private fun showOrHideGifLoading(isShow: Boolean) {
        val binding = binding ?: return
        binding.containerLoading.isVisible = isShow
    }

    override fun onDestroy() {
        (binding?.recyclerViewInfo?.adapter as? RepositoryDetailAdapter)?.dispose()
        binding = null
        super.onDestroy()
    }

}