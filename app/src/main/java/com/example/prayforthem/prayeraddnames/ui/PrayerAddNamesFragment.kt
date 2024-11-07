package com.example.prayforthem.prayeraddnames.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.prayforthem.R
import com.example.prayforthem.databinding.FragmentPrayerAddNamesBinding
import com.example.prayforthem.listings.domain.RecyclerViewDeleteItem
import com.example.prayforthem.listings.domain.models.PersonDignityName
import com.example.prayforthem.prayeraddnames.domain.models.PrayerAddNamesScreenState
import com.example.prayforthem.prayeraddnames.presentation.PrayerAddNamesViewModel
import com.example.prayforthem.utils.Constants
import com.example.prayforthem.utils.DialogConstructor
import com.example.prayforthem.utils.NameFormsConstructor
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class PrayerAddNamesFragment : Fragment(), RecyclerViewDeleteItem<PersonDignityName> {

    private var _binding: FragmentPrayerAddNamesBinding? = null
    private val binding get() = _binding!!
    private val args: PrayerAddNamesFragmentArgs by navArgs()
    private val viewModel: PrayerAddNamesViewModel by viewModel {
        parametersOf(args.prayerFileNameArg, args.forHealthArg)
    }
    private var showExitDialog: Boolean = false

    private val tempNamesAdapter = PrayerAddNamesAdapter(this)
    private lateinit var exitDialog: MaterialAlertDialogBuilder
    private lateinit var deleteDialog: MaterialAlertDialogBuilder

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPrayerAddNamesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationOnClickListener {
            leaveFragment()
        }

        exitDialog = DialogConstructor.createExitDialog(
            context = requireContext(),
            navController = findNavController(),
            view = binding.overlay
        )

        binding.recyclerView.apply {
            adapter = tempNamesAdapter
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
        }

        viewModel.getScreenState().observe(viewLifecycleOwner) { state ->
            renderState(state)
        }

        binding.btnAddName.setOnClickListener {
            findNavController().navigate(R.id.action_prayerAddNamesFragment_to_namesFragment)
        }

    }

    override fun onResume() {
        super.onResume()
        setFragmentResultListener(Constants.REQUEST_PERSON_KEY) { _, bundle ->
            val dignityId = bundle.getInt(Constants.DIGNITY_KEY)
            val nameId = bundle.getInt(Constants.NAME_KEY)
            if (dignityId != NULL) {
                viewModel.createNewPerson(dignityId, nameId)
            } else {
                viewModel.createNewPerson(null, nameId)
            }
            parentFragmentManager.clearFragmentResult(Constants.REQUEST_PERSON_KEY)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
//        viewModel.deleteAllTempNames()
        _binding = null
    }

    private fun renderState(state: PrayerAddNamesScreenState) {
        when (state) {
            is PrayerAddNamesScreenState.Loading -> {
                binding.apply {
                    progressBar.isVisible = true
                    recyclerView.isVisible = false
                    placeholder.isVisible = false
                }
            }

            is PrayerAddNamesScreenState.Content -> {
                binding.apply {
                    progressBar.isVisible = false
                    recyclerView.isVisible = true
                    tempNamesAdapter.submitList(state.list)
                    placeholder.isVisible = state.list.isEmpty()
                }
                showExitDialog = state.list.isNotEmpty()
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
            action = { viewModel.deleteTempPerson(item) },
            message = getString(
                R.string.are_you_sure_you_want_to_delete_person_x,
                NameFormsConstructor.createPersonDisplay(item)
            ),
            view = binding.overlay
        )
        deleteDialog.show()
    }

    companion object {
        private const val NULL = 0
    }

}