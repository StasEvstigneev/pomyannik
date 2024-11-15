package com.example.prayforthem.articledisplay.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.prayforthem.articledisplay.presentation.ArticleDisplayViewModel
import com.example.prayforthem.databinding.FragmentArticleDisplayBinding
import com.example.prayforthem.prayerdisplay.domain.models.PrayersDisplayScreenState
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ArticleDisplayFragment : Fragment() {

    private var _binding: FragmentArticleDisplayBinding? = null
    private val binding get() = _binding!!
    private val args: ArticleDisplayFragmentArgs by navArgs()
    private val viewModel: ArticleDisplayViewModel by viewModel {
        parametersOf(args.articleFileNameArg)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArticleDisplayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getScreenState().observe(viewLifecycleOwner) { state ->
            renderState(state)
        }

        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        requireActivity()
            .onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().popBackStack()
                }
            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun renderState(state: PrayersDisplayScreenState) {
        when (state) {
            is PrayersDisplayScreenState.Content -> {
                binding.apply {
                    progressBar.isVisible = false
                    articleTitle.text = state.title
                    articleText.text = state.text
                    article.isVisible = true
                    placeholder.isVisible = false
                }
            }

            is PrayersDisplayScreenState.Loading -> {
                binding.apply {
                    progressBar.isVisible = true
                    article.isVisible = false
                    placeholder.isVisible = false
                }
            }

            is PrayersDisplayScreenState.Error -> {
                binding.apply {
                    progressBar.isVisible = false
                    article.isVisible = false
                    placeholder.isVisible = true
                }
            }
        }
    }

}