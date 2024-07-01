package ru.marina.githubrepositoriesobservernew.fragment

import android.graphics.PorterDuff
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.TextView.OnEditorActionListener
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
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
class AuthUserFragment : Fragment() {

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

        setupInputTokenEditText()

        Glide.with(this)
            .load(R.drawable.logo_git)
            .into(binding.logoGit)

        Glide.with(this)
            .load(R.drawable.gif_loading)
            .into(binding.imageViewLoading)

        binding.buttonSingIn.setOnClickListener {
            logIn()
        }

        observeViewModelState()
    }

    private fun logIn() {
        val binding = binding ?: return
        val authViewModel = this.authViewModel ?: return
        val inputToken = binding.inputToken.text.toString()
        if (authViewModel.validate(inputToken).not()) {
            return
        }
        authViewModel.tryAuth(inputToken)
    }

    private fun setupInputTokenEditText() {
        val binding = binding ?: return
        val inputToken = binding.inputToken
        inputToken.setOnEditorActionListener(object : OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    logIn()
                    return false
                }
                return false
            }
        })

        inputToken.doOnTextChanged { _, _, _, _ ->
            authViewModel?.clearErrorState()
        }
    }

    private fun observeViewModelState() {
        val binding = binding ?: return
        val errorText = binding.textError

        lifecycleScope.launch {
            authViewModel?.viewStateFlow?.collect { state ->
                when (state) {
                    is AuthUserTokenViewModelState.ErrorEmptyToken -> {
                        showOrHideGifLoading(false)
                        errorText.isVisible = true
                        errorText.text = getString(R.string.input_token)
                        setColorError(true)
                    }

                    AuthUserTokenViewModelState.Idle -> {
                        showOrHideGifLoading(false)
                        errorText.isVisible = false
                        setColorError(false)
                    }

                    AuthUserTokenViewModelState.Loading -> {
                        showOrHideGifLoading(true)
                        errorText.isVisible = false
                        setColorError(false)
                    }

                    is AuthUserTokenViewModelState.Success -> {
                        getNavigatorFragment()?.navigationAuthFragmentToRepositoriesListFragment()
                    }

                    is AuthUserTokenViewModelState.ErrorInternet -> {
                        showOrHideGifLoading(false)
                        errorText.isVisible = true
                        errorText.text = getString(R.string.no_internet)
                        setColorError(true)
                    }

                    is AuthUserTokenViewModelState.Error -> {
                        showOrHideGifLoading(false)
                        errorText.isVisible = true
                        errorText.text = getString(R.string.unknown_error)
                        setColorError(true)
                    }

                    AuthUserTokenViewModelState.ErrorToken -> {
                        showOrHideGifLoading(false)
                        errorText.isVisible = true
                        errorText.text = getString(R.string.bad_credentials)
                        setColorError(true)
                    }
                }
            }
        }
    }

    private fun setColorError(isShow: Boolean) {
        val binding = binding ?: return
        val inputToken = binding.inputToken
        val hint = binding.hint
        val textColor = if (isShow) R.color.red else R.color.blue
        inputToken.background.mutate().setColorFilter(
            resources.getColor(textColor),
            PorterDuff.Mode.SRC_ATOP
        )
        hint.setTextColor(resources.getColor(textColor))
    }

    private fun showOrHideGifLoading(isShow: Boolean) {
        val binding = binding ?: return
        binding.containerLoading.isVisible = isShow
    }

    override fun onSaveInstanceState(outState: Bundle) {
        val binding = binding ?: return
        outState.putString(LAST_TOKEN_INPUT, binding.inputToken.text.toString())
        super.onSaveInstanceState(outState)
    }

    override fun onDestroyView() {
        authViewModel = null
        binding = null
        super.onDestroyView()
    }
}