package ru.marina.githubrepositoriesobservernew.fragment

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.marina.githubrepositoriesobservernew.auth.R
import ru.marina.githubrepositoriesobservernew.auth.databinding.FragmentAuthBinding
import ru.marina.githubrepositoriesobservernew.getNavigatorFragment
import ru.marina.githubrepositoriesobservernew.state.AuthUserTokenViewModelState
import ru.marina.githubrepositoriesobservernew.viewModel.AuthViewModel

private const val LAST_TOKEN_INPUT = "LAST_TOKEN_INPUT"

@AndroidEntryPoint
class AuthUserFragment : Fragment(), OnEditorActionListener {

    private var binding: FragmentAuthBinding? = null

    private var authViewModel: AuthViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentAuthBinding.inflate(inflater, container, false)
        this.binding = binding
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = binding ?: return
        if (savedInstanceState != null) {
            binding.inputToken.setText(savedInstanceState.getString(LAST_TOKEN_INPUT) ?: "")
        }

        Glide.with(this)
            .load(R.drawable.github_bleu_png)
            .into(binding.logoGit)

        binding.buttonSingIn.setOnClickListener {
            val authViewModel = this.authViewModel ?: return@setOnClickListener
            val inputToken = binding.inputToken

            authViewModel.tryAuth(inputToken.text.toString())
            inputToken.setOnEditorActionListener(this)
            inputToken.setImeActionLabel("GO", EditorInfo.IME_ACTION_DONE)


        }
        observeViewModelState()

    }

    private fun observeViewModelState() {
        lifecycleScope.launch {
            authViewModel?.viewStateFlow?.collect { state ->
                when (state) {
                    is AuthUserTokenViewModelState.ErrorEmptyToken -> {
                        showOrHideGifLoading(false)
//                        Toast.makeText(context, "Введите токен", Toast.LENGTH_SHORT).show()
                        binding?.textError?.text=R.string.input_token.toString()
                    }

                    AuthUserTokenViewModelState.Idle -> {
                        showOrHideGifLoading(false)
                    }

                    AuthUserTokenViewModelState.Loading -> {
                        showOrHideGifLoading(true)

                    }

                    is AuthUserTokenViewModelState.Success -> {
                        getNavigatorFragment()?.navigationAuthFragmentToRepositoriesListFragment()
                        binding?.buttonSingIn?.isClickable = false
                    }

                    is AuthUserTokenViewModelState.ErrorInternet -> {
                        showOrHideGifLoading(false)
//                        Toast.makeText(context, "Нет подключения к сети", Toast.LENGTH_SHORT).show()
                        binding?.textError?.text=R.string.no_internet.toString()
                    }

                    is AuthUserTokenViewModelState.Error ->{}
                }
            }
        }
    }

    private fun showOrHideGifLoading(isShow: Boolean) {
        val binding = binding ?: return
        val image = binding.imageViewLoading
        binding.containerLoading.isVisible = isShow
        Glide.with(this)
            .load(R.drawable.gif_loading)
            .into(image)

    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(LAST_TOKEN_INPUT, binding?.inputToken?.text.toString())
        super.onSaveInstanceState(outState)
    }

    override fun onDestroyView() {
        authViewModel = null
        binding = null
        super.onDestroyView()
    }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        if (actionId == EditorInfo.IME_ACTION_DONE || actionId == R.id.input_token) {
            getNavigatorFragment()?.navigationAuthFragmentToRepositoriesListFragment()
            return true
        }
        return false
    }
}