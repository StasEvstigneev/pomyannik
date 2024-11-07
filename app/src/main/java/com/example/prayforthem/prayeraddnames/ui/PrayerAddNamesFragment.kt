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
import com.example.prayforthem.R
import com.example.prayforthem.databinding.FragmentPrayerAddNamesBinding
import com.example.prayforthem.prayeraddnames.domain.models.PrayerAddNamesScreenState
import com.example.prayforthem.prayeraddnames.presentation.PrayerAddNamesViewModel
import com.example.prayforthem.utils.Constants
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class PrayerAddNamesFragment : Fragment() {

    private var _binding: FragmentPrayerAddNamesBinding? = null
    private val binding get() = _binding!!
    private val args: PrayerAddNamesFragmentArgs by navArgs()
    private val viewModel: PrayerAddNamesViewModel by viewModel {
        parametersOf(args.prayerFileNameArg, args.forHealthArg)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPrayerAddNamesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
                    placeholder.isVisible = state.list.isEmpty()
                }
            }

        }
    }

    companion object {
        private const val NULL = 0
    }

}