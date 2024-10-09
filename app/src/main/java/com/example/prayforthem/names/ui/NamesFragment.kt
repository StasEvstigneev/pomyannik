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
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.example.prayforthem.R
import com.example.prayforthem.RootActivity
import com.example.prayforthem.databinding.FragmentNamesBinding
import com.example.prayforthem.names.domain.models.DignityBasicData
import com.example.prayforthem.names.domain.models.NameBasicData
import com.example.prayforthem.names.domain.models.NamesScreenState
import com.example.prayforthem.names.presentation.NamesViewModel
import com.example.prayforthem.utils.Constants
import com.example.prayforthem.utils.setFragmentTitle
import org.koin.androidx.viewmodel.ext.android.viewModel

class NamesFragment : Fragment() {

    private var _binding: FragmentNamesBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<NamesViewModel>()

    private lateinit var dignityAdapter: CustomArrayAdapter<DignityBasicData>
    private lateinit var namesAdapter: CustomArrayAdapter<NameBasicData>

    private var selectedDignity: DignityBasicData? = null
    private var selectedName: NameBasicData? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setFragmentTitle(requireActivity() as RootActivity, getString(R.string.names))
        _binding = FragmentNamesBinding.inflate(inflater, container, false)

        dignityAdapter = CustomArrayAdapter(requireContext(), listOf())
        binding.inputDignity.setAdapter(dignityAdapter)

        namesAdapter = CustomArrayAdapter(requireContext(), listOf())
        binding.inputName.setAdapter(namesAdapter)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getScreenState().observe(viewLifecycleOwner) { state ->
            renderState(state)
        }

        viewModel.getSelectedDignity().observe(viewLifecycleOwner) { dignity ->
            selectedDignity = dignity
        }

        viewModel.getSelectedName().observe(viewLifecycleOwner) { name ->
            selectedName = name
            binding.buttonSave.isEnabled = (name != null)
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
    }

    private fun renderState(state: NamesScreenState) {
        when (state) {
            is NamesScreenState.Loading -> {
                true
            }

            is NamesScreenState.Default -> {
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