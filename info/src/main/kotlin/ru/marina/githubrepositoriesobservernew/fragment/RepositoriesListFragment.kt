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
import javax.inject.Inject
import kotlinx.coroutines.launch
import ru.marina.githubrepositoriesobservernew.NavigatorActivity
import ru.marina.githubrepositoriesobservernew.info.R
import ru.marina.githubrepositoriesobservernew.info.databinding.FragmentRepositoriesListBinding
import ru.marina.githubrepositoriesobservernew.recycler.RepositoriesListAdapter
import ru.marina.githubrepositoriesobservernew.state.RepositoriesListViewModelState
import ru.marina.githubrepositoriesobservernew.viewModel.RepositoriesListViewModel

@AndroidEntryPoint
class RepositoriesListFragment @Inject constructor() : Fragment() {

    private var binding: FragmentRepositoriesListBinding? = null
    private var viewModel: RepositoriesListViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[RepositoriesListViewModel::class.java]
        if (savedInstanceState == null) {
            viewModel!!.updateRepositoriesList()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentRepositoriesListBinding.inflate(inflater, container, false)
        this.binding = binding
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = binding ?: return

        binding.repositoriesListRecycler.layoutManager = LinearLayoutManager(context)

        binding.logOutButton.setOnClickListener {
            // todo добавить логику через вьбмодельку и через акшианалы что чистился токен
            getNavigationHostFragment(this)?.navigationHostFragmentToAuthUserFragment()
        }

        lifecycleScope.launch {
            viewModel?.viewStateFlow?.collect { state ->
                when (state) {
                    is RepositoriesListViewModelState.Error -> {
                        showOrHideErrorContainer(true)
                        showOrHideGifLoading(false)
                    }

                    RepositoriesListViewModelState.Loading -> {
                        showOrHideErrorContainer(false)
                        showOrHideGifLoading(true)
                    }

                    is RepositoriesListViewModelState.Success -> {
                        showOrHideGifLoading(false)
                        showOrHideErrorContainer(false)
                        binding.repositoriesListRecycler.adapter =
                            RepositoriesListAdapter(
                                state.repositoriesModelList,
                                onCardClicked = { name, owner ->
                                    getNavigationHostFragment(
                                        this@RepositoriesListFragment
                                    )?.navigationHostFragmentToRepositoryInfoFragment(name, owner)
                                })
                    }
                }
            }
        }
    }

    private tailrec fun getNavigationHostFragment(fragment: Fragment): NavigatorActivity? {
        val parentFragment = fragment.parentFragment ?: return null
        if (parentFragment is NavigatorActivity) return parentFragment
        return getNavigationHostFragment(parentFragment)
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
        binding = null
        super.onDestroy()
    }
}