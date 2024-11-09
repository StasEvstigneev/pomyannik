package com.example.prayforthem.createlisting.ui

import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.prayforthem.R
import com.example.prayforthem.createlisting.domain.CreateListScreenState
import com.example.prayforthem.createlisting.presentation.CreateListingViewModel
import com.example.prayforthem.databinding.FragmentCreateListingBinding
import com.example.prayforthem.utils.Constants
import com.example.prayforthem.utils.DialogConstructor
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreateListingFragment : Fragment(), TempPersonClickInterface {

    private var _binding: FragmentCreateListingBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<CreateListingViewModel>()
    private val personAdapter = TempPersonListingAdapter(
        ArrayList(),
        this
    )
    private var isForHealth: Boolean = true
    private lateinit var listType: String

    private var showExitDialog = false
    private lateinit var exitDialog: MaterialAlertDialogBuilder

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateListingBinding.inflate(inflater, container, false)
        isForHealth = CreateListingFragmentArgs.fromBundle(requireArguments()).isForHealthArg
        listType =
            if (isForHealth) getString(R.string.for_the_health).lowercase()
            else getString(R.string.for_the_repose).lowercase()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setListType(isForHealth)
        binding.toolbar.apply {
            title = getString(R.string.new_list, listType)
            setNavigationOnClickListener {
                leaveFragment()
            }
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

        viewModel.getScreenState().observe(viewLifecycleOwner) { state ->
            renderState(state)
        }

        viewModel.getSaveButtonState().observe(viewLifecycleOwner) { canSave ->
            binding.buttonSave.isEnabled = canSave
        }

        viewModel.getExitDialogStatus().observe(viewLifecycleOwner) { status ->
            showExitDialog = status
        }

        binding.editText.doAfterTextChanged { text: Editable? ->
            if (text.isNullOrBlank()) {
                viewModel.updateListTitle("")
            } else {
                viewModel.updateListTitle(text.toString())
            }
        }

        binding.buttonAddName.setOnClickListener {
            findNavController().navigate(R.id.action_createListFragment_to_namesFragment)
        }

        binding.buttonSave.setOnClickListener {
            viewModel.saveList()
            findNavController().popBackStack()
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

    private fun leaveFragment() {
        if (showExitDialog) {
            binding.overlay.isVisible = true
            exitDialog.show()
        } else {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        setFragmentResultListener(Constants.REQUEST_PERSON_KEY) { _, bundle ->
            val dignityId = bundle.getInt(Constants.DIGNITY_KEY)
            val nameId = bundle.getInt(Constants.NAME_KEY)

            Log.d("RECEIVED DIG_id FRAGMENT", "$dignityId")
            Log.d("RECEIVED NAME_id FRAGMENT", "$nameId")

            if (dignityId != NULL) {
                viewModel.createNewPersonBasicData(dignityId, nameId)
            } else {
                viewModel.createNewPersonBasicData(null, nameId)

            }
            parentFragmentManager.clearFragmentResult(Constants.REQUEST_PERSON_KEY)
        }
    }

    private fun renderState(state: CreateListScreenState) {
        when (state) {
            is CreateListScreenState.Loading -> binding.apply {
                recyclerView.isVisible = false
                progressBar.isVisible = true
            }

            is CreateListScreenState.Content -> binding.apply {
                progressBar.isVisible = false
                recyclerView.isVisible = true
                infoText.text = getString(R.string.added_n_of_ten, state.listSize.toString())
                buttonAddName.isEnabled = !state.isListFull
                personAdapter.list = state.list
                personAdapter.notifyDataSetChanged()

                showExitDialog = (state.listSize > NULL || binding.editText.text.isNullOrEmpty())
            }
        }
    }

    override fun onTrashBinClick(position: Int) {
        viewModel.removePersonFromList(position)
    }

    companion object {
        private const val NULL = 0
    }

}