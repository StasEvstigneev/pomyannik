package com.example.prayforthem.names.ui

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.example.prayforthem.R
import com.example.prayforthem.databinding.FragmentNamesBinding
import com.example.prayforthem.names.domain.models.DignityBasicData
import com.example.prayforthem.names.domain.models.NameBasicData
import com.example.prayforthem.names.domain.models.NamesScreenState
import com.example.prayforthem.names.presentation.NamesViewModel
import com.example.prayforthem.utils.Constants
import com.example.prayforthem.utils.DialogConstructor
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.androidx.viewmodel.ext.android.viewModel

class NamesFragment : Fragment() {

    private var _binding: FragmentNamesBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<NamesViewModel>()

    private lateinit var dignityAdapter: CustomArrayAdapter<DignityBasicData>
    private lateinit var namesAdapter: CustomArrayAdapter<NameBasicData>

    private var selectedDignity: DignityBasicData? = null
    private var selectedName: NameBasicData? = null

    private lateinit var exitDialog: MaterialAlertDialogBuilder
    private var showExitDialog = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNamesBinding.inflate(inflater, container, false)

        dignityAdapter = CustomArrayAdapter(requireContext(), listOf())
        binding.inputDignity.setAdapter(dignityAdapter)

        namesAdapter = CustomArrayAdapter(requireContext(), listOf())
        binding.inputName.setAdapter(namesAdapter)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.apply {
            setNavigationOnClickListener {
                leaveFragment()
            }
        }

        viewModel.getScreenState().observe(viewLifecycleOwner) { state ->
            renderState(state)
        }

        viewModel.getSelectedDignity().observe(viewLifecycleOwner) { dignity ->
            selectedDignity = dignity
            showExitDialog = (dignity != null)
        }

        viewModel.getSelectedName().observe(viewLifecycleOwner) { name ->
            selectedName = name
            binding.buttonSave.isEnabled = (name != null)
            showExitDialog = (name != null)
        }

        binding.inputDignity.apply {
            setDropDownBackgroundDrawable(ColorDrawable(Color.WHITE))

            setOnItemClickListener { parent, view, position, id ->
                val dignity = parent.getItemAtPosition(position) as DignityBasicData
                selectedDignity = dignity
                viewModel.updateSelectedDignity(dignity)
                hideKeyboard()
            }

            doAfterTextChanged { text ->
                viewModel.updateSelectedDignity(null)

                if (text.isNullOrEmpty()) {
                    viewModel.updateSelectedDignity(null)
                }
            }
        }

        binding.inputName.apply {
            setDropDownBackgroundDrawable(ColorDrawable(Color.WHITE))

            setOnItemClickListener { parent, view, position, id ->
                val name = parent.getItemAtPosition(position) as NameBasicData
                selectedName = name
                viewModel.updateSelectedName(name)
                hideKeyboard()
            }

            addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) = Unit

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(s: Editable?) {
                    binding.placeholderNoNames.isVisible = (adapter.count == 0)
                    viewModel.updateSelectedName(null)
                    if (s.isNullOrEmpty()) {
                        viewModel.updateSelectedName(null)
                    }
                }
            })

        }

        binding.buttonAddNewName.setOnClickListener {
            findNavController()
                .navigate(R.id.action_namesFragment_to_addNameFragment)
        }

        binding.buttonSave.setOnClickListener {
            val dignityId = if (selectedDignity != null) selectedDignity!!.dignityId else null
            setFragmentResult(
                Constants.REQUEST_PERSON_KEY,
                bundleOf(
                    Constants.DIGNITY_KEY to dignityId,
                    Constants.NAME_KEY to selectedName?.nameId
                )
            )
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

    private fun renderState(state: NamesScreenState) {
        when (state) {
            is NamesScreenState.Loading -> {
                binding.apply {
                    progressBar.isVisible = true
                }
            }

            is NamesScreenState.Default -> {
                binding.progressBar.isVisible = false
                dignityAdapter.updateList(state.dignity)
                namesAdapter.updateList(state.names)
            }
        }
    }

    private fun hideKeyboard() {
        val imm =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
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
        viewModel.updateSelectedName(selectedName)
        viewModel.updateSelectedDignity(selectedDignity)
        viewModel.getNamesList()
        binding.placeholderNoNames.isVisible =
            !binding.inputName.text.isNullOrEmpty() && !namesAdapter.getAllItems()
                .contains(binding.inputName.text.toString())

    }

}