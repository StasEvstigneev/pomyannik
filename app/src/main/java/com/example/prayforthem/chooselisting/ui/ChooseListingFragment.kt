package com.example.prayforthem.chooselisting.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.prayforthem.chooselisting.domain.ChooseListingScreenState
import com.example.prayforthem.chooselisting.presentation.ChooseListingViewModel
import com.example.prayforthem.databinding.FragmentChooseListingBinding
import com.example.prayforthem.listings.domain.models.ListingWithPerson
import com.example.prayforthem.utils.Constants
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ChooseListingFragment : Fragment(), RecyclerViewCheckboxInterface<ListingWithPerson> {

    private var _binding: FragmentChooseListingBinding? = null
    private val binding get() = _binding!!
    private val args: ChooseListingFragmentArgs by navArgs()
    private val viewModel: ChooseListingViewModel by viewModel {
        parametersOf(args.forHealthArg)
    }
    private val listingsAdapter = ListingsCheckboxAdapter(arrayListOf(), this)
    private val listingIds: ArrayList<Int> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChooseListingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setNavigationOnClickListener {
            //добавить диалог перед закрытием, если listingIds.isNotEmpty
            findNavController().popBackStack()
        }

        binding.recyclerView.apply {
            adapter = listingsAdapter
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
        }

        viewModel.getScreenState().observe(viewLifecycleOwner) { state ->
            renderState(state)
        }

        viewModel.getChosenListings().observe(viewLifecycleOwner) { ids ->
            binding.buttonChoose.isEnabled = ids.isNotEmpty()
            processChosenIds(ids)
            listingsAdapter.updateSelectedLists(ids)
        }

        binding.buttonChoose.setOnClickListener {
            setFragmentResult(
                Constants.SELECTED_LISTINGS_KEY,
                bundleOf(
                    Constants.LISTINGS_IDS to listingIds
                )
            )
            findNavController().popBackStack()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun renderState(state: ChooseListingScreenState) {
        when (state) {
            is ChooseListingScreenState.Loading -> {
                binding.apply {
                    progressBar.isVisible = true
                    recyclerView.isVisible = false
                    placeholder.isVisible = false
                }
            }

            is ChooseListingScreenState.Content -> {
                binding.apply {
                    progressBar.isVisible = false
                    placeholder.isVisible = false
                    recyclerView.isVisible = true
                }
                listingsAdapter.list = state.list as ArrayList<ListingWithPerson>
                listingsAdapter.notifyDataSetChanged()
            }

            is ChooseListingScreenState.Placeholder -> {
                binding.apply {
                    progressBar.isVisible = false
                    recyclerView.isVisible = false
                    placeholder.isVisible = true
                }
            }
        }
    }

    private fun processChosenIds(ids: ArrayList<Int>) {
        listingIds.apply {
            clear()
            addAll(ids)
        }
    }

    override fun onCheckBoxClick(item: ListingWithPerson, isChecked: Boolean) {
        viewModel.processCheckBoxClick(item.listing.listingId!!, isChecked)
    }

}