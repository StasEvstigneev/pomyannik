package com.example.prayforthem.listingdisplay.ui

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.prayforthem.App
import com.example.prayforthem.R
import com.example.prayforthem.databinding.FragmentListingDisplayBinding
import com.example.prayforthem.listingdisplay.domain.models.ListingDisplayScreenState
import com.example.prayforthem.listingdisplay.presentation.ListingDisplayViewModel
import com.example.prayforthem.utils.ImageSaver
import com.google.android.material.bottomsheet.BottomSheetBehavior
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
    private lateinit var menuBottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListingDisplayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.title = args.listingTitleArg
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

        val menuBottomSheetContainer = binding.bottomSheet
        menuBottomSheetBehavior = BottomSheetBehavior.from(menuBottomSheetContainer).apply {
            state = BottomSheetBehavior.STATE_HIDDEN
        }
        menuBottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_COLLAPSED -> binding.overlay.isVisible = true
                    else -> binding.overlay.isVisible = false
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) = Unit

        })

        binding.editIcon.setOnClickListener {
            val action = ListingDisplayFragmentDirections
                .actionListingDisplayFragmentToEditListingFragment(args.listingIdArg)
            findNavController().navigate(action)
        }

        binding.shareAsText.setOnClickListener {
            menuBottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
            shareListingAsText()
        }

        binding.saveAsImage.setOnClickListener {
            saveListingAsImage()
            menuBottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        }

        binding.shareIcon.setOnClickListener {
            menuBottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }

    }

    private fun renderState(state: ListingDisplayScreenState) {
        when (state) {
            is ListingDisplayScreenState.Loading -> true
            is ListingDisplayScreenState.Content -> {
                binding.toolbar.title = state.listingTitle
                listingAdapter.list = state.list
                listingAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun shareListingAsText() {
        val title = binding.title.text.toString()
        val names = listingAdapter.list
        viewModel.shareListingAsText(title, names)
    }

    private fun saveListingAsImage() {
        if (
            ImageSaver
                .saveViewAsJpeg(
                    context = requireContext(),
                    view = binding.listCard,
                    fileName = args.listingTitleArg
                )
        ) {
            Toast.makeText(requireContext(), getString(R.string.image_saved), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(),
                getString(R.string.image_saving_failure), Toast.LENGTH_SHORT).show()
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

}