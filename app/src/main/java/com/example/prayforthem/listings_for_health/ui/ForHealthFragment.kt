package com.example.prayforthem.listings_for_health.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.prayforthem.R
import com.example.prayforthem.databinding.FragmentForHealthBinding
import com.example.prayforthem.listings.domain.RecyclerViewDeleteItem
import com.example.prayforthem.listings.domain.RecyclerViewItemClick
import com.example.prayforthem.listings.domain.models.ListingScreenState
import com.example.prayforthem.listings.domain.models.ListingWithPerson
import com.example.prayforthem.listings.ui.ListingsAdapter
import com.example.prayforthem.listings.ui.ListingsFragmentDirections
import com.example.prayforthem.listings_for_health.presentation.ForHealthViewModel
import com.example.prayforthem.utils.DialogConstructor
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.androidx.viewmodel.ext.android.viewModel

open class ForHealthFragment : Fragment(), RecyclerViewItemClick<ListingWithPerson>,
    RecyclerViewDeleteItem<ListingWithPerson> {

    private var _binding: FragmentForHealthBinding? = null
    open val binding get() = _binding!!
    open val viewModel by viewModel<ForHealthViewModel>()
    private val listingsAdapter = ListingsAdapter(
        itemClickInterface = this,
        deleteItemInterface = this
    )
    private lateinit var deleteDialog: MaterialAlertDialogBuilder

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
        viewModel.getListings()
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
        val action = ListingsFragmentDirections.actionListsFragmentToListingDisplayFragment(
            isForHealthArg = item.listing.forHealth,
            listingIdArg = item.listing.listingId!!,
            listingTitleArg = item.listing.title
        )
        findNavController().navigate(action)
    }

    override fun onDeleteElementClick(item: ListingWithPerson) {
        deleteDialog = DialogConstructor.createDeleteDialog(
            context = requireContext(),
            action = { viewModel.deleteListing(item) },
            message = getString(
                R.string.are_you_sure_you_want_to_delete_list_x,
                item.listing.title
            ),
            view = binding.overlay
        )
        deleteDialog.show()
    }

    companion object {
        fun newInstance() = ForHealthFragment()
        private const val IS_FOR_HEALTH = true
    }

}