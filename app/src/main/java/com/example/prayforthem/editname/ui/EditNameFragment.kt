package com.example.prayforthem.editname.ui

import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.prayforthem.R
import com.example.prayforthem.addname.ui.AddNameFragment
import com.example.prayforthem.editname.presentation.EditNameViewModel
import com.example.prayforthem.utils.DialogConstructor
import com.example.prayforthem.utils.NameForms
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class EditNameFragment : AddNameFragment() {

    private val args: EditNameFragmentArgs by navArgs()
    override val viewModel: EditNameViewModel by viewModel {
        parametersOf(args.nameIdArg)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.apply {
            title = getString(R.string.edit_name)
            setNavigationOnClickListener {
                leaveFragment()
            }
        }

        binding.buttonSave.text = getString(R.string.update)

        viewModel.getSaveButtonState().observe(viewLifecycleOwner) { status ->
            binding.buttonSave.isEnabled = status
            Log.d("UPDATED_NAME", "Save button = $status")
        }

        viewModel.getExitDialogStatus().observe(viewLifecycleOwner) { status ->
            showExitDialog = status
            Log.d("UPDATED_NAME", "Exit dialog = $status")
        }

        viewModel.getNameData().observe(viewLifecycleOwner) { name ->
            binding.apply {
                textInputLayoutNom.editText?.setText(name.nameNominative)
                textInputLayoutGen.editText?.setText(name.nameGenitive)
                textInputLayoutDat.editText?.setText(name.nameDative)
                textInputLayoutAcc.editText?.setText(name.nameAccusative)
                textInputLayoutInst.editText?.setText(name.nameInstrumental)
                textInputLayoutPrep.editText?.setText(name.namePrepositional)
            }
        }

        binding.editTextNom.doAfterTextChanged { text: Editable? ->
            viewModel.updateName(text, NameForms.NAME_NOMINATIVE)
        }

        binding.editTextGen.doAfterTextChanged { text: Editable? ->
            viewModel.updateName(text, NameForms.NAME_GENITIVE)
        }

        binding.editTextDat.doAfterTextChanged { text: Editable? ->
            viewModel.updateName(text, NameForms.NAME_DATIVE)
        }

        binding.editTextAcc.doAfterTextChanged { text: Editable? ->
            viewModel.updateName(text, NameForms.NAME_ACCUSATIVE)
        }

        binding.editTextInst.doAfterTextChanged { text: Editable? ->
            viewModel.updateName(text, NameForms.NAME_INSTRUMENTAL)
        }

        binding.editTextPrep.doAfterTextChanged { text: Editable? ->
            viewModel.updateName(text, NameForms.NAME_PREPOSITIONAL)
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


}