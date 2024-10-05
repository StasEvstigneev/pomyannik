package com.example.prayforthem.createlist.ui

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.prayforthem.R
import com.example.prayforthem.RootActivity
import com.example.prayforthem.createlist.domain.CreateListScreenState
import com.example.prayforthem.createlist.presentation.CreateListViewModel
import com.example.prayforthem.databinding.FragmentCreateListBinding
import com.example.prayforthem.lists.domain.PersonBasicData
import com.example.prayforthem.utils.setFragmentTitle
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Locale

class CreateListFragment : Fragment() {

    private var _binding: FragmentCreateListBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<CreateListViewModel>()

    private var isForHealth: Boolean = true
    private lateinit var listType: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateListBinding.inflate(inflater, container, false)
        isForHealth = CreateListFragmentArgs.fromBundle(requireArguments()).isForHealthArg
        listType =
            if (isForHealth) getString(R.string.for_the_health)
                .lowercase(Locale.getDefault())
            else getString(R.string.for_the_repose)
        setFragmentTitle(requireActivity() as RootActivity, getString(R.string.new_list, listType))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setListType(isForHealth)

        viewModel.getScreenState().observe(viewLifecycleOwner) { state ->
            renderState(state)
        }

        viewModel.getSaveButtonState().observe(viewLifecycleOwner) { canSave ->
            binding.buttonSave.isEnabled = canSave
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

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        val selectedDignity = CreateListFragmentArgs.fromBundle(requireArguments()).dignityArg
        val selectedName = CreateListFragmentArgs.fromBundle(requireArguments()).nameArg
        if (selectedName != null) {
            viewModel.addPersonToList(PersonBasicData(selectedDignity, selectedName))
            requireArguments().clear()
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
                infoText.text = getString(R.string.added_n_of_ten, state.listSize.toString())
                buttonAddName.isEnabled = !state.isListFull
            }
        }

    }

}