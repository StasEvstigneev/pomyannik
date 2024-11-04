package com.example.prayforthem.prayerscategories.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.prayforthem.databinding.FragmentPrayersCategoriesBinding
import com.example.prayforthem.listings.domain.RecyclerViewItemClick
import com.example.prayforthem.prayerscategories.domain.models.PrayerCategory
import com.example.prayforthem.prayerscategories.domain.models.PrayersCategoriesScreenState
import com.example.prayforthem.prayerscategories.presentation.PrayersCategoriesViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class PrayersCategoriesFragment : Fragment(), RecyclerViewItemClick<PrayerCategory> {

    private var _binding: FragmentPrayersCategoriesBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<PrayersCategoriesViewModel>()
    private val categoriesAdapter = PrayerCategoriesAdapter(arrayListOf(), this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPrayersCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getScreenState().observe(viewLifecycleOwner) { state ->
            renderState(state)
        }

        binding.recyclerView.apply {
            adapter = categoriesAdapter
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun renderState(state: PrayersCategoriesScreenState) {
        when (state) {
            is PrayersCategoriesScreenState.Loading -> {
                binding.apply {
                    progressBar.isVisible = true
                    recyclerView.isVisible = false
                    placeholder.isVisible = false
                }
            }

            is PrayersCategoriesScreenState.Content -> {
                binding.apply {
                    categoriesAdapter.list = state.list as ArrayList<PrayerCategory>
                    progressBar.isVisible = false
                    recyclerView.isVisible = true
                    placeholder.isVisible = false
                }
            }

            is PrayersCategoriesScreenState.Error -> {
                binding.apply {
                    progressBar.isVisible = false
                    recyclerView.isVisible = false
                    placeholder.isVisible = true
                }
            }
        }
    }

    override fun onItemClick(item: PrayerCategory) {
        val action = PrayersCategoriesFragmentDirections
            .actionPrayersCategoriesFragmentToPrayersFragment(
                categoryIdArg = item.categoryId
            )
        findNavController().navigate(action)
    }

}