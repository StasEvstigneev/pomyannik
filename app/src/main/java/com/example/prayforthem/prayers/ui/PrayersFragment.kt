package com.example.prayforthem.prayers.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.prayforthem.databinding.FragmentPrayersBinding
import com.example.prayforthem.listings.RecyclerViewClickInterface
import com.example.prayforthem.prayers.domain.models.Prayer
import com.example.prayforthem.prayers.domain.models.PrayersScreenState
import com.example.prayforthem.prayers.presentation.PrayersViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class PrayersFragment : Fragment(), RecyclerViewClickInterface<Prayer> {

    private var _binding: FragmentPrayersBinding? = null
    private val binding get() = _binding!!
    private val args: PrayersFragmentArgs by navArgs()
    private val viewModel: PrayersViewModel by viewModel {
        parametersOf(args.categoryIdArg)
    }
    private val prayersAdapter = PrayersAdapter(arrayListOf(), this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPrayersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getScreenState().observe(viewLifecycleOwner) { state ->
            renderState(state)
        }

        binding.toolbar.setNavigationOnClickListener {
            navigateBack()
        }

        binding.recyclerView.apply {
            adapter = prayersAdapter
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
        }

        requireActivity()
            .onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    navigateBack()
                }
            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun renderState(state: PrayersScreenState) {
        when (state) {
            is PrayersScreenState.Loading -> {
                binding.apply {
                    progressBar.isVisible = true
                    recyclerView.isVisible = false
                    placeholder.isVisible = false
                }
            }

            is PrayersScreenState.Content -> {
                binding.apply {
                    prayersAdapter.list = state.list as ArrayList<Prayer>
                    progressBar.isVisible = false
                    recyclerView.isVisible = true
                    placeholder.isVisible = false
                }
            }

            is PrayersScreenState.Error -> {
                binding.apply {
                    progressBar.isVisible = false
                    recyclerView.isVisible = false
                    placeholder.isVisible = true
                }
            }
        }
    }

    private fun navigateBack() {
        findNavController().popBackStack()
    }


    override fun onItemClick(item: Prayer) {
        TODO("Not yet implemented")
    }

    override fun onDeleteElementClick(item: Prayer) = Unit


}