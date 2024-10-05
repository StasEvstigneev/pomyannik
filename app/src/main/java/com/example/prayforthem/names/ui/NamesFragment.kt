package com.example.prayforthem.names.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
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
            setDropDownBackgroundDrawable(ColorDrawable(Color.WHITE)) // убирает верхние марджины в dpopdown menu

            setOnFocusChangeListener { v, hasFocus ->

            }

            setOnItemClickListener { parent, view, position, id ->
                val dignity = parent.getItemAtPosition(position) as DignityBasicData
                selectedDignity = dignity
                viewModel.updateSelectedDignity(dignity)
                Log.d("CHOSEN DIGNITY FRAGMENT", selectedDignity?.dignityDisplay ?: "null")
            }

            doAfterTextChanged { text ->
                viewModel.updateSelectedDignity(null)

                if (text.isNullOrEmpty()) {
                    viewModel.updateSelectedDignity(null)
                }

                Log.d(
                    "CHOSEN DIGNITY AFTER TEXT CHANGED",
                    selectedDignity?.dignityDisplay ?: "null"
                )
            }
        }

        binding.inputName.apply {
            setDropDownBackgroundDrawable(ColorDrawable(Color.WHITE))

            setOnItemClickListener { parent, view, position, id ->
                val name = parent.getItemAtPosition(position) as NameBasicData
                selectedName = name
                viewModel.updateSelectedName(name)
                Log.d("CHOSEN NAME FRAGMENT", selectedName?.nameDisplay ?: "null")
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
                    Log.d("CHOSEN NAME AFTER TEXT CHANGED", selectedName?.nameDisplay ?: "null")
                }
            })

        }

        binding.buttonSave.setOnClickListener {
            val action = NamesFragmentDirections
                .actionNamesFragmentToCreateListFragment(
                    dignityArg = selectedDignity,
                    nameArg = selectedName
                )
            findNavController().navigate(action)
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

        Log.d("CHOSEN DIGNITY RESUME FRAGMENT", selectedDignity?.dignityDisplay ?: "null")
        Log.d("CHOSEN DIGNITY RESUME VM", viewModel.selectedDignity.value?.dignityDisplay ?: "null")
        Log.d("CHOSEN NAME RESUME FRAGMENT", selectedName?.nameDisplay ?: "null")
        Log.d("CHOSEN INPUT FIELD", binding.inputName.text.toString())
        Log.d("CHOSEN NAME RESUME VM", viewModel.selectedName.value?.nameDisplay ?: "null")

    }

}