package com.example.prayforthem.names.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import com.example.prayforthem.R
import com.example.prayforthem.RootActivity
import com.example.prayforthem.databinding.FragmentNamesBinding
import com.example.prayforthem.names.presentation.NamesViewModel
import com.example.prayforthem.utils.setFragmentTitle
import org.koin.androidx.viewmodel.ext.android.viewModel

class NamesFragment : Fragment() {

    private var _binding: FragmentNamesBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<NamesViewModel>()

    private var dignity = ""
    private var name = ""

    private var dignityList = listOf(
        "Патриарх",
        "Митрополит",
        "Епископ",
        "Архимандрит",
        "Иерей",
        "Диакон",
        "Монах",
        "Патриарх",
        "Митрополит",
        "Епископ",
        "Архимандрит",
        "Иерей",
        "Диакон",
        "Монах",
        "Патриарх",
        "Митрополит",
        "Епископ",
        "Архимандрит",
        "Иерей",
        "Диакон",
        "Монах",
        "Патриарх",
        "Митрополит",
        "Епископ",
        "Архимандрит",
        "Иерей",
        "Диакон",
        "Монах"
    )

    private var namesList = listOf(
        "Имя",
        "Имя",
        "Имя",
        "Имя",
        "Имя",
        "Имя",
        "Имя",
        "Имя",
        "Имя",
        "Имя",
        "Имя",
        "Имя",
        "Имя",
        "Имя",
        "Имя",
        "Имя",
        "Имя",
        "Имя",
        "Имя",
        "Имя",
        "Имя",
        "Имя",
        "Имя",
        "Имя"
    )


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

        val dignityAdapter =
            ArrayAdapter<String>(requireContext(), R.layout.name_drop_down_item, dignityList)

        val namesAdapter =
            ArrayAdapter<String>(requireContext(), R.layout.name_drop_down_item, namesList)

        binding.inputDignity.apply {
            setAdapter(dignityAdapter)
            setDropDownBackgroundDrawable(ColorDrawable(Color.WHITE)) // убирает верхние марджины в dpopdown menu
            doAfterTextChanged { text ->
                dignity = if (text.isNullOrBlank()) "" else text.toString()
            }
        }

        binding.inputName.apply {
            setAdapter(namesAdapter)
            setDropDownBackgroundDrawable(ColorDrawable(Color.WHITE))
            doAfterTextChanged { text ->
                name = if (text.isNullOrBlank()) "" else text.toString()
            }
        }

        binding.buttonSave.setOnClickListener {
            Toast.makeText(requireContext(), dignity + " " + name, Toast.LENGTH_LONG).show()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}