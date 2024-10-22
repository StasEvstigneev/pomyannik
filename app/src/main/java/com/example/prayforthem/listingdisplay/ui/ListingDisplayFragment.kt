package com.example.prayforthem.listingdisplay.ui

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.prayforthem.R
import com.example.prayforthem.databinding.FragmentListingDisplayBinding
import com.example.prayforthem.listingdisplay.domain.ListingDisplayScreenState
import com.example.prayforthem.listingdisplay.presentation.ListingDisplayViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ListingDisplayFragment : Fragment() {

    private var _binding: FragmentListingDisplayBinding? = null
    private val binding get() = _binding!!
    private val args: ListingDisplayFragmentArgs by navArgs()
    private val viewModel: ListingDisplayViewModel by viewModel {
        parametersOf(args.isForHealthArg, args.listingIdArg)
    }
    private val listingAdapter = ListingDisplayAdapter(listOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListingDisplayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.title = getString(R.string.list, args.listingTitleArg)
        if (!args.isForHealthArg) {
            val typedValue = TypedValue()
            val theme = requireContext().theme
            theme.resolveAttribute(
                com.google.android.material.R.attr.textAppearanceBody1,
                typedValue,
                true
            )
            val color = typedValue.data

            binding.apply {
                listCard.setBackgroundResource(R.drawable.list_disp_bg_black)
                title.text = getString(R.string.for_the_repose)
                cross.setImageResource(R.drawable.cross_orthodox_black)
                divider.setImageResource(R.drawable.divider_ornament_black)
                title.setTextColor(color)
            }
        }

        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.apply {
            recyclerView.adapter = listingAdapter
            recyclerView.layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
        }

        viewModel.getScreenState().observe(viewLifecycleOwner) { state ->
            renderState(state)

        }
    }

    private fun renderState(state: ListingDisplayScreenState) {
        when (state) {
            is ListingDisplayScreenState.Loading -> true
            is ListingDisplayScreenState.Content -> {
                listingAdapter.list = state.list
                listingAdapter.notifyDataSetChanged()

            }

        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getListing()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

    }
}