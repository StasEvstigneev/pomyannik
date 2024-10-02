package com.example.prayforthem.names.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import com.example.prayforthem.R
import com.example.prayforthem.RootActivity
import com.example.prayforthem.databinding.FragmentNamesBinding
import com.example.prayforthem.names.domain.models.DignityBasicData
import com.example.prayforthem.names.domain.models.NameBasicData
import com.example.prayforthem.names.domain.models.NamesScreenState
import com.example.prayforthem.names.presentation.NamesViewModel
import com.example.prayforthem.utils.setFragmentTitle
import org.koin.androidx.viewmodel.ext.android.viewModel

class NamesFragment : Fragment() {

    private var _binding: FragmentNamesBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<NamesViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setFragmentTitle(requireActivity() as RootActivity, getString(R.string.names))
        _binding = FragmentNamesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getScreenState().observe(viewLifecycleOwner) { state ->

            when (state) {
                is NamesScreenState.Loading -> {
                    true
                }

                is NamesScreenState.Default -> {
                    binding.inputDignity.setAdapter(
                        CustomArrayAdapter(
                            requireContext(),
                            state.dignity
                        )
                    )
                    binding.inputName.setAdapter(
                        CustomArrayAdapter(
                            requireContext(),
                            state.names
                        )
                    )
                }
            }
        }

        viewModel.getSaveButtonState().observe(viewLifecycleOwner) { isEnabled ->
            binding.buttonSave.isEnabled = isEnabled
        }

        binding.inputDignity.apply {
            setDropDownBackgroundDrawable(ColorDrawable(Color.WHITE)) // убирает верхние марджины в dpopdown menu
            setOnFocusChangeListener { v, hasFocus ->
                if (hasFocus) showDropDown()
            }
            setOnItemClickListener { parent, view, position, id ->
                val selectedDignity = parent.getItemAtPosition(position) as DignityBasicData
                viewModel.updateSelectedDignity(selectedDignity)
            }
            doAfterTextChanged { text ->
                viewModel.updateSelectedDignity(null)
            }

        }

        binding.inputName.apply {
            setDropDownBackgroundDrawable(ColorDrawable(Color.WHITE))
            setOnFocusChangeListener { v, hasFocus ->
                if (hasFocus) showDropDown()
            }

            setOnItemClickListener { parent, view, position, id ->
                val selectedName = parent.getItemAtPosition(position) as NameBasicData
                viewModel.updateSelectedName(selectedName)
                binding.placeholderNoNames.isVisible = false

            }

            addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    viewModel.updateSelectedName(null)

                }

                override fun afterTextChanged(s: Editable?) {
                    if (adapter.count > 0 || s.isNullOrBlank()) {
                        binding.placeholderNoNames.isVisible = false
                    } else {
                        binding.placeholderNoNames.isVisible = true
                    }
                }

            })

        }

        binding.buttonSave.setOnClickListener {}
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        viewModel.getNamesList()
    }

}