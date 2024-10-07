package com.example.prayforthem.createlist.ui

import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import com.example.prayforthem.R
import com.example.prayforthem.RootActivity
import com.example.prayforthem.createlist.domain.CreateListScreenState
import com.example.prayforthem.createlist.presentation.CreateListViewModel
import com.example.prayforthem.databinding.FragmentCreateListBinding
import com.example.prayforthem.utils.Constants
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
        setFragmentResultListener(Constants.REQUEST_PERSON_KEY) { _, bundle ->
            val dignityId = bundle.getInt(Constants.DIGNITY_KEY)
            val nameId = bundle.getInt(Constants.NAME_KEY)

            Log.d("RECEIVED DIG_id FRAGMENT", "$dignityId")
            Log.d("RECEIVED NAME_id FRAGMENT", "$nameId")

            if (dignityId != NULL) {
                viewModel.createNewPerson(dignityId, nameId)
            } else {
                viewModel.createNewPerson(null, nameId)

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
                infoText.text = getString(R.string.added_n_of_ten, state.listSize.toString())
                buttonAddName.isEnabled = !state.isListFull
            }
        }

    }

    companion object {
        private const val NULL = 0
    }

}