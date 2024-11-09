package com.example.prayforthem.addname.ui

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.prayforthem.R
import com.example.prayforthem.addname.presentation.AddNameViewModel
import com.example.prayforthem.databinding.FragmentAddNameBinding
import com.example.prayforthem.utils.DialogConstructor
import com.example.prayforthem.utils.NameForms
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddNameFragment : Fragment() {

    private var _binding: FragmentAddNameBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<AddNameViewModel>()
    private var showExitDialog = false
    private lateinit var exitDialog: MaterialAlertDialogBuilder

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddNameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.apply {
            setNavigationOnClickListener {
                leaveFragment()
            }
        }

        viewModel.getSaveButtonState().observe(viewLifecycleOwner) { status ->
            binding.buttonSave.isEnabled = status
        }

        viewModel.getExitDialogStatus().observe(viewLifecycleOwner) { status ->
            showExitDialog = status
        }

        binding.editTextNom.doAfterTextChanged { text: Editable? ->
            viewModel.updateName(text, NameForms.NAME_NOM)
        }

        binding.editTextGen.doAfterTextChanged { text: Editable? ->
            viewModel.updateName(text, NameForms.NAME_GEN)
        }

        binding.editTextDat.doAfterTextChanged { text: Editable? ->
            viewModel.updateName(text, NameForms.NAME_DAT)
        }

        binding.editTextAcc.doAfterTextChanged { text: Editable? ->
            viewModel.updateName(text, NameForms.NAME_ACC)
        }

        binding.editTextInst.doAfterTextChanged { text: Editable? ->
            viewModel.updateName(text, NameForms.NAME_INST)
        }

        binding.editTextPrep.doAfterTextChanged { text: Editable? ->
            viewModel.updateName(text, NameForms.NAME_PREP)
        }

        binding.buttonSave.setOnClickListener {
            viewModel.saveName()
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

}