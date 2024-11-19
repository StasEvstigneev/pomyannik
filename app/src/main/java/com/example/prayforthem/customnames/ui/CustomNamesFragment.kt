package com.example.prayforthem.customnames.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.prayforthem.R
import com.example.prayforthem.customnames.domain.CustomNamesScreenState
import com.example.prayforthem.customnames.presentation.CustomNamesViewModel
import com.example.prayforthem.databinding.FragmentCustomNamesBinding
import com.example.prayforthem.listings.domain.RecyclerViewDeleteItem
import com.example.prayforthem.listings.domain.RecyclerViewItemClick
import com.example.prayforthem.names.domain.models.Name
import com.example.prayforthem.utils.DialogConstructor
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.androidx.viewmodel.ext.android.viewModel


class CustomNamesFragment : Fragment(), RecyclerViewItemClick<Name>, RecyclerViewDeleteItem<Name> {

    private var _binding: FragmentCustomNamesBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<CustomNamesViewModel>()
    private val customNamesAdapter = CustomNamesAdapter(this, this)
    private lateinit var deleteDialog: MaterialAlertDialogBuilder

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCustomNamesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        viewModel.getScreenState().observe(viewLifecycleOwner) { state ->
            renderState(state)
        }

        binding.apply {
            recyclerView.adapter = customNamesAdapter
            recyclerView.layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
        }

        binding.fab.setOnClickListener {
            val action = CustomNamesFragmentDirections.actionCustomNamesFragmentToAddNameFragment()
            findNavController().navigate(action)
        }

        requireActivity()
            .onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().popBackStack()
                }
            })

    }

    override fun onResume() {
        super.onResume()
        viewModel.getCustomNames()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun renderState(state: CustomNamesScreenState) {
        when (state) {
            is CustomNamesScreenState.Loading -> {
                binding.apply {
                    progressBar.isVisible = true
                    placeholder.isVisible = false
                    recyclerView.isVisible = false
                }
            }

            is CustomNamesScreenState.Content -> {
                customNamesAdapter.submitList(state.names)
                binding.apply {
                    progressBar.isVisible = false
                    placeholder.isVisible = false
                    recyclerView.isVisible = true
                }
            }

            is CustomNamesScreenState.Error -> {
                binding.apply {
                    progressBar.isVisible = false
                    placeholder.isVisible = true
                    recyclerView.isVisible = false
                }
            }
        }
    }

    override fun onItemClick(item: Name) {
        val action =
            CustomNamesFragmentDirections.actionCustomNamesFragmentToEditNameFragment(item.id!!)
        findNavController().navigate(action)
    }

    override fun onDeleteElementClick(item: Name) {
        deleteDialog = DialogConstructor.createDeleteDialog(
            context = requireContext(),
            action = { viewModel.deleteName(item) },
            view = binding.overlay,
            message = getString(R.string.are_you_sure_you_want_to_delete_person_x, item.nameDisplay)
        )
        deleteDialog.show()
    }

}