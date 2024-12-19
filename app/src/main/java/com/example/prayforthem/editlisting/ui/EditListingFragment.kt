package com.example.prayforthem.editlisting.ui

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.prayforthem.R
import com.example.prayforthem.databinding.FragmentEditListingBinding
import com.example.prayforthem.editlisting.domain.EditListingScreenState
import com.example.prayforthem.editlisting.presentation.EditListingViewModel
import com.example.prayforthem.listings.domain.RecyclerViewDeleteItem
import com.example.prayforthem.listings.domain.models.PersonDignityName
import com.example.prayforthem.utils.Constants
import com.example.prayforthem.utils.DialogConstructor
import com.example.prayforthem.utils.NameFormsConstructor
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class EditListingFragment : Fragment(), RecyclerViewDeleteItem<PersonDignityName> {

    private var _binding: FragmentEditListingBinding? = null
    private val binding get() = _binding!!
    private val args: EditListingFragmentArgs by navArgs()
    private val viewModel: EditListingViewModel by viewModel {
        parametersOf(args.listingIdArg)
    }
    private var showExitDialog = false
    private lateinit var exitDialog: MaterialAlertDialogBuilder
    private lateinit var deleteDialog: MaterialAlertDialogBuilder
    private val personAdapter = EditListingAdapter(this)
    private var isClickAllowed = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditListingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getScreenState().observe(viewLifecycleOwner) { state ->
            renderState(state)
        }

        viewModel.getSaveButtonState().observe(viewLifecycleOwner) { status ->
            binding.buttonSave.isEnabled = status
        }

        viewModel.getExitDialogStatus().observe(viewLifecycleOwner) { status ->
            showExitDialog = status
        }

        binding.toolbar.setNavigationOnClickListener {
            leaveFragment()
        }

        binding.recyclerView.apply {
            adapter = personAdapter
            layoutManager =
                LinearLayoutManager(
                    requireContext(),
                    LinearLayoutManager.VERTICAL,
                    false
                )
        }

        binding.editText.doAfterTextChanged { text: Editable? ->
            viewModel.updateTitle(if (text.isNullOrBlank()) "" else text.toString())
        }

        binding.buttonAddName.setOnClickListener {
            findNavController().navigate(R.id.action_editListingFragment_to_namesFragment)
        }

        binding.buttonSave.setOnClickListener {
            if (isClickAllowed) {
                isClickAllowed = false
                viewModel.updateListing()
                findNavController().popBackStack()
            }
        }

        exitDialog = DialogConstructor
            .createExitDialog(
                context = requireContext(),
                action = { findNavController().popBackStack() },
                message = getString(R.string.are_you_sure_you_want_to_leave),
                view = binding.overlay
            )

        requireActivity()
            .onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    leaveFragment()
                }
            })

    }

    override fun onResume() {
        super.onResume()
        isClickAllowed = true
        setFragmentResultListener(Constants.REQUEST_PERSON_KEY) { _, bundle ->
            val dignityIdArg = bundle.getInt(Constants.DIGNITY_KEY)
            val nameIdArg = bundle.getInt(Constants.NAME_KEY)

            viewModel.addPersonToList(
                dignityId = if (dignityIdArg != Constants.NULL) dignityIdArg else null,
                nameId = nameIdArg
            )
            parentFragmentManager.clearFragmentResult(Constants.REQUEST_PERSON_KEY)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun renderState(state: EditListingScreenState) {
        when (state) {
            is EditListingScreenState.Loading -> {
                binding.apply {
                    progressBar.isVisible = true
                    recyclerView.isVisible = false
                }
            }

            is EditListingScreenState.InitialContent -> {
                personAdapter.submitList(state.list)
                binding.apply {
                    progressBar.isVisible = false
                    recyclerView.isVisible = true
                    editText.setText(state.title)
                    infoText.text = getString(R.string.added_n_of_ten, state.list.size.toString())
                    buttonAddName.isEnabled = !state.isListFull
                }
            }

            is EditListingScreenState.UpdatedContent -> {
                personAdapter.submitList(state.list)
                personAdapter.notifyDataSetChanged()
                if (state.list.size > ZERO) {
                    binding.recyclerView.smoothScrollToPosition(state.list.size - ONE)
                }
                binding.apply {
                    progressBar.isVisible = false
                    recyclerView.isVisible = true
                    infoText.text = getString(R.string.added_n_of_ten, state.list.size.toString())
                    buttonAddName.isEnabled = !state.isListFull
                }
            }
        }
    }

    private fun leaveFragment() {
        if (showExitDialog) {
            binding.overlay.isVisible = true
            exitDialog.show()
        } else {
            findNavController().popBackStack()
        }
    }

    override fun onDeleteElementClick(item: PersonDignityName) {
        deleteDialog = DialogConstructor.createDeleteDialog(
            context = requireContext(),
            action = { viewModel.deleteItemFromList(item) },
            view = binding.overlay,
            message = getString(
                R.string.are_you_sure_you_want_to_delete_person_x,
                NameFormsConstructor.createPersonDisplay(item)
            )
        )
        deleteDialog.show()
    }

    companion object {
        private const val ZERO = 0
        private const val ONE = 1
    }

}