package com.example.prayforthem.names.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
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
                        ArrayAdapter(
                            requireContext(),
                            R.layout.name_drop_down_item,
                            state.dignity
                        )
                    )
                    binding.inputName.setAdapter(
                        ArrayAdapter(
                            requireContext(),
                            R.layout.name_drop_down_item,
                            state.names
                        )
                    )

                }
            }
        }

        binding.inputDignity.apply {
            setDropDownBackgroundDrawable(ColorDrawable(Color.WHITE)) // убирает верхние марджины в dpopdown menu
            setOnItemClickListener { parent, view, position, id ->
                val selectedDignity = parent.getItemAtPosition(position) as DignityBasicData
                viewModel.updateSelectedDignity(selectedDignity)
            }
        }

        binding.inputName.apply {
            setDropDownBackgroundDrawable(ColorDrawable(Color.WHITE))
            setOnItemClickListener { parent, view, position, id ->
                val selectedName = parent.getItemAtPosition(position) as NameBasicData
                viewModel.updateSelectedName(selectedName)
            }
        }

        binding.buttonSave.setOnClickListener {

        }
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