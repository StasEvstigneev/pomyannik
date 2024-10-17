package com.example.prayforthem.listings_for_health.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.prayforthem.databinding.FragmentForHealthBinding
import com.example.prayforthem.listings.RecyclerViewClickInterface
import com.example.prayforthem.listings.domain.models.ListingScreenState
import com.example.prayforthem.listings.domain.models.ListingWithPerson
import com.example.prayforthem.listings.ui.ListingsAdapter
import com.example.prayforthem.listings.ui.ListingsFragmentDirections
import com.example.prayforthem.listings_for_health.presentation.ForHealthViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

open class ForHealthFragment : Fragment(), RecyclerViewClickInterface<ListingWithPerson> {

    private var _binding: FragmentForHealthBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<ForHealthViewModel>()
    private val listingsAdapter = ListingsAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentForHealthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.apply {
            adapter = listingsAdapter
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
        }

        binding.fab.setOnClickListener {
            navigate()
        }

        viewModel.getScreenState().observe(viewLifecycleOwner) { state ->
            renderState(state)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        viewModel.getListingsForHealth()
    }

    private fun renderState(state: ListingScreenState) {
        when (state) {
            is ListingScreenState.Loading -> {
                binding.apply {
                    recyclerView.isVisible = false
                    placeholder.isVisible = false
                    progressBar.isVisible = true
                }
            }

            is ListingScreenState.Placeholder -> {
                binding.apply {
                    recyclerView.isVisible = false
                    placeholder.isVisible = true
                    progressBar.isVisible = false
                }
            }

            is ListingScreenState.Content -> {
                listingsAdapter.submitList(state.listing)
                binding.apply {
                    recyclerView.isVisible = true
                    placeholder.isVisible = false
                    progressBar.isVisible = false
                }
            }
        }

    }

    private fun navigate() {
        val action = ListingsFragmentDirections.actionListsFragmentToCreateListFragment(
            isForHealthArg = IS_FOR_HEALTH
        )
        findNavController().navigate(action)
    }

    override fun onItemClick(item: ListingWithPerson) {
        TODO("Not yet implemented")
    }

    override fun onDeleteElementClick(item: ListingWithPerson) {
        viewModel.deleteListing(item)
    }

    companion object {
        fun newInstance() = ForHealthFragment()
        private const val IS_FOR_HEALTH = true
    }


}