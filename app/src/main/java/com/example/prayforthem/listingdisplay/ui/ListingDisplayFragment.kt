package com.example.prayforthem.listingdisplay.ui

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.prayforthem.R
import com.example.prayforthem.databinding.FragmentListingDisplayBinding
import com.example.prayforthem.listingdisplay.domain.models.ListingDisplayScreenState
import com.example.prayforthem.listingdisplay.presentation.ListingDisplayViewModel
import com.example.prayforthem.utils.ImageSaver
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.markodevcic.peko.PermissionRequester
import com.markodevcic.peko.PermissionResult
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ListingDisplayFragment : Fragment() {
    private var _binding: FragmentListingDisplayBinding? = null
    private val binding get() = _binding!!
    private val args: ListingDisplayFragmentArgs by navArgs()
    private val viewModel: ListingDisplayViewModel by viewModel {
        parametersOf(args.listingIdArg)
    }
    private val listingAdapter = ListingDisplayAdapter(listOf())
    private lateinit var menuBottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    private val requester = PermissionRequester.instance()

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
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        if (!binding.overlay.isVisible) showOverlay()
                    }

                    BottomSheetBehavior.STATE_DRAGGING -> {
                        binding.overlay.isVisible = true
                    }

                    BottomSheetBehavior.STATE_HIDDEN -> {
                        hideOverlay()
                    }

                    else -> {
                        binding.overlay.isVisible = true
                    }
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

        binding.shareAsImage.setOnClickListener {
            menuBottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                checkPermissions { shareListingAsJpeg() }
            } else {
                shareListingAsJpeg()
            }
        }

        binding.saveAsImage.setOnClickListener {
            menuBottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                checkPermissions { saveListingAsImage() }
            } else {
                saveListingAsImage()
            }
        }

        binding.shareIcon.setOnClickListener {
            menuBottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            showOverlay()
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

    private fun shareListingAsJpeg() {
        val imageUri = ImageSaver.getUriFromView(requireContext(), binding.listCard)
        if (imageUri == null) {
            Toast.makeText(
                requireContext(),
                getString(R.string.image_saving_failure), Toast.LENGTH_SHORT
            ).show()
        } else {
            viewModel.shareListingAsJpeg(imageUri)
        }
    }

    private fun showOverlay() {
        binding.overlay.apply {
            isVisible = true
            animation = AnimationUtils.loadAnimation(requireContext(), R.anim.overlay_fade_in)
            animate()
        }
    }

    private fun hideOverlay() {
        binding.overlay.apply {
            animation = AnimationUtils.loadAnimation(requireContext(), R.anim.overlay_fade_out)
            animate()
            isVisible = false
        }
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
            Toast.makeText(
                requireContext(),
                getString(R.string.image_saved),
                Toast.LENGTH_SHORT
            )
                .show()
        } else {
            Toast.makeText(
                requireContext(),
                getString(R.string.image_saving_failure), Toast.LENGTH_SHORT
            ).show()
        }

    }

    private fun checkPermissions(action: () -> Unit) {
        lifecycleScope.launch {
            requester.request(
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ).collect { result ->
                when (result) {
                    is PermissionResult.Granted -> {
                        action()
                    }
                    is PermissionResult.Denied.NeedsRationale -> requestPermissions()
                    is PermissionResult.Denied.DeniedPermanently -> {
                        Toast.makeText(requireContext(),
                            getString(R.string.provide_permissions), Toast.LENGTH_LONG).show()
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        intent.data = Uri.fromParts("package", requireContext().packageName, null)
                        requireContext().startActivity(intent)
                    }
                    is PermissionResult.Cancelled -> {
                        return@collect
                    }
                }

            }
        }

    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ),
            EXTERNAL_STORAGE_PERMISSION_CODE
        )
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
        private const val EXTERNAL_STORAGE_PERMISSION_CODE = 1
    }

}